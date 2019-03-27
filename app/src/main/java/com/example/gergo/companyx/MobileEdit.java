package com.example.gergo.companyx;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MobileEdit extends AppCompatActivity {

    private Button btMobileEditMod, btMobileEditDel, btMobileEditBack;
    private ListView lwMobileEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> mobileList;
    private ListAdapter adapter;
    private String modMobileImeiNumber, modMobileModel;
    private ArrayList<String> mobileTypeList;
    private int selectedMobileType, selectedModMobileType, pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_edit);
        init();
        createList();

        lwMobileEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modMobileModel = (String) obj.get("MOBILTYPE");
                modMobileImeiNumber = (String) obj.get("MOBIL_IMEINUMBER");
            }
        });

        btMobileEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyMobile();
            }
        });

        btMobileEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMobile(mobileList,adapter);
            }
        });

        btMobileEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileEdit.this,MobileHandle.class));
                Animatoo.animateSlideRight(MobileEdit.this);
                finish();
            }
        });

    }

    private void deleteMobile(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwMobileEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean mobileDelete = db.mobileDelete(modMobileImeiNumber);

            if (mobileDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Mobil törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(MobileEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void modifyMobile() {
        pos = lwMobileEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MobileEdit.this);

            alert.setMessage("Mobil módosítása:");
            alert.setTitle("Felvétel");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(MobileEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText mobileImeiNumber = new EditText(MobileEdit.this);
            mobileImeiNumber.setHint("Mobil IMEI száma");
            mobileImeiNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            mobileImeiNumber.setGravity(Gravity.CENTER);
            mobileImeiNumber.setPadding(0, 0, 0, 60);
            mobileImeiNumber.setText(modMobileImeiNumber);
            layout.addView(mobileImeiNumber); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spMobileType = new Spinner(MobileEdit.this, Spinner.MODE_DROPDOWN);

            mobileTypeList = db.modelOfMobileList();

            for (int s = 0; s < mobileTypeList.size(); s++){
                if (mobileTypeList.get(s).equals(modMobileModel)) {
                    selectedModMobileType = s;
                }
            }

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(MobileEdit.this, android.R.layout.simple_spinner_item, mobileTypeList);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMobileType.setAdapter(spinnerDataAdapter);
            spMobileType.setSelection(selectedModMobileType);
            spMobileType.setBackgroundResource(R.color.colorWhite);
            layout.addView(spMobileType); //Spinner hozzáadása layouthoz
            layout.setPadding(0, 30, 0, 30);
            alert.setView(layout);

            spMobileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedMobileType = adapterView.getSelectedItemPosition()+1;
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
                    String etImeiNumber = mobileImeiNumber.getText().toString();

                    if (etImeiNumber.equals("")) {
                        Toast.makeText(MobileEdit.this, "IMEI szám megadása kötelező!", Toast.LENGTH_SHORT).show();
                    } else if (etImeiNumber.equals(modMobileImeiNumber)) {
                        boolean modifyMobile = db.modifyMobile(modMobileImeiNumber,etImeiNumber,selectedMobileType);
                        if (modifyMobile) {
                            ls.progressDialog(MobileEdit.this, "Mobil módosítása folyamatban...", "Módosítás");
                            createList();
                        } else
                            Toast.makeText(MobileEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean mobileCheck = db.mobileCheck(etImeiNumber);
                        if (!mobileCheck) {
                            boolean modifyMobile = db.modifyMobile(modMobileImeiNumber,etImeiNumber, selectedMobileType);
                            if (modifyMobile) {
                                ls.progressDialog(MobileEdit.this, "Mobil módosítása folyamatban...", "Módosítás");
                                createList();
                            } else
                                Toast.makeText(MobileEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(MobileEdit.this, "Ilyen mobil már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();
        }
    }

    private void createList() {
        mobileList = db.viewMobiles();
        adapter = new SimpleAdapter(MobileEdit.this, mobileList, R.layout.mobile_edit_row,
                new String[]{"MOBILTYPE","MOBIL_IMEINUMBER"},
                new int[]{R.id.twModelType,R.id.twImeiNumber});

        lwMobileEdit.setAdapter(adapter);
    }

    private void init() {
        btMobileEditMod =  findViewById(R.id.btMobileEditMod);
        btMobileEditDel =  findViewById(R.id.btMobileEditDel);
        btMobileEditBack =  findViewById(R.id.btMobileEditBack);
        lwMobileEdit =  findViewById(R.id.lwMobileEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileEdit.this);

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
                startActivity(new Intent(MobileEdit.this, Login.class));
                Animatoo.animateFade(MobileEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
