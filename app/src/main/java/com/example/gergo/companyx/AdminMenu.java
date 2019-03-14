package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMenu extends AppCompatActivity{

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView twLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginUserName",MODE_PRIVATE);
        String LoginUserName = sp.getString("LoginUserName","Nincs adat");

        twLogin.setText(LoginUserName);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navUserMenu:
                        startActivity(new Intent(AdminMenu.this, UserMenu.class));
                        finish();
                        break;

                    case R.id.navEmployeeMenu:

                        break;

                    case R.id.navPositionMenu:
                        startActivity(new Intent(AdminMenu.this, PositionMenu.class));
                        finish();
                        break;

                    case R.id.navDepartmentMenu:
                        startActivity(new Intent(AdminMenu.this, DepartmentMenu.class));
                        finish();
                        break;

                    case R.id.navCarMenu:
                        startActivity(new Intent(AdminMenu.this, CarMenu.class));
                        finish();
                        break;

                    case R.id.navMobileMenu:
                        startActivity(new Intent(AdminMenu.this, MobileMenu.class));
                        finish();
                        break;

                    case R.id.navLaptopMenu:
                        startActivity(new Intent(AdminMenu.this, LaptopMenu.class));
                        finish();
                        break;

                    case R.id.navTrans:

                        break;

                    case R.id.navTrip:

                        break;

                    case R.id.navLog:

                        break;

                    case R.id.navLogout:
                        startActivity(new Intent(AdminMenu.this, Login.class));
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    public void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Főmenü");
        drawer = findViewById(R.id.admin_layout);
        navigationView = findViewById(R.id.nav_view_admin);
        twLogin = findViewById(R.id.twLogin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMenu.this);

        builder.setCancelable(true);
        builder.setTitle("Kilépés");
        builder.setMessage("Valóban be szeretnéd zárni az alkalmazást?");

        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        builder.show();
        }
    }
}
