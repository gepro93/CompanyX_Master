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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class AdminMenu extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView twLogin;
    private ImageView iwMenuHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String LoginUserName = sp.getString("LoginUserName", "Nincs adat");
        iwMenuHeader.setImageResource(R.mipmap.ic_nav_admin_round);
        twLogin.setText(LoginUserName);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navUserMenu:
                        startActivity(new Intent(AdminMenu.this, UserMenu.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navEmployeeMenu:
                        startActivity(new Intent(AdminMenu.this, EmployeeHandle.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navPositionMenu:
                        startActivity(new Intent(AdminMenu.this, PositionMenu.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navDepartmentMenu:
                        startActivity(new Intent(AdminMenu.this, DepartmentMenu.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navCarMenu:
                        startActivity(new Intent(AdminMenu.this, CarMenu.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navMobileMenu:
                        startActivity(new Intent(AdminMenu.this, MobileMenu.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navLaptopMenu:
                        startActivity(new Intent(AdminMenu.this, LaptopMenu.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navTrans:
                        startActivity(new Intent(AdminMenu.this, BenefitList.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;

                    case R.id.navTrip:
                        startActivity(new Intent(AdminMenu.this, TripList.class));
                        Animatoo.animateSlideLeft(AdminMenu.this);
                        finish();
                        break;


                    case R.id.navLogout:
                        startActivity(new Intent(AdminMenu.this, Login.class));
                        Animatoo.animateFade(AdminMenu.this);
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    public void init() {
        iwMenuHeader = findViewById(R.id.iwMenuHeader);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Főmenü");
        drawer = findViewById(R.id.admin_layout);
        navigationView = findViewById(R.id.nav_view_admin);
        twLogin = findViewById(R.id.twLogin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMenu.this);

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
                startActivity(new Intent(AdminMenu.this, Login.class));
                Animatoo.animateFade(AdminMenu.this);
                finish();
            }
        });
        builder.show();
    }

}
