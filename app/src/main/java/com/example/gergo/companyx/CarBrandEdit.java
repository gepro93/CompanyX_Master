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

public class CarBrandEdit extends AppCompatActivity {

    private Button btCarBrandEditMod, btCarBrandEditDel, btCarBrandEditBack;
    private ListView lwCarBrandEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> carBrandList;
    private ArrayList<String> gradeCategory;
    private ListAdapter adapter;
    private int pos, selectedGrade, modSelectedGrade;
    private String modCarBrand, modCarType, modGrade;
    private ArrayList<String> GradeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand_edit);
        init();
        createList();

        lwCarBrandEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modCarBrand = (String) obj.get("MODELOFCAR_BRAND");
                modCarType = (String) obj.get("MODELOFCAR_TYPE");
                modGrade = (String) obj.get("GRADE_NAME");
            }
        });

        btCarBrandEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyCarBrand();
            }
        });

        btCarBrandEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCarBrand(carBrandList,adapter);
            }
        });

        btCarBrandEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarBrandEdit.this, CarBrandHandle.class));
                finish();
            }
        });
    }

    private void deleteCarBrand(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwCarBrandEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean carBrandDelete = db.carBrandDelete(modCarBrand,modCarType);

            if (carBrandDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Gyártmány törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(CarBrandEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void modifyCarBrand() {
        pos = lwCarBrandEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(CarBrandEdit.this);

            alert.setMessage("Márka módosítása:");
            alert.setTitle("Módosítás");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(CarBrandEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText brandName = new EditText(CarBrandEdit.this);
            brandName.setHint("Márka");
            brandName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            brandName.setGravity(Gravity.CENTER);
            brandName.setPadding(0, 0, 0, 60);
            brandName.setText(modCarBrand);
            layout.addView(brandName); //Edit Text hozzáadása layouthoz

            //Edit Text létrehozása
            final EditText typeName = new EditText(CarBrandEdit.this);
            typeName.setHint("Típus");
            typeName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            typeName.setGravity(Gravity.CENTER);
            typeName.setPadding(0, 60, 0, 60);
            typeName.setText(modCarType);
            layout.addView(typeName); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spGrade = new Spinner(CarBrandEdit.this, Spinner.MODE_DROPDOWN);

            gradeCategory = db.gradeList();

            for (int s = 0; s < gradeCategory.size(); s++){
                if (gradeCategory.get(s).equals(modCarType)) {
                    modSelectedGrade = s;
                }
            }

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(CarBrandEdit.this, android.R.layout.simple_spinner_item, gradeCategory);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrade.setAdapter(spinnerDataAdapter);
            spGrade.setSelection(modSelectedGrade);
            layout.addView(spGrade); //Spinner hozzáadása layouthoz

            layout.setPadding(0, 30, 0, 30);
            alert.setView(layout);

            spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedGrade = adapterView.getSelectedItemPosition();
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
                    String etBrandName = brandName.getText().toString();
                    String etTypeName = typeName.getText().toString();

                    if (etBrandName.equals("") || etTypeName.equals("")) {
                        Toast.makeText(CarBrandEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                    }else if (etBrandName.equals(modCarBrand) && etTypeName.equals(modCarType)){
                        boolean modifyCarBrand = db.modifyCarBrand(modCarBrand,modCarType,etBrandName, etTypeName, selectedGrade);
                        if (modifyCarBrand) {
                            ls.progressDialog(CarBrandEdit.this, "Márka módosítása folyamatban...", "Létrehozás");
                            createList();
                        } else
                            Toast.makeText(CarBrandEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean carBrandCheck = db.carBrandCheck(etBrandName,etTypeName);
                        if (!carBrandCheck){
                            boolean modifyCarBrand = db.modifyCarBrand(modCarBrand,modCarType,etBrandName, etTypeName, selectedGrade);
                            if (modifyCarBrand) {
                                ls.progressDialog(CarBrandEdit.this, "Márka módosítása folyamatban...", "Létrehozás");
                                createList();
                            } else
                                Toast.makeText(CarBrandEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(CarBrandEdit.this, "Ez a gyártmány már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();
        }
    }

    private void createList() {
        carBrandList = db.viewCarBrands();
        adapter = new SimpleAdapter(CarBrandEdit.this, carBrandList, R.layout.car_brand_edit_row,
                new String[]{"MODELOFCAR_BRAND","MODELOFCAR_TYPE","GRADE_NAME"},
                new int[]{R.id.twCarBrand,R.id.twCarType,R.id.twGradeName});

        lwCarBrandEdit.setAdapter(adapter);
    }

    private void init() {
        btCarBrandEditMod = findViewById(R.id.btCarBrandEditMod);
        btCarBrandEditDel = findViewById(R.id.btCarBrandEditDel);
        btCarBrandEditBack = findViewById(R.id.btCarBrandEditBack);
        lwCarBrandEdit = findViewById(R.id.lwCarBrandEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
