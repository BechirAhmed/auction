package com.auction.auction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.auction.auction.data.CategoriesRepository;
import com.auction.auction.data.models.Category;
import com.auction.auction.data.services.CategoryLocalService;
import com.auction.auction.data.services.CategoryRemoteService;
import com.auction.auction.data.services.ICategoryLocalService;
import com.auction.auction.data.services.ICategoryRemoteService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] mCategoryNames;
    private String mAuthToken;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mainToolbar;
    private SharedPreferences sharedPrefs;
    private ICategoryLocalService categoryLocalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        mAuthToken = sharedPrefs
                .getString(getString(R.string.auth_token_pref_key), null);

        if (mAuthToken == null) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);

            mainToolbar = (Toolbar)findViewById(R.id.main_toolbar);
            setSupportActionBar(mainToolbar);

            mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            mDrawerList = (ListView)findViewById(R.id.left_drawer);

            CategoriesRepository categoriesRepository = new CategoriesRepository(getApplicationContext());
            categoryLocalService = new CategoryLocalService(categoriesRepository);

            long savedUnixTime = sharedPrefs
                    .getLong(getString(R.string.categories_cache_pref_key), Long.MAX_VALUE);

            long currentUnixTime = System.currentTimeMillis() / 1000L;

            // Categories are cached each 10 minutes (10 * 60 seconds).
            if (savedUnixTime == Long.MAX_VALUE) {
                (new GetCategoriesTask()).execute(mAuthToken);
                Log.d("MainActivity", "Categories never cached so far, fetching from server..");
            } else if (currentUnixTime > savedUnixTime + 10 * 60) {
                Log.d("MainActivity", "Categories cache expired, deleting old ones and fetching new..");
                categoryLocalService.removeAll();
                (new GetCategoriesTask()).execute(mAuthToken);
            } else {
                Log.d("MainActivity", "Categories cached, copying from db..");
                mCategoryNames = copyCategoryNames(categoryLocalService.all());
                mDrawerList.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.drawer_list_item, mCategoryNames));
                mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.remove(getString(R.string.auth_token_pref_key));
            editor.apply();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_add_product) {
            Intent intent = new Intent(this, AddProductActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // This is used to change the FrameLayout, which contains the products ListView
    private void selectItem(int position) {
        // Create a new fragment, specify the category to show and pass the needed arguments to populate
        Fragment fragment = new ProductsListFragment();
        Bundle args = new Bundle();
        args.putString("categoryId", categoryLocalService.getCategoryId(mCategoryNames[position]));
        args.putString("authToken", mAuthToken);
        fragment.setArguments(args);

        // Replace the old products fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.product_list_fragment, fragment)
                .commit();

        // Highlight the selected category, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mainToolbar.setTitle(mCategoryNames[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class GetCategoriesTask extends AsyncTask<String, Void, List<Category>> {
        @Override
        protected List<Category> doInBackground(String... params) {
            String encodedToken = params[0];

            ICategoryRemoteService categoryRemoteService = new CategoryRemoteService(encodedToken);
            return categoryRemoteService.getAll();
        }

        @Override
        protected void onPostExecute(final List<Category> categories) {
            mCategoryNames = copyCategoryNames(categories);

            mDrawerList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.drawer_list_item, mCategoryNames));
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

            long unixTime = System.currentTimeMillis() / 1000L;
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putLong(getString(R.string.categories_cache_pref_key), unixTime);
            editor.apply();

            for (Category category : categories) {
                categoryLocalService.add(category);
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Could not fetch categories. Try again later", Toast.LENGTH_LONG).show();
        }
    }

    private String[] copyCategoryNames(List<Category> categories) {
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).name;
        }

        return categoryNames;
    }
}