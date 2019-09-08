package com.example.hamburger;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;



import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
DrawerLayout drawerLayout;
Toolbar toolbar;

ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
    }
    private void setUpToolbar()
    {
drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
toolbar=(Toolbar) findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.app_name, R.string.app_name);
drawerLayout.addDrawerListener(actionBarDrawerToggle);
actionBarDrawerToggle.syncState();

    }



}
