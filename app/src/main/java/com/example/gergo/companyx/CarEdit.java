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
import android.view.inputmethod.EditorInfo;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CarEdit extends AppCompatActivity {

    private Button btCarEditMod, btCarEditDel, btCarEditBack;
    private ListView lwCarEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> carList;
    private ListAdapter adapter;
    private String modCarType, modLicenseeNumber, modMotDate, modVinNumber;
    private ArrayList<String> carTypeList;
    private int selectedCarType, selectedModCarType, pos;
    private Calendar c;
    private DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_edit);
        init();

        createList();

        lwCarEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modCarType = (String) obj.get("CARTYPE");
                modLicenseeNumber = (String) obj.get("CAR_LICENSENUMBER");
                modMotDate = (String) obj.get("MOTDATE");
                modVinNumber = (String) obj.get("CAR_VINNUMBER");
            }
        });

        btCarEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarEdit.this, CarHandle.class));
                finish();
            }
        });

        btCarEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carModify();
            }
        });

        btCarEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CarEdit.this);

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
                        carDelete(carList, adapter); //Osztály törlése
                    }
                });
                builder.show();
            }
        });

    }

    private void carDelete(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwCarEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean carDelete = db.carDelete(modLicenseeNumber);

            if (carDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Autó törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(CarEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void carModify() {
        pos = lwCarEdit.getCheckedItemPosition();

        if (pos > -1) {
        AlertDialog.Builder alert = new AlertDialog.Builder(CarEdit.this);

        alert.setMessage("Autó módosítása:");
        alert.setTitle("Módosítás");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(CarEdit.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText licenseNumber = new EditText(CarEdit.this);
        licenseNumber.setHint("Rendszám");
        licenseNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        licenseNumber.setGravity(Gravity.CENTER);
        licenseNumber.setPadding(0, 0, 0, 60);
        licenseNumber.setText(modLicenseeNumber);
        layout.addView(licenseNumber); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText vinNumber = new EditText(CarEdit.this);
        vinNumber.setHint("Alvázszám");
        vinNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        vinNumber.setGravity(Gravity.CENTER);
        vinNumber.setPadding(0, 60, 0, 60);
        vinNumber.setText(modVinNumber);
        layout.addView(vinNumber); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText motDate = new EditText(CarEdit.this);
        motDate.setHint("Műszaki érvényessége");
        motDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        motDate.setGravity(Gravity.CENTER);
        motDate.setText(modMotDate);
        motDate.setPadding(0, 60, 0, 60);
        layout.addView(motDate); //Edit Text hozzáadása layouthoz

        motDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                dpd = new DatePickerDialog(CarEdit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        if(mMonth < 10 && mDay < 10){
                            motDate.setText(mYear + "-0" + (mMonth+1) + "-0" + mDay);
                        }else if (mMonth < 10){
                            motDate.setText(mYear + "-0" + (mMonth+1) + "-" + mDay);
                        }else if(mDay < 10){
                            motDate.setText(mYear + "-" + (mMonth+1) + "-0" + mDay);
                        }else motDate.setText(mYear + "-" + (mMonth+1) + "-" + mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });



        //Spinner létrehozása
        final Spinner spCarType = new Spinner(CarEdit.this, Spinner.MODE_DROPDOWN);

        carTypeList = db.modelOfCarList();

        for (int s = 0; s < carTypeList.size(); s++){
            if (carTypeList.get(s).equals(modCarType)) {
                selectedModCarType = s;
            }
        }

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(CarEdit.this, android.R.layout.simple_spinner_item, carTypeList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarType.setAdapter(spinnerDataAdapter);
        spCarType.setSelection(selectedModCarType);
        layout.addView(spCarType); //Spinner hozzáadása layouthoz

        layout.setPadding(0, 30, 0, 30);
        alert.setView(layout);

        spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCarType = adapterView.getSelectedItemPosition()+1;
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
                String etLicenseNumber = licenseNumber.getText().toString();
                String etVinNumber = vinNumber.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                String etMotDate = motDate.getText().toString();

                Date formattedMotDate = null;
                try {
                    formattedMotDate = formatter.parse(etMotDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (etLicenseNumber.equals("") || etVinNumber.equals("") || etMotDate.equals("")) {
                    Toast.makeText(CarEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }else {
                    if (modLicenseeNumber.equals(etLicenseNumber) && modVinNumber.equals(etVinNumber)){
                        boolean carModify = db.carModify(modLicenseeNumber,etLicenseNumber,etVinNumber, formattedMotDate,selectedCarType);
                        if (carModify) {
                            ls.progressDialog(CarEdit.this, "Autó módosítása folyamatban...", "Módosítás");
                            createList();
                        } else
                            Toast.makeText(CarEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                        boolean carCheck = db.carCheck(etLicenseNumber,etVinNumber);
                        if (!carCheck){
                            boolean carModify = db.carModify(modLicenseeNumber,etLicenseNumber,etVinNumber, formattedMotDate,selectedCarType);
                            if (carModify) {
                                ls.progressDialog(CarEdit.this, "Autó módosítása folyamatban...", "Módosítás");
                                createList();
                            } else
                                Toast.makeText(CarEdit.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(CarEdit.this, "Ilyen autó már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.show();}
    }

    private void createList() {
        carList = db.viewCars();
        adapter = new SimpleAdapter(CarEdit.this, carList, R.layout.car_edit_row,
                new String[]{"CAR_LICENSENUMBER","CAR_VINNUMBER","MOTDATE","CARTYPE"},
                new int[]{R.id.twLicenseeNumber,R.id.twVinNumber,R.id.twMotDate,R.id.twCarType});

        lwCarEdit.setAdapter(adapter);
    }

    private void init() {
        btCarEditMod = findViewById(R.id.btCarEditMod);
        btCarEditDel = findViewById(R.id.btCarEditDel);
        btCarEditBack = findViewById(R.id.btCarEditBack);
        lwCarEdit = findViewById(R.id.lwCarEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
