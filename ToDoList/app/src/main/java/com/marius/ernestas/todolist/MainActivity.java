package com.marius.ernestas.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.marius.ernestas.todolist.database.Database;
import com.marius.ernestas.todolist.navigationDrawer.DrawerItem;
import com.marius.ernestas.todolist.navigationDrawer.DrawerItemClickListener;
import com.marius.ernestas.todolist.navigationDrawer.DrawerListAdapter;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public Database database;
    private String[] menuList;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private SharedPreferences sharedPreferences;
    private String language = "languageKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new Database(this);
        sharedPreferences = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);

        // Setting language
        String languageToLoad = (sharedPreferences.getLong(language, 0) == 0) ? "en" : "lt";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = this.getResources().getConfiguration();
        config.locale = locale;

        Resources resources = this.getBaseContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());


        setContentView(R.layout.activity_main);

        handleNavigationDrawer(savedInstanceState);
    }

    private void handleNavigationDrawer(Bundle savedInstanceState) {
        menuList = getResources().getStringArray(R.array.menu_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_layout);
        drawerListView = (ListView) findViewById(R.id.drawer_list_view);

        // Create new ArrayList for ListView's items
        ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();
        // Populating ArrayList
        for (int i = 0; i < menuList.length; i++) {
            drawerItems.add(new DrawerItem(menuList[i]));
        }

        // Set drawers list adapter
        DrawerListAdapter listAdapter = new DrawerListAdapter(getApplicationContext(), drawerItems);
        drawerListView.setAdapter(listAdapter);

        // Set on click listener for list view
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        drawerListView.setOnItemClickListener(new DrawerItemClickListener(fragmentManager, database, drawerLayout));

        // Set MainFragment as default
        drawerListView.performItemClick(drawerListView, 0, drawerListView.getItemIdAtPosition(0));

        // Handle drawer toggle
        handleDrawerToggle();

        drawerLayout.setDrawerListener(drawerToggle);

        // Enable action ar app icon and use it as a toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void handleDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Call onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Call onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
        };
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        // If navigation drawer is opened, hide the action bar items
        menu.setGroupVisible(0, drawerLayout.isDrawerOpen(drawerListView) ? false : true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
