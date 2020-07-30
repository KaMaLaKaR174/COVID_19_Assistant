package com.example.kamalakar.covidtracker.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.preference.PreferenceManager;

import com.example.kamalakar.covidtracker.Fragments.HomeFragment;
import com.example.kamalakar.covidtracker.R;
import com.example.kamalakar.covidtracker.Fragments.SearchFragment;
import com.example.kamalakar.covidtracker.Fragments.StockFragment;
import com.example.kamalakar.covidtracker.Adapters.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.widget.TextView;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String KEY_SWITCH="switch_select";
    TabLayout tabLayout;
    ViewPager viewPager;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    TextView email,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Covid Assistant");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);

        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(),"Home");
        adapter.addFragment(new StockFragment(),"Stock");
        adapter.addFragment(new SearchFragment(),"Search");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        firebaseAuth=FirebaseAuth.getInstance();

//        email=findViewById(R.id.nav_email);
//        name=findViewById(R.id.nav_user_name);
//
//        currentUser=firebaseAuth.getCurrentUser();
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference reference=database.getReference(currentUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
//                email.setText(userProfile.getRegisterEmail());
//                name.setText(userProfile.getRegisterName());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

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

    


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_covid_stats) {
            startActivity(new Intent(NavigationDrawerActivity.this,GlobalStatsActivity.class));


        }
        else if(id==R.id.nav_profile){
            startActivity(new Intent(NavigationDrawerActivity.this,ProfileActivity.class));

        }
        else if (id == R.id.nav_settings) {
            startActivity(new Intent(NavigationDrawerActivity.this,SettingsActivity.class));

        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            this.finish();
            startActivity(new Intent(NavigationDrawerActivity.this,LoginActivity.class));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
