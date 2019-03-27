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

public class LaptopEdit extends AppCompatActivity {

    private Button btLaptopEditMod, btLaptopEditDel, btLaptopEditBack;
    private ListView lwLaptopEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> laptopList;
    private ListAdapter adapter;
    private String modLaptopImeiNumber, modLaptopModel;
    private ArrayList<String> laptopTypeList;
    private int selectedLaptopType, selectedModLaptopType, pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_edit);
        init();
        createList();

        lwLaptopEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modLaptopModel = (String) obj.get("LAPTOPTYPE");
                modLaptopImeiNumber = (String) obj.get("LAPTOP_IMEINUMBER");
            }
        });

        btLaptopEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyLaptop();
            }
        });

        btLaptopEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLaptop(laptopList,adapter);
            }
        });

        btLaptopEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopEdit.this,LaptopHandle.class));
                Animatoo.animateSlideRight(LaptopEdit.this);
                finish();
            }
        });
    }
    private void deleteLaptop(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwLaptopEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean laptopDelete = db.laptopDelete(modLaptopImeiNumber);

            if (laptopDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Laptop törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(LaptopEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void modifyLaptop() {
        pos = lwLaptopEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(LaptopEdit.this);

            alert.setMessage("Laptop módosítása:");
            alert.setTitle("Felvétel");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(LaptopEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText laptopImeiNumber = new EditText(LaptopEdit.this);
            laptopImeiNumber.setHint("Laptop IMEI száma");
            laptopImeiNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            laptopImeiNumber.setGravity(Gravity.CENTER);
            laptopImeiNumber.setPadding(0, 0, 0, 60);
            laptopImeiNumber.setText(modLaptopImeiNumber);
            layout.addView(laptopImeiNumber); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spLaptopType = new Spinner(LaptopEdit.this, Spinner.MODE_DROPDOWN);

            laptopTypeList = db.modelOfLaptopList();

            for (int s = 0; s < laptopTypeList.size(); s++){
                if (laptopTypeList.get(s).equals(modLaptopModel)) {
                    selectedModLaptopType = s;
                }
            }

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(LaptopEdit.this, android.R.layout.simple_spinner_item, laptopTypeList);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLaptopType.setAdapter(spinnerDataAdapter);
            spLaptopType.setSelection(selectedModLaptopType);
            spLaptopType.setBackgroundResource(R.color.colorWhite);
            layout.addView(spLaptopType); //Spinner hozzáadása layouthoz
            layout.setPadding(0, 30, 0, 30);
            alert.setView(layout);

            spLaptopType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedLaptopType = adapterView.getSelectedItemPosition()+1;
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
                    String etImeiNumber = laptopImeiNumber.getText().toString();

                    if (etImeiNumber.equals("")) {
                        Toast.makeText(LaptopEdit.this, "IMEI szám megadása kötelező!", Toast.LENGTH_SHORT).show();
                    } else if (etImeiNumber.equals(modLaptopImeiNumber)) {
                        boolean modifyLaptop = db.modifyLaptop(modLaptopImeiNumber,etImeiNumber,selectedLaptopType);
                        if (modifyLaptop) {
                            ls.progressDialog(LaptopEdit.this, "Laptop módosítása folyamatban...", "Módosítás");
                            createList();
                        } else
                            Toast.makeText(LaptopEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean laptopCheck = db.laptopCheck(etImeiNumber);
                        if (!laptopCheck) {
                            boolean modifyLaptop = db.modifyLaptop(modLaptopImeiNumber,etImeiNumber, selectedLaptopType);
                            if (modifyLaptop) {
                                ls.progressDialog(LaptopEdit.this, "Laptop módosítása folyamatban...", "Módosítás");
                                createList();
                            } else
                                Toast.makeText(LaptopEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(LaptopEdit.this, "Ilyen laptop már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();
        }
    }

    private void createList() {
        laptopList = db.viewLaptops();
        adapter = new SimpleAdapter(LaptopEdit.this, laptopList, R.layout.laptop_edit_row,
                new String[]{"LAPTOPTYPE","LAPTOP_IMEINUMBER"},
                new int[]{R.id.twModelType,R.id.twImeiNumber});

        lwLaptopEdit.setAdapter(adapter);
    }

    private void init() {
        btLaptopEditMod =  findViewById(R.id.btLaptopEditMod);
        btLaptopEditDel =  findViewById(R.id.btLaptopEditDel);
        btLaptopEditBack =  findViewById(R.id.btLaptopEditBack);
        lwLaptopEdit =  findViewById(R.id.lwLaptopEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LaptopEdit.this);

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
                startActivity(new Intent(LaptopEdit.this, Login.class));
                Animatoo.animateFade(LaptopEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
