package com.milansamardzic.ms.rottentomatomovie;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.milansamardzic.ms.RemoteImageView;
import com.milansamardzic.ms.navigationdrawer.CustomNavDraw;


import org.acra.annotation.ReportsCrashes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import static android.graphics.Color.parseColor;

/**
 * Created by ms on 10/25/14.
 */
public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    private ActionBarDrawerToggle drawerListener;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private CustomNavDraw customNavDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);

        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(parseColor("#ce5043")));
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.drawerList);
        customNavDraw = new CustomNavDraw(this);
        listView.setAdapter(customNavDraw);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerListener);

        listView.setOnItemClickListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment selected;
        selected = new Home();
        fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global, menu);
       return super.onCreateOptionsMenu(menu);
    }

    int pos=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((drawerLayout.isDrawerOpen(Gravity.LEFT)) && pos!= (-1) ) {
                drawerLayout.closeDrawer(Gravity.LEFT); return true;
            }else if (pos != 0) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment selected = new Home();;
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                listView.setItemChecked(0, true);
                setTitle(customNavDraw.opt[0].toUpperCase());
                pos = 0; return true;
            } else if(pos == 0){
                finish();
            }


            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerListener.onOptionsItemSelected(item)){
            return  true;
        }
        switch(item.getItemId()) {
            case R.id.action_settings:

                Toast.makeText(getApplication(), "refresh", Toast.LENGTH_LONG).show();
                break;
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

        selectItem(position);

        TextView tv = (TextView) view.findViewById(R.id.textView1);

          for (int i = 0; i < parent.getChildCount(); i++){
             parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);}

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        view.setBackgroundColor(color);
        drawerLayout.closeDrawers();

    }

    public void selectItem(int position) {
        listView.setItemChecked(position, true);
        setTitle(customNavDraw.opt[position]);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment selected;

        pos=position;
        switch(position){
            case 0:
                selected = new Home();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 1:
                selected = new BoxOffice();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                //  Intent intent = new Intent(this, BoxOfficeActivity.class);
                // startActivity(intent);
                break;
            case 2:
                selected = new UpcomingSoon();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 3:
                selected = new OpeningMovies();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 4:
                selected =new InTheatersG();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 5:
                selected = new Favourite();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 6:
                selected = new Search();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
            case 8:
                selected = new Feedback();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;

            case 9:
                selected = new About();
                fragmentManager.beginTransaction().replace(R.id.mainContent, selected).commit();
                break;
        }

    }

}