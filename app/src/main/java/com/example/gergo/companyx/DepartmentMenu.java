package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;


public class DepartmentMenu extends AppCompatActivity {

    private Button btDepartmentCreate, btDepartmentModify, btDepartmentDelete, btDepartmentList,  btDepartmentMenuBack;
    private Database db;
    private LoadScreen ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_menu);
        init();

        btDepartmentMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, AdminMenu.class));
                Animatoo.animateSlideRight(DepartmentMenu.this);
                finish();
            }
        });

        btDepartmentCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPermission();
            }
        });

        btDepartmentModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, DepartmentModify.class));
                Animatoo.animateSlideLeft(DepartmentMenu.this);
                finish();
            }
        });

        btDepartmentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, DepartmentDelete.class));
                Animatoo.animateSlideLeft(DepartmentMenu.this);
                finish();
            }
        });

        btDepartmentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, DepartmentList.class));
                Animatoo.animateSlideLeft(DepartmentMenu.this);
                finish();
            }
        });

    }

    private void createPermission() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DepartmentMenu.this);

        alert.setMessage("Osztály megnevezése:");
        alert.setTitle("Létrehozás");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(DepartmentMenu.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText department = new EditText(DepartmentMenu.this);

        department.setHint("Osztály");
        department.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        department.setGravity(Gravity.CENTER);
        department.setPadding(0,0,0,60);
        layout.addView(department); //Edit Text hozzáadása layouthoz

        alert.setView(layout);

        alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String etDepartment = department.getText().toString();

                if (etDepartment.equals("")){
                    department.setError("Üresen hagyott mező!");
                }else{
                    boolean departmentCheck = db.departmentCheck(etDepartment);
                    if (!departmentCheck){
                        boolean createDepartment = db.insertDepartment(etDepartment);
                        if (createDepartment){
                            ls.progressDialog(DepartmentMenu.this,"Létrehozás folyamatban...", "Létrehozás");
                        }else Toast.makeText(DepartmentMenu.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.setPositiveButton("Mégse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }


    private void init() {
        btDepartmentCreate = findViewById(R.id.btDepartmentCreate);
        btDepartmentModify = findViewById(R.id.btDepartmentModify);
        btDepartmentDelete = findViewById(R.id.btDepartmentDelete);
        btDepartmentList = findViewById(R.id.btDepartmentList);
        btDepartmentMenuBack = findViewById(R.id.btDepartmentMenuBack);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DepartmentMenu.this);

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
                startActivity(new Intent(DepartmentMenu.this, Login.class));
                Animatoo.animateFade(DepartmentMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
