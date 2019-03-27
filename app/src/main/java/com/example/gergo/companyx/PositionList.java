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
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionList extends AppCompatActivity {

    private Button btPositionListBack;
    private ListView lwPositionList;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_list);
        init();

        ArrayList<HashMap<String, String>> positionList = db.viewPositions();

        ListAdapter adapter = new SimpleAdapter(PositionList.this, positionList, R.layout.position_list_row,
                new String[]{"POSITION_NAME", "GRADE_NAME", "SALARY_MIN_VALUE", "SALARY_MAX_VALUE"},
                new int[]{R.id.twPositionName, R.id.twPositionGradeName, R.id.twPositionSalaryFrom, R.id.twPositionSalaryTo});
        lwPositionList.setAdapter(adapter);

        btPositionListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionList.this, PositionMenu.class));
                Animatoo.animateSlideRight(PositionList.this);
                finish();
            }

        });
    }

    public void init(){
        btPositionListBack = (Button) findViewById(R.id.btPositionListBack);
        lwPositionList = (ListView) findViewById(R.id.lwPositionList);
        db = new Database(this);
    }


    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PositionList.this);

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
                startActivity(new Intent(PositionList.this, Login.class));
                Animatoo.animateFade(PositionList.this);
                finish();
            }
        });
        builder.show();
    }
}
