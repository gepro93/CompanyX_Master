package com.example.gergo.companyx;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

public class DepartmentModify extends AppCompatActivity {

    private Button btDepartmentMod, btDepartmentModBack;
    private ListView lwDepartmentModify;
    private Database db;
    private String departmentName;
    private ProgressDialog progress;
    private ArrayList<HashMap<String, String>> departmentList;
    private ListAdapter adapter;
    private int pos;

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
                            progressDialog();
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
    }


    public void progressDialog(){
        progress = new ProgressDialog(DepartmentModify.this);
        progress.setMax(100);
        progress.setMessage("Osztály módosítása folyamatban...");
        progress.setTitle("Módosítás");
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


