package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMenu extends AppCompatActivity {

    private TextView twlogin;
    private Button btUser, btEmployee, btPosition, btDepartment, btCar, btMobile, btLaptop, btGrade, btTrip, btTrans, btLog, btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginUserName",MODE_PRIVATE);
        String LoginUserName = sp.getString("LoginUserName","Nincs adat");

        twlogin.setText("Belejentkezve mint: " + LoginUserName);

        btUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this, UserMenu.class));
                finish();
            }
        });

        btPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this, PositionMenu.class));
                finish();
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this, Login.class));
                finish();
            }
        });

    }

    public void init(){
        twlogin = (TextView) findViewById(R.id.twlogin);
        btUser = (Button) findViewById(R.id.btUser);
        btEmployee = (Button) findViewById(R.id.btEmployee);
        btPosition = (Button) findViewById(R.id.btPosition);
        btDepartment = (Button) findViewById(R.id.btDepartment);
        btCar = (Button) findViewById(R.id.btCar);
        btMobile = (Button) findViewById(R.id.btMobile);
        btLaptop = (Button) findViewById(R.id.btLaptop);
        btGrade = (Button) findViewById(R.id.btGrade);
        btTrip = (Button) findViewById(R.id.btTrip);
        btTrans = (Button) findViewById(R.id.btTrans);
        btLog = (Button) findViewById(R.id.btLog);
        btLogout = (Button) findViewById(R.id.btLogout);
    }

    public void onBackPressed(){
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
