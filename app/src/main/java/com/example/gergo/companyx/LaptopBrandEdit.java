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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;

public class LaptopBrandEdit extends AppCompatActivity {

    private Button btLaptopBrandEditMod, btLaptopBrandEditDel, btLaptopBrandEditBack;
    private ListView lwLaptopBrandEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> laptopList;
    private ListAdapter adapter;
    private String modLaptopBrand, modLaptopType, modGrade;
    private int pos, selectedGrade, modSelectedGrade;
    private ArrayList<String> gradeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_brand_edit);
        init();
        createList();

        lwLaptopBrandEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modLaptopBrand = (String) obj.get("MODELOFLAPTOP_BRAND");
                modLaptopType = (String) obj.get("MODELOFLAPTOP_TYPE");
                modGrade = (String) obj.get("GRADE_NAME");
            }
        });

        btLaptopBrandEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyLaptopBrand();
            }
        });

        btLaptopBrandEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLaptopBrand(laptopList,adapter);
            }
        });

        btLaptopBrandEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopBrandEdit.this, LaptopBrandHandle.class));
                Animatoo.animateSlideRight(LaptopBrandEdit.this);
                finish();
            }
        });
    }

    private void modifyLaptopBrand() {
        pos = lwLaptopBrandEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(LaptopBrandEdit.this);

            alert.setMessage("Márka módosítása:");
            alert.setTitle("Módosítás");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(LaptopBrandEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText brandName = new EditText(LaptopBrandEdit.this);
            brandName.setHint("Márka");
            brandName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            brandName.setGravity(Gravity.CENTER);
            brandName.setPadding(0, 0, 0, 60);
            brandName.setText(modLaptopBrand);
            layout.addView(brandName); //Edit Text hozzáadása layouthoz

            //Edit Text létrehozása
            final EditText typeName = new EditText(LaptopBrandEdit.this);
            typeName.setHint("Típus");
            typeName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            typeName.setGravity(Gravity.CENTER);
            typeName.setPadding(0, 60, 0, 60);
            typeName.setText(modLaptopType);
            layout.addView(typeName); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spGrade = new Spinner(LaptopBrandEdit.this, Spinner.MODE_DROPDOWN);

            gradeCategory = db.gradeList();

            for (int s = 0; s < gradeCategory.size(); s++){
                if (gradeCategory.get(s).equals(modGrade)) {
                    modSelectedGrade = s;
                }
            }

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(LaptopBrandEdit.this, android.R.layout.simple_spinner_item, gradeCategory);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGrade.setAdapter(spinnerDataAdapter);
            spGrade.setSelection(modSelectedGrade);
            spGrade.setBackgroundResource(R.color.colorWhite);
            layout.addView(spGrade); //Spinner hozzáadása layouthoz

            layout.setPadding(0, 30, 0, 30);
            alert.setView(layout);

            spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedGrade = adapterView.getSelectedItemPosition()+1;
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
                    brandName.setText(brandName.getText().toString().replaceAll(" ","_"));
                    typeName.setText(typeName.getText().toString().replaceAll(" ","_"));
                    String etBrandName = brandName.getText().toString();
                    String etTypeName = typeName.getText().toString();

                    if (etBrandName.equals("") || etTypeName.equals("")) {
                        Toast.makeText(LaptopBrandEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                    }else if (etBrandName.equals(modLaptopBrand) && etTypeName.equals(modLaptopType)){
                        boolean modifyLaptopBrand = db.modifyLaptopBrand(modLaptopBrand,modLaptopType,etBrandName, etTypeName, selectedGrade);
                        if (modifyLaptopBrand) {
                            ls.progressDialog(LaptopBrandEdit.this, "Márka módosítása folyamatban...", "Módosítás");
                            createList();
                        } else
                            Toast.makeText(LaptopBrandEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean laptopBrandCheck = db.laptopBrandCheck(etBrandName,etTypeName);
                        if (!laptopBrandCheck){
                            boolean modifyLaptopBrand = db.modifyLaptopBrand(modLaptopBrand,modLaptopType,etBrandName, etTypeName, selectedGrade);
                            if (modifyLaptopBrand) {
                                ls.progressDialog(LaptopBrandEdit.this, "Márka módosítása folyamatban...", "Módosítás");
                                createList();
                            } else
                                Toast.makeText(LaptopBrandEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(LaptopBrandEdit.this, "Ez a gyártmány már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();
        }
    }

    private void deleteLaptopBrand(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwLaptopBrandEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean laptopBrandDelete = db.laptopBrandDelete(modLaptopBrand,modLaptopType);

            if (laptopBrandDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Gyártmány törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(LaptopBrandEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void createList() {
        laptopList = db.viewLaptopBrands();
        adapter = new SimpleAdapter(LaptopBrandEdit.this, laptopList, R.layout.laptop_brand_edit_row,
                new String[]{"MODELOFLAPTOP_BRAND","MODELOFLAPTOP_TYPE","GRADE_NAME"},
                new int[]{R.id.twLaptopBrand,R.id.twLaptopType,R.id.twGradeName});

        lwLaptopBrandEdit.setAdapter(adapter);
    }

    private void init() {
        btLaptopBrandEditMod = findViewById(R.id.btLaptopBrandEditMod);
        btLaptopBrandEditDel = findViewById(R.id.btLaptopBrandEditDel);
        btLaptopBrandEditBack = findViewById(R.id.btLaptopBrandEditBack);
        lwLaptopBrandEdit = findViewById(R.id.lwLaptopBrandEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LaptopBrandEdit.this);

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
                startActivity(new Intent(LaptopBrandEdit.this, Login.class));
                Animatoo.animateFade(LaptopBrandEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
