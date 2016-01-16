package com.auction.auction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.auction.auction.data.models.Category;
import com.auction.auction.data.services.CategoryRemoteService;
import com.auction.auction.data.services.ICategoryRemoteService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private String[] mCategoryTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String authToken = sharedPref.getString(getString(R.string.auth_token_pref_key), null);

        if (authToken == null) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);

            mainToolbar = (Toolbar)findViewById(R.id.main_toolbar);
            setSupportActionBar(mainToolbar);

            mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            mDrawerList = (ListView)findViewById(R.id.left_drawer);

            (new GetCategoriesTask()).execute(authToken);
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
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.auth_token_pref_key));
            editor.commit();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This is used to change the FrameLayout
    private void selectItem(int position) {
        // Create a new fragment and specify the category to show based on position
        Fragment fragment = new ProductsListFragment();
        Bundle args = new Bundle();
        args.putInt(ProductsListFragment.CATEGORY_ID, position);
        fragment.setArguments(args);

        // Replace the old products fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.product_list_fragment, fragment)
                .commit();

        // Highlight the selected category, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mainToolbar.setTitle(mCategoryTitles[position]);
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
            mCategoryTitles = new String[categories.size()];

            for (int i = 0; i < categories.size(); i++) {
                mCategoryTitles[i] = categories.get(i).name;
            }

            mDrawerList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.drawer_list_item, mCategoryTitles));
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Could not fetch categories. Try again later", Toast.LENGTH_LONG).show();
        }
    }
}