package com.dailyaquaWaterCarrier.dailyaqua;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

public class DrawerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout = null;
    private ListView mDrawerList = null;
    private String[] mDrawerItems;
    private ActionBarDrawerToggle mDrawerToggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerItems = getResources().getStringArray(R.array.left_drawer_array);

        //  mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerList.setAdapter(new ArrayAdapter<String>(
                this, R.layout.drawer_list_item, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        for (int index = 0; index < menu.size(); index++) {
            MenuItem menuItem = menu.getItem(index);
            if (menuItem != null) {
                // hide the menu items if the drawer is open
                menuItem.setVisible(!drawerOpen);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        if (drawerOpen)
        {
            mDrawerLayout.closeDrawers();
        }
        else {
            ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            String currentClass = am.getRunningTasks(1).get(0).topActivity.getShortClassName();
            if(currentClass.equals((".Activity_Home")))
            {
                showAlert();
            }
            else {
                Intent i = new Intent(this, Activity_Home.class);
                startActivity(i);
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String s= cn.getClassName();
            switch (position) {
                case 0: {
                    //  Intent intent = new Intent(DrawerActivity.this, SettingsActivity.class);
                    //startActivity(intent);
                    break;
                }
                case 1: {
                    //Intent intent = new Intent(DrawerActivity.this, SampleActivity.class);
                    //startActivity(intent);
                    break;
                }
                case 2: {
                    //  Intent intent = new Intent(DrawerActivity.this, SettingsActivity.class);
                    //startActivity(intent);
                    break;
                }
                case 3: {
                    //Intent intent = new Intent(DrawerActivity.this, SampleActivity.class);
                    //startActivity(intent);
                    break;
                }
                case 4: {
                    //  Intent intent = new Intent(DrawerActivity.this, SettingsActivity.class);
                    //startActivity(intent);
                    break;
                }
                case 5: {
                    Intent intent = new Intent(DrawerActivity.this, Activity_Email_Us.class);
                    startActivity(intent);
                    break;
                }
            case 6: {
                Intent intent = new Intent(DrawerActivity.this, Activity_About_Us.class);
                startActivity(intent);
                break;
            }
                default:
                    break;
            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private void showAlert()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle("");
        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to  to exit!")
                .setCancelable(true)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}