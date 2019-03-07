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

public class MobileList extends AppCompatActivity {

    private Button btMobileListBack;
    private ListView lwMobileList;
    private Database db;
    private ArrayList<HashMap<String, String>> mobileList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_list);
        init();
        createList();

        btMobileListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileList.this, MobileHandle.class));
                finish();
            }
        });

    }

    private void init() {
        btMobileListBack = findViewById(R.id.btMobileListBack);
        lwMobileList = findViewById(R.id.lwMobileList);
        db = new Database(this);
    }

    private void createList() {
        mobileList = db.viewMobiles();
        adapter = new SimpleAdapter(MobileList.this, mobileList, R.layout.mobile_list_row,
                new String[]{"MOBILTYPE","MOBIL_IMEINUMBER"},
                new int[]{R.id.twModelType,R.id.twImeiNumber});

        lwMobileList.setAdapter(adapter);
    }
}
