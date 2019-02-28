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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;


public class DepartmentModify extends AppCompatActivity {

    private Button btDepartmentMod, btDepartmentModBack;
    private ListView lwDepartmentModify;
    private Database db;
    private String departmentName;
    private ArrayList<HashMap<String, String>> departmentList;
    private ListAdapter adapter;
    private int pos;
    private LoadScreen ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_modify);
        init();

        departmentList = db.viewDepartments();
        adapter = new SimpleAdapter(DepartmentModify.this, departmentList, R.layout.department_mod_row,
                new String[]{"DEPARTMENT_NAME"},
                new int[]{R.id.twDepartmentName});

        lwDepartmentModify.setAdapter(adapter);

        lwDepartmentModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                departmentName = (String) obj.get("DEPARTMENT_NAME");
            }
        });

        btDepartmentModBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentModify.this, DepartmentMenu.class));
                finish();
            }
        });

        btDepartmentMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                departmentModify(departmentList,adapter);
            }
        });
    }

    private void departmentModify(final ArrayList<HashMap<String,String>> arrayList, final ListAdapter listAdapter){
        pos = lwDepartmentModify.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(DepartmentModify.this);

            alert.setMessage("Osztály megnevezése:");
            alert.setTitle("Módosítás");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(DepartmentModify.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText department = new EditText(DepartmentModify.this);
            department.setHint("Osztály");
            department.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            department.setGravity(Gravity.CENTER);
            department.setPadding(0, 0, 0, 60);
            department.setText(departmentName);
            layout.addView(department); //Edit Text hozzáadása layouthoz

            alert.setView(layout);

            alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String etDepartment = department.getText().toString();
                    boolean departmentCheck = db.departmentCheck(etDepartment);

                    if (etDepartment.equals("")) {
                        department.setError("A mező kitöltése kötelező!");
                    } else if (departmentCheck) {
                        department.setError("Az osztály már létezik!");
                    } else {
                        boolean positionModify = db.departmentModify(departmentName, etDepartment);
                        if (positionModify) {
                            ls.progressDialog(DepartmentModify.this,"Módosítás folyamatban...","Módosítás");
                        } else
                            Toast.makeText(DepartmentModify.this, "Adatbázis hiba módosításkor!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.show();
        }
    }

    private void init() {
        btDepartmentMod = findViewById(R.id.btDepartmentMod);
        btDepartmentModBack = findViewById(R.id.btDepartmentModBack);
        lwDepartmentModify = findViewById(R.id.lwDepartmentModify);
        db = new Database(this);
        ls = new LoadScreen();
    }

}


