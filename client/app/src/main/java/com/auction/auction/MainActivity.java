package com.auction.auction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

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
            Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
            setSupportActionBar(mainToolbar);
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
}