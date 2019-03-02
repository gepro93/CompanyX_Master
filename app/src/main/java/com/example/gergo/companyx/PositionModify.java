package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionModify extends AppCompatActivity {

    private Button btPositionMod, btPositionModBack;
    private ListView lwPositionModify;
    private Database db;
    private String positionName, positionGradeName;
    private int gradeId, modGradeId;
    private LoadScreen ls;
    private ArrayList<HashMap<String, String>> positionList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_modify);
        init();

        createList();

        lwPositionModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                positionName = (String) obj.get("POSITION_NAME");
                positionGradeName = (String) obj.get("GRADE_NAME");
            }
        });

        btPositionMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionModify(positionList,adapter);
            }
        });


        btPositionModBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionModify.this,PositionMenu.class));
                finish();
            }
        });

    }

    private void createList() {
        positionList = db.viewPositions();
        adapter = new SimpleAdapter(PositionModify.this, positionList, R.layout.position_del_row,
                new String[]{"POSITION_NAME", "GRADE_NAME", "SALARY_MIN_VALUE", "SALARY_MAX_VALUE"},
                new int[]{R.id.twPositionName, R.id.twPositionGradeName, R.id.twPositionSalaryFrom, R.id.twPositionSalaryTo});

        lwPositionModify.setAdapter(adapter);
    }

    public void init(){
        btPositionMod = (Button) findViewById(R.id.btPositionMod);
        btPositionModBack = (Button) findViewById(R.id.btPositionModBack);
        lwPositionModify = (ListView) findViewById(R.id.lwPositionModify);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void positionModify(ArrayList<HashMap<String,String>> arrayList, final ListAdapter listAdapter){
        int pos  = lwPositionModify.getCheckedItemPosition();

        if (pos > -1)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(PositionModify.this);

            alert.setMessage("Beosztás megnevezése:");
            alert.setTitle("Módosítás");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(PositionModify.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText position = new EditText(PositionModify.this);
            position.setHint("Beosztás");
            position.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            position.setGravity(Gravity.CENTER);
            position.setPadding(0,0,0,60);
            position.setText(positionName);
            layout.addView(position); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner grade = new Spinner(PositionModify.this,Spinner.MODE_DROPDOWN);

            //Spinner tartalmának elkészítése listában
            final List<String> grades = new ArrayList<>();
            grades.add(0,"Grade 1");
            grades.add("Grade 2");
            grades.add("Grade 3");
            grades.add("Grade 4");
            grades.add("Grade 5");
            grades.add("Grade 6");

            switch (positionGradeName){
                case "Grade 1": modGradeId = 0; break;
                case "Grade 2": modGradeId = 1; break;
                case "Grade 3": modGradeId = 2; break;
                case "Grade 4": modGradeId = 3; break;
                case "Grade 5": modGradeId = 4; break;
                case "Grade 6": modGradeId = 5; break;
            }

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(PositionModify.this, android.R.layout.simple_spinner_item, grades);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            grade.setAdapter(spinnerDataAdapter);
            grade.setSelection(modGradeId);
            layout.addView(grade); //Spinner hozzáadása layouthoz

            layout.setPadding(0,30,0,30);
            alert.setView(layout);

            grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    gradeId = adapterView.getSelectedItemPosition()+1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String etPosition = position.getText().toString();
                    boolean positionCheck = db.positionCheck(etPosition);

                    if (etPosition.equals("")){
                        position.setError("A mező kitöltése kötelező!");
                    }else if(positionCheck) {
                        position.setError("A beosztás már létezik!");
                    }else{
                        boolean positionModify = db.positionModify(positionName,etPosition,gradeId);
                        if (positionModify){
                            ls.progressDialog(PositionModify.this,"Módosítás folyamatban...", "Módosítás");
                            createList();
                        }else Toast.makeText(PositionModify.this, "Adatbázis hiba módosításkor!", Toast.LENGTH_SHORT).show();
                    }
            }});
            alert.show();
        }
    }

}
