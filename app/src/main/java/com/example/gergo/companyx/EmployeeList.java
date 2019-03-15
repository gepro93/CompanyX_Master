package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
}
