package com.milansamardzic.ms.rottentomatomovie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;

import com.milansamardzic.ms.navigationdrawer.CustomNavDraw;

/**
 * Created by ms on 10/25/14.
 */
public class MainActivity extends ActionBarActivity implements OnItemClickListener {

  private ActionBarDrawerToggle drawerListener;
  private DrawerLayout drawerLayout;
  private ListView listView;
  private CustomNavDraw customNavDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3A9425")));
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.drawerList);
        customNavDraw = new CustomNavDraw(this);
        listView.setAdapter(customNavDraw);

        listView.setOnItemClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerListener);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerListener.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //  Toast.makeText(this, "nesto-1" + items[position], Toast.LENGTH_LONG).show();
        selectItem(position);
        drawerLayout.closeDrawers();

    }

    public void selectItem(int position) {
        listView.setItemChecked(position, true);
        setTitle(customNavDraw.opt[position]);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment selected;

        switch(position){
            case 0:
                selected = new TopBoxOffice();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 1:
                selected = new TopCommingSoon();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 2:
                selected = new OpeningThisWeek();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 3:
                selected = new InTheaters();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
        }




    }
}