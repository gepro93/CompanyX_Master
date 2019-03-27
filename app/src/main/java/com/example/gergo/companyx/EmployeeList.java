package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeList extends AppCompatActivity {

    private Button btEmpListBack;
    private ListView lwEmpList;
    private Database db;
    private ArrayList<HashMap<String, String>> empList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        init();
        createList();

        btEmpListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeList.this, EmployeeHandle.class));
                Animatoo.animateSlideRight(EmployeeList.this);
                finish();
            }
        });
    }

    private void createList() {
        empList = db.viewEmployees();
        adapter = new SimpleAdapter(EmployeeList.this, empList, R.layout.employee_edit_row,
                new String[]{"EMPLOYEE_ID","EMPLOYEE_NAME","EMPLOYEE_GENDER","EMPLOYEE_BIRTH","EMPLOYEE_MOTHERS_NAME","EMPLOYEE_STATUS",
                        "EMPLOYEE_SALARY","DEPARTMENT_NAME","POSITION_NAME"},
                new int[]{R.id.twEmpId,R.id.twEmpName,R.id.twEmpGender,R.id.twEmpBirth,R.id.twEmpyMoName,R.id.twEmpStatus,R.id.twEmpSalary,R.id.twEmpDepName,R.id.twEmpPosName});

        lwEmpList.setAdapter(adapter);
    }

    private void init() {
        btEmpListBack = findViewById(R.id.btEmpListBack);
        lwEmpList = findViewById(R.id.lwEmpList);
        db = new Database(this);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeList.this);

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
                startActivity(new Intent(EmployeeList.this, Login.class));
                Animatoo.animateFade(EmployeeList.this);
                finish();
            }
        });
        builder.show();
    }
}
