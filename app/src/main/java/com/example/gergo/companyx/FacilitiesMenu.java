package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class FacilitiesMenu extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView iwMenuHeader;
    private TextView twLogin;
    private String userName, permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");
        permission = sp.getString("Permission","Nincs adat");
        twLogin.setText(userName);
        iwMenuHeader.setImageResource(R.mipmap.ic_nav_facilities_round);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navCarMenu:
                        startActivity(new Intent(FacilitiesMenu.this, FacCarHandle.class));
                        Animatoo.animateSlideLeft(FacilitiesMenu.this);
                        finish();
                        break;

                    case R.id.navMobileMenu:
                        startActivity(new Intent(FacilitiesMenu.this, FacMobileHandle.class));
                        Animatoo.animateSlideLeft(FacilitiesMenu.this);
                        finish();
                        break;

                    case R.id.navLaptopMenu:
                        startActivity(new Intent(FacilitiesMenu.this, FacLaptopHandle.class));
                        Animatoo.animateSlideLeft(FacilitiesMenu.this);
                        finish();
                        break;

                    case R.id.navQrCreate:
                        startActivity(new Intent(FacilitiesMenu.this, QRCreation.class));
                        Animatoo.animateSlideLeft(FacilitiesMenu.this);
                        finish();
                        break;

                    case R.id.navQrRead:
                        startActivity(new Intent(FacilitiesMenu.this, QRRead.class));
                        Animatoo.animateSlideLeft(FacilitiesMenu.this);
                        finish();
                        break;

                    case R.id.navLogout:
                        startActivity(new Intent(FacilitiesMenu.this, Login.class));
                        Animatoo.animateFade(FacilitiesMenu.this);
                        finish();
                        break;
                }

                return true;
            }
        });
    }

    private void init() {
        iwMenuHeader = findViewById(R.id.iwMenuHeader);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Főmenü");
        drawer = findViewById(R.id.facilities_layout);
        navigationView = findViewById(R.id.nav_view_facilities);
        twLogin = findViewById(R.id.twLogin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacilitiesMenu.this);

        builder.setCancelable(true);
        builder.setTitle("Kijelentkezés");
        builder.setMessage("Valóban kijelentkezel?");
        builder.setIcon(R.drawable.ic_dialog_error);

        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(FacilitiesMenu.this, Login.class));
                Animatoo.animateFade(FacilitiesMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
