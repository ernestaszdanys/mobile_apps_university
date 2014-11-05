package com.marius.ernestas.todolist.navigationDrawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.marius.ernestas.todolist.R;
import com.marius.ernestas.todolist.database.Database;
import com.marius.ernestas.todolist.fragments.AboutFragment;
import com.marius.ernestas.todolist.fragments.MainFragment;

public class DrawerItemClickListener implements ListView.OnItemClickListener {

    private android.support.v4.app.FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private Database database;

    public DrawerItemClickListener(FragmentManager fragmentManager, Database database, DrawerLayout drawerLayout) {
        this.fragmentManager = fragmentManager;
        this.drawerLayout = drawerLayout;
        this.database = database;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new MainFragment(database);
                break;
            case 2:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new MainFragment(database);
                break;
        }

        drawerLayout.closeDrawers();
        // Replace current fragment with selected
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }
}
