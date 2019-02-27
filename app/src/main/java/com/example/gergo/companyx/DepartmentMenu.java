package com.example.gergo.companyx;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentMenu extends AppCompatActivity {

    private Button btDepartmentCreate, btDepartmentModify, btDepartmentDelete, btDepartmentList,  btDepartmentMenuBack;
    private Database db;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_menu);
        init();

        btDepartmentMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, AdminMenu.class));
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
                finish();
            }
        });

        btDepartmentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, DepartmentDelete.class));
                finish();
            }
        });

        btDepartmentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentMenu.this, DepartmentList.class));
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
                            progressDialog();
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
    }

    public void progressDialog(){
        progress = new ProgressDialog(DepartmentMenu.this);
        progress.setMax(100);
        progress.setMessage("Osztály létrehozása folyamatban...");
        progress.setTitle("Létrehozás");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(progress.getProgress() <= progress.getMax()){
                        Thread.sleep(20);
                        handler.sendMessage(handler.obtainMessage());
                        if(progress.getProgress() == progress.getMax()){
                            progress.dismiss();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();}

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.incrementProgressBy(1);
        }
    };
}
