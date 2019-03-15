package com.example.gergo.companyx;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeMenu extends AppCompatActivity {

    private ImageView iwMenuHeader;
    private String userName, permission;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView twLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");
        permission = sp.getString("Permission","Nincs adat");
        twLogin.setText(userName);
        iwMenuHeader.setImageResource(R.mipmap.ic_nav_emp_round);

    }

    private void init() {
        iwMenuHeader = findViewById(R.id.iwMenuHeader);

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
}
