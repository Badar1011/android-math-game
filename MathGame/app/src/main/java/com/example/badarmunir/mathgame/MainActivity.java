package com.example.badarmunir.mathgame;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Explode;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badarmunir.mathgame.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Home.OnFragmentInteractionListener,
        BlankFragment.OnFragmentInteractionListener,
        ItemFragment.OnListFragmentInteractionListener {




    // this field is used to save reference to blank fragment class
    BlankFragment blankFragment;
    // this field is used to save reference to item fragment class
    ItemFragment itemFragment;
    // this field is used to get reference to fragment manager
    FragmentManager fragmentManager;
    // home fragment
    Home homeFragment;




     Spinner spinner2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);








        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // here it creates objects and store references to the field
        blankFragment = new BlankFragment();
        itemFragment = new ItemFragment();
        homeFragment = new Home();
        fragmentManager = getSupportFragmentManager();





        spinner2 = findViewById(R.id.spinner_nav4);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_name, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int ii = 0;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {

                    if (ii > 0)
                    {
                        fragmentManager.beginTransaction().detach(itemFragment).commit();

                    }
                    ii++;
                    itemFragment.clearPlayerArrayList();
                    itemFragment.readFromFile("easy.txt",MainActivity.this);
                    fragmentManager.beginTransaction().attach(itemFragment).commit();
                }else if (i == 1)
                {
                    Log.d("kava","hahahaha");
               //     itemFragment.clearPlayerArrayList();
                    fragmentManager.beginTransaction().detach(itemFragment).commit();
                    itemFragment.clearPlayerArrayList();
                    itemFragment.readFromFile("medium.txt",MainActivity.this);
                    fragmentManager.beginTransaction().attach(itemFragment).commit();
                } else if (i == 2)
                {
                    Log.d("kava","hahahaha");
               //     itemFragment.clearPlayerArrayList();
                    fragmentManager.beginTransaction().detach(itemFragment).commit();
                    itemFragment.clearPlayerArrayList();
                    itemFragment.readFromFile("hard.txt",MainActivity.this);
                    fragmentManager.beginTransaction().attach(itemFragment).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       fragmentManager.beginTransaction().replace(R.id.content_main,homeFragment).commit();

    }







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.howToPlayPage1)
        {
            // Handle the camera action


            spinner2.setVisibility(View.INVISIBLE);
            fragmentManager.beginTransaction().replace(R.id.content_main,blankFragment).commit();
         //   coordinatorLayout.setVisibility(coordinatorLayout.INVISIBLE);
        }
        else if (id == R.id.highScorePage1)
        {

            spinner2.setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction().replace(R.id.content_main,itemFragment).commit();
        }
        else if (id == R.id.exitButton1)
        {
            finish();
        }
        else if (id == R.id.homepage1)
        {
            spinner2.setVisibility(View.INVISIBLE);
            fragmentManager.beginTransaction().replace(R.id.content_main,homeFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }





    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
