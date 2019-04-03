package com.example.gergo.companyx;

import android.content.Intent;
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

public class BenefitList extends AppCompatActivity {

    private Button btBenefitListBack;
    private ListView lwBenefitList;
    private Database db;
    private ArrayList<HashMap<String, String>> benefitList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit_list);
        init();
        createList();

        btBenefitListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BenefitList.this, AdminMenu.class));
                Animatoo.animateSlideRight(BenefitList.this);
                finish();
            }
        });
    }

    private void init() {
        btBenefitListBack = findViewById(R.id.btBenefitListBack);
        lwBenefitList = findViewById(R.id.lwBenefitList);
        db = new Database(this);
    }

    private void createList() {
        benefitList = db.viewBenefits();
        adapter = new SimpleAdapter(BenefitList.this, benefitList, R.layout.benefit_list_row,
                new String[]{"tipus","dolgNev","status","datetime","fac_user_name"},
                new int[]{R.id.twBenefitItem,R.id.twEmployeeName,R.id.twStatusType,R.id.twDateTime,R.id.twFacUser});

        lwBenefitList.setAdapter(adapter);
    }


}
