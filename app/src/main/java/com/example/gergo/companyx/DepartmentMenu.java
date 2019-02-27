package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepartmentMenu extends AppCompatActivity {

    private Button btDepartmentCreate, btDepartmentModify, btDepartmentDelete, btDepartmentList,  btDepartmentMenuBack;

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
                startActivity(new Intent(DepartmentMenu.this, AdminMenu.class));
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

    private void init() {
        btDepartmentCreate = findViewById(R.id.btDepartmentCreate);
        btDepartmentModify = findViewById(R.id.btDepartmentModify);
        btDepartmentDelete = findViewById(R.id.btDepartmentDelete);
        btDepartmentList = findViewById(R.id.btDepartmentList);
        btDepartmentMenuBack = findViewById(R.id.btDepartmentMenuBack);
    }
}
