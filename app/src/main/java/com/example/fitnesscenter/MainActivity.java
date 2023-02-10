package com.example.fitnesscenter;

import static com.example.fitnesscenter.Constant.getUserLoginStatus;
import static com.example.fitnesscenter.Constant.setUserLoginStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fitnesscenter.Screen.AdminLoginActivity;
import com.example.fitnesscenter.Screen.BookingActivity;
import com.example.fitnesscenter.Screen.LoginActivity;
import com.example.fitnesscenter.Screen.UserBookingStatusActivity;
import com.example.fitnesscenter.fragments.AccountFragment;
import com.example.fitnesscenter.fragments.ContactFragment;
import com.example.fitnesscenter.fragments.HomeFragment;
import com.example.fitnesscenter.fragments.PackgesFragment;
import com.example.fitnesscenter.fragments.aboutFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView =findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer, (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar),R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home :
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                        break;
                    case R.id.logout:
                        setUserLoginStatus(MainActivity.this,false);
                        break;
                    case R.id.aboutus:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new aboutFragment()).commit();
                        break;
                    case R.id.contactus:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ContactFragment()).commit();
                        break;
                    case R.id.my_account:
                        if(getUserLoginStatus(MainActivity.this)){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AccountFragment()).commit();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Please sign in first",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                        case R.id.admin_login:
                            startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
                        break;
                    case R.id.packges:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PackgesFragment()).commit();
                        break;
                    case R.id.booking:
                        if(getUserLoginStatus(MainActivity.this)){
                            startActivity(new Intent(MainActivity.this, BookingActivity.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Please sign in first",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                    case R.id.booking_status:
                        if(getUserLoginStatus(MainActivity.this)){
                            startActivity(new Intent(MainActivity.this, UserBookingStatusActivity.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Please sign in first",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}