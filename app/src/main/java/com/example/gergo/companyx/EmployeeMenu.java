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
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class EmployeeMenu extends AppCompatActivity {

    private ImageView iwMenuHeader, iwEmp;
    private String userName, permission, empName, empDep, empPos;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView twLogin, twName, twDep, twPos;
    private Database db;
    private boolean itemBenefitCheckByUserName;

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
        iwEmp.setImageResource(R.mipmap.ic_nav_emp_round);

        empName = db.empName(userName);
        empDep = db.empDep(userName);
        empPos = db.empPos(userName);

        twName.setText(empName);
        twDep.setText(empDep);
        twPos.setText(empPos);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navCarMenu:
                        itemBenefitCheckByUserName = db.itemBenefitCheckByUserName("a",empName);
                        if (itemBenefitCheckByUserName){
                            startActivity(new Intent(EmployeeMenu.this,EmployeeCarMenu.class));
                            Animatoo.animateSlideLeft(EmployeeMenu.this);
                            finish();
                        }else Toast.makeText(EmployeeMenu.this, "Jelenleg Neked nincs kiadva autó!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.navMobileMenu:
                        itemBenefitCheckByUserName = db.itemBenefitCheckByUserName("m",empName);
                        if (itemBenefitCheckByUserName){
                            startActivity(new Intent(EmployeeMenu.this,EmployeeCarMenu.class));
                            Animatoo.animateSlideLeft(EmployeeMenu.this);
                            finish();
                        }else Toast.makeText(EmployeeMenu.this, "Jelenleg Neked nincs kiadva mobil!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.navLaptopMenu:
                        itemBenefitCheckByUserName = db.itemBenefitCheckByUserName("l",empName);
                        if (itemBenefitCheckByUserName){
                            startActivity(new Intent(EmployeeMenu.this,EmployeeCarMenu.class));
                            Animatoo.animateSlideLeft(EmployeeMenu.this);
                            finish();
                        }else Toast.makeText(EmployeeMenu.this, "Jelenleg Neked nincs kiadva laptop!", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.navLogout:
                        startActivity(new Intent(EmployeeMenu.this, Login.class));
                        Animatoo.animateFade(EmployeeMenu.this);
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    private void init() {
        iwMenuHeader = findViewById(R.id.iwMenuHeader);
        iwEmp = findViewById(R.id.iwEmp);
        twName = findViewById(R.id.twName);
        twDep = findViewById(R.id.twDep);
        twPos = findViewById(R.id.twPos);
        db = new Database(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Főmenü");
        drawer = findViewById(R.id.employee_layout);
        navigationView = findViewById(R.id.nav_view_employee);
        twLogin = findViewById(R.id.twLogin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeMenu.this);

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
                startActivity(new Intent(EmployeeMenu.this, Login.class));
                Animatoo.animateFade(EmployeeMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
