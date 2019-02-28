package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentDelete extends AppCompatActivity {

    private Button btDepartmentDeleteBack, btDepartmentDeleteExec;
    private ListView lwDepartmentDelete;
    private Database db;
    private String departmentNameForDelete;
    private ArrayList<HashMap<String, String>> departmentList;
    private ListAdapter adapter;
    private LoadScreen ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_delete);
        init();

        departmentList = db.viewDepartments();

        adapter = new SimpleAdapter(DepartmentDelete.this, departmentList, R.layout.department_mod_row,
                new String[]{"DEPARTMENT_NAME"},
                new int[]{R.id.twDepartmentName});

        lwDepartmentDelete.setAdapter(adapter);

        lwDepartmentDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                departmentNameForDelete = (String) obj.get("DEPARTMENT_NAME");
            }
        });

        btDepartmentDeleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DepartmentDelete.this, DepartmentMenu.class));
                finish();
            }
        });

        btDepartmentDeleteExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DepartmentDelete.this);

                builder.setCancelable(true);
                builder.setTitle("Törlés");
                builder.setMessage("Biztosan törlöd az osztályt?");

                builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        departmentDelete(departmentList, adapter); //Osztály törlése
                    }
                });
                builder.show();
            }
        });

    }

    private void init() {
        btDepartmentDeleteBack = findViewById(R.id.btDepartmentDeleteBack);
        btDepartmentDeleteExec = findViewById(R.id.btDepartmentDeleteExec);
        lwDepartmentDelete = findViewById(R.id.lwDepartmentDelete);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void departmentDelete(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwDepartmentDelete.getCheckedItemPosition();


        if (pos > -1) {
            Boolean departmentDelete = db.departmentDelete(departmentNameForDelete);

            if (departmentDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Osztály törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(DepartmentDelete.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }
}

