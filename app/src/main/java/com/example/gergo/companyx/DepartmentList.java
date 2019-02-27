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

public class DepartmentList extends AppCompatActivity {

    private Button btDepartmentListBack;
    private ListView lwDepartmentList;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        init();

        ArrayList<HashMap<String, String>> departmentList = db.viewDepartments();

        ListAdapter adapter = new SimpleAdapter(DepartmentList.this, departmentList, R.layout.department_list_row,
                new String[]{"DEPARTMENT_NAME"},
                new int[]{R.id.twDepartmentName});

        lwDepartmentList.setAdapter(adapter);

        btDepartmentListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentList.this, DepartmentMenu.class));
                finish();
            }
        });

    }

    private void init() {
        btDepartmentListBack = findViewById(R.id.btDepartmentListBack);
        lwDepartmentList = findViewById(R.id.lwDepartmentList);
        db = new Database(this);
    }
}
