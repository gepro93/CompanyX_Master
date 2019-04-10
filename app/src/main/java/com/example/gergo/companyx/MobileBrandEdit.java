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

public class MobileBrandEdit extends AppCompatActivity {

    private Button btMobileBrandEditMod, btMobileBrandEditDel, btMobileBrandEditBack;
    private ListView lwMobileBrandEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> mobileList;
    private ListAdapter adapter;
    private String modMobileBrand, modMobileType, modGrade;
    private int pos, selectedGrade, modSelectedGrade;
    private ArrayList<String> gradeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_brand_edit);
        init();
        createList();

        lwMobileBrandEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modMobileBrand = (String) obj.get("MODELOFMOBIL_BRAND");
                modMobileType = (String) obj.get("MODELOFMOBIL_TYPE");
                modGrade = (String) obj.get("GRADE_NAME");
            }
        });

        btMobileBrandEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyMobileBrand();
            }
        });

        btMobileBrandEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMobileBrand(mobileList,adapter);
            }
        });

        btMobileBrandEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileBrandEdit.this, MobileBrandHandle.class));
                Animatoo.animateSlideRight(MobileBrandEdit.this);
                finish();
            }
        });
    }

    private void modifyMobileBrand() {
        pos = lwMobileBrandEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MobileBrandEdit.this);

            alert.setMessage("Márka módosítása:");
            alert.setTitle("Módosítás");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(MobileBrandEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText brandName = new EditText(MobileBrandEdit.this);
            brandName.setHint("Márka");
            brandName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            brandName.setGravity(Gravity.CENTER);
            brandName.setPadding(0, 0, 0, 60);
            brandName.setText(modMobileBrand);
            layout.addView(brandName); //Edit Text hozzáadása layouthoz

            //Edit Text létrehozása
            final EditText typeName = new EditText(MobileBrandEdit.this);
            typeName.setHint("Típus");
            typeName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            typeName.setGravity(Gravity.CENTER);
            typeName.setPadding(0, 60, 0, 60);
            typeName.setText(modMobileType);
            layout.addView(typeName); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spGrade = new Spinner(MobileBrandEdit.this, Spinner.MODE_DROPDOWN);

            gradeCategory = db.gradeList();

            for (int s = 0; s < gradeCategory.size(); s++){
                if (gradeCategory.get(s).equals(modGrade)) {
                    modSelectedGrade = s;
                }
            }

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(MobileBrandEdit.this, android.R.layout.simple_spinner_item, gradeCategory);
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
                        Toast.makeText(MobileBrandEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                    }else if (etBrandName.equals(modMobileBrand) && etTypeName.equals(modMobileType)){
                        boolean modifyMobileBrand = db.modifyMobileBrand(modMobileBrand,modMobileType,etBrandName, etTypeName, selectedGrade);
                        if (modifyMobileBrand) {
                            ls.progressDialog(MobileBrandEdit.this, "Márka módosítása folyamatban...", "Módosítás");
                            createList();
                        } else
                            Toast.makeText(MobileBrandEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean mobileBrandCheck = db.mobileBrandCheck(etBrandName,etTypeName);
                        if (!mobileBrandCheck){
                            boolean modifyMobileBrand = db.modifyMobileBrand(modMobileBrand,modMobileType,etBrandName, etTypeName, selectedGrade);
                            if (modifyMobileBrand) {
                                ls.progressDialog(MobileBrandEdit.this, "Márka módosítása folyamatban...", "Módosítás");
                                createList();
                            } else
                                Toast.makeText(MobileBrandEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(MobileBrandEdit.this, "Ez a gyártmány már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();
        }
    }

    private void deleteMobileBrand(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwMobileBrandEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean mobileBrandDelete = db.mobileBrandDelete(modMobileBrand,modMobileType);

            if (mobileBrandDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Gyártmány törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(MobileBrandEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void createList() {
        mobileList = db.viewMobileBrands();
        adapter = new SimpleAdapter(MobileBrandEdit.this, mobileList, R.layout.mobile_brand_edit_row,
                new String[]{"MODELOFMOBIL_BRAND","MODELOFMOBIL_TYPE","GRADE_NAME"},
                new int[]{R.id.twMobileBrand,R.id.twMobileType,R.id.twGradeName});

        lwMobileBrandEdit.setAdapter(adapter);
    }

    private void init() {
        btMobileBrandEditMod = findViewById(R.id.btMobileBrandEditMod);
        btMobileBrandEditDel = findViewById(R.id.btMobileBrandEditDel);
        btMobileBrandEditBack = findViewById(R.id.btMobileBrandEditBack);
        lwMobileBrandEdit = findViewById(R.id.lwMobileBrandEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileBrandEdit.this);

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
                startActivity(new Intent(MobileBrandEdit.this, Login.class));
                Animatoo.animateFade(MobileBrandEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
