package com.example.moviemate.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.moviemate.info.DevelopersAbout;
import com.example.moviemate.info.DevelopersFragment;
import com.example.moviemate.home.HomeFragment;
import com.example.moviemate.R;
import com.example.moviemate.tv.TvFragment;
import com.example.moviemate.movie.MoviesFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Fragment fragment;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                new HomeFragment()).commit();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        setUpToolBar();
    }

    public void sakshiInstaClick(View view) {
        openUrl1("https://www.instagram.com/sakshi_yadav_77/");
    }

    private void openUrl1(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void sakshiFacebookClick(View view) {
        openUrl2("https://www.facebook.com/sakshi.yadav.140");
    }

    private void openUrl2(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void sakshiGmailClick(View view) {
        openUrl3("https://www.gmail.com");
    }


    private void openUrl3(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }


    public void tanishqInstaClick(View view) {
        openUrl4("https://www.instagram.com/tanishq_bhardwaj_9/");
    }


    private void openUrl4(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void tanishqFacebookClick(View view) {
        openUrl5("https://www.facebook.com/sakshi.yadav.140");
    }


    private void openUrl5(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void tanishqGmailClick(View view) {
        openUrl6("https://mail.google.com/mail/u/0/#inbox");
    }

    private void openUrl6(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_movies:
                fragment = new MoviesFragment();
                break;

            case R.id.nav_TV_shows:
                fragment = new TvFragment();
                break;

            case R.id.nav_about:
                fragment = new DevelopersAbout();
                Toast.makeText(this, "ABOUT US", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_dev:
                fragment = new DevelopersFragment();
                Toast.makeText(this, "DEVELOPERS", Toast.LENGTH_LONG).show();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpToolBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        if(fragment instanceof HomeFragment) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new HomeFragment()).commit();
            navigationView.setCheckedItem(menuItem.getItemId());
        }
    }
}