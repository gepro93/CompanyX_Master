package com.example.gergo.companyx;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class QRCreation extends AppCompatActivity {

    private Button btQrCodeCreate, btQrCreationBack, btQrSave;
    private ImageView iwQrCode;
    private Database db;
    private LoadScreen ls;
    private MultiFormatWriter multiFormatWriter;
    private BitMatrix bitMatrix;
    private ArrayList<String> brandList, typeList, identityList;
    private LinearLayout layout;
    private AlertDialog.Builder alert;
    private String sqlQuery, selectedItem ,selectedBrand, selectedType, selectedIdentity, qrCodeText, item;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreation);
        init();

        btQrCodeCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btQrSave.setAlpha(0);
                qrCodeText = null;
                iwQrCode.setImageResource(0);
                createQrCode();
            }
        });

        btQrSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQrCode();
            }
        });

        btQrCreationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QRCreation.this,FacilitiesMenu.class));
                finish();
            }
        });
    }

    private void saveQrCode() {
        ActivityCompat.requestPermissions(QRCreation.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        image = ((BitmapDrawable) iwQrCode.getDrawable()).getBitmap();
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/QR codes/");
        dir.mkdirs();

        File file = new File(dir, "QRCode.png");

        OutputStream out = null;

        try{
            out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
        }catch (java.io.IOException e){
            e.printStackTrace();
        }

        ls.progressDialog(QRCreation.this,"QR mentése a belső tárhelyre folyamatban...", "Mentés");

    }

    private void createQrCode() {
        alert = new AlertDialog.Builder(QRCreation.this);

        alert.setTitle("QR kód létrehozása");

        //Linear Layout felépítése
        layout = new LinearLayout(QRCreation.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 60, 0, 60);

        //Spinner létrehozása
        final Spinner spItem = new Spinner(QRCreation.this, Spinner.MODE_DROPDOWN);

        final ArrayList<String> itemList = new ArrayList<>();
        itemList.add(0,"Válassz eszközt!");
        itemList.add("Autó");
        itemList.add("Mobil");
        itemList.add("Laptop");

        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, itemList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spItem.setAdapter(spinnerDataAdapter);
        spItem.setSelection(0);
        spItem.setBackgroundResource(R.color.colorWhite);
        spItem.setPadding(0, 0, 0, 60);
        layout.addView(spItem); //Spinner hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spBrand = new Spinner(QRCreation.this, Spinner.MODE_DROPDOWN);

        spBrand.setSelection(0);
        spBrand.setAlpha(0);
        spBrand.setBackgroundResource(R.color.colorWhite);
        spBrand.setPadding(0, 0, 0, 60);
        layout.addView(spBrand); //Spinner hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spType = new Spinner(QRCreation.this, Spinner.MODE_DROPDOWN);

        spType.setSelection(0);
        spType.setAlpha(0);
        spType.setBackgroundResource(R.color.colorWhite);
        spType.setPadding(0, 0, 0, 60);
        layout.addView(spType); //Spinner hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spIdentity = new Spinner(QRCreation.this, Spinner.MODE_DROPDOWN);

        spIdentity.setSelection(0);
        spIdentity.setAlpha(0);
        spIdentity.setBackgroundResource(R.color.colorWhite);
        spIdentity.setPadding(0, 0, 0, 60);
        layout.addView(spIdentity); //Spinner hozzáadása layouthoz

        alert.setView(layout);

        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
                switch (selectedItem){
                    case "Autó":
                        sqlQuery = "SELECT autoMarka as brand FROM auto_gyartmanyok GROUP BY autoMarka";
                        brandList = db.BrandHelper(sqlQuery);
                        brandList.add(0,"Válassz márkát!");
                        ArrayAdapter<String> carBrandList;
                        carBrandList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, brandList);
                        carBrandList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spBrand.setAdapter(carBrandList);
                        spBrand.setAlpha(1);
                        break;

                    case "Mobil":
                        sqlQuery = "SELECT mobilMarka as brand FROM mobil_gyartmanyok GROUP BY mobilMarka";
                        brandList = db.BrandHelper(sqlQuery);
                        brandList.add(0,"Válassz márkát!");
                        ArrayAdapter<String> mobileBrandList;
                        mobileBrandList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, brandList);
                        mobileBrandList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spBrand.setAdapter(mobileBrandList);
                        spBrand.setAlpha(1);
                        break;

                    case "Laptop":
                        sqlQuery = "SELECT laptopMarka as brand FROM laptop_gyartmanyok GROUP BY laptopMarka";
                        brandList = db.BrandHelper(sqlQuery);
                        brandList.add(0,"Válassz márkát!");
                        ArrayAdapter<String> laptopBrandList;
                        laptopBrandList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, brandList);
                        laptopBrandList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spBrand.setAdapter(laptopBrandList);
                        spBrand.setAlpha(1);
                        break;

                    case "Válassz eszközt!":
                        spBrand.setAlpha(0);
                        spType.setAlpha(0);
                        spIdentity.setAlpha(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBrand = adapterView.getItemAtPosition(i).toString();
                if(selectedBrand.equals("Válassz márkát!")){
                    spType.setAlpha(0);
                    spIdentity.setAlpha(0);
                }else {
                    switch (selectedItem) {
                        case "Autó":
                            sqlQuery = "SELECT autoTipus as type FROM auto_gyartmanyok WHERE autoMarka='" + selectedBrand + "' GROUP BY autoTipus";
                            typeList = db.TypeHelper(sqlQuery);
                            typeList.add(0,"Válassz típust!");
                            ArrayAdapter<String> carTypeList;
                            carTypeList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, typeList);
                            carTypeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spType.setAdapter(carTypeList);
                            spType.setAlpha(1);
                            break;

                        case "Mobil":
                            sqlQuery = "SELECT mobilTipus as type FROM mobil_gyartmanyok WHERE mobilMarka='" + selectedBrand + "' GROUP BY mobilTipus";
                            typeList = db.TypeHelper(sqlQuery);
                            typeList.add(0,"Válassz típust!");
                            ArrayAdapter<String> mobileTypeList;
                            mobileTypeList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, typeList);
                            mobileTypeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spType.setAdapter(mobileTypeList);
                            spType.setAlpha(1);
                            break;

                        case "Laptop":
                            sqlQuery = "SELECT laptopTipus as type FROM laptop_gyartmanyok WHERE laptopMarka='" + selectedBrand + "' GROUP BY laptopTipus";
                            typeList = db.TypeHelper(sqlQuery);
                            typeList.add(0,"Válassz típust!");
                            ArrayAdapter<String> laptopTypeList;
                            laptopTypeList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, typeList);
                            laptopTypeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spType.setAdapter(laptopTypeList);
                            spType.setAlpha(1);
                            break;

                        case "Válassz eszközt!":
                            spBrand.setAlpha(0);
                            spType.setAlpha(0);
                            spIdentity.setAlpha(0);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = adapterView.getItemAtPosition(i).toString();
                if(selectedType.equals("Válassz típust!")){
                    spIdentity.setAlpha(0);
                }else {
                    switch (selectedItem) {
                        case "Autó":
                            item = "a";
                            sqlQuery = "SELECT a.autoRendszam as identity " +
                                    "FROM autok as a " +
                                    "LEFT JOIN auto_gyartmanyok AS g ON g.autoGyartmany_id = a.autoGyartmany_id " +
                                    "WHERE autoMarka='" + selectedBrand + "' AND autoTipus='"+ selectedType +
                                    "' GROUP BY a.autoRendszam";
                            identityList = db.IdentityHelper(sqlQuery);
                            identityList.add(0,"Válassz rendszámot!");
                            ArrayAdapter<String> carIdList;
                            carIdList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, identityList);
                            carIdList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spIdentity.setAdapter(carIdList);
                            spIdentity.setAlpha(1);
                            break;

                        case "Mobil":
                            item = "m";
                            sqlQuery = "SELECT a.mobilImeiSzam as identity " +
                                    "FROM mobilok as a " +
                                    "LEFT JOIN mobil_gyartmanyok AS g ON g.mobilGyartmany_id = a.mobilGyartmany_id " +
                                    "WHERE mobilMarka='" + selectedBrand + "' AND mobilTipus='"+ selectedType +
                                    "' GROUP BY a.mobilImeiSzam";
                            identityList = db.IdentityHelper(sqlQuery);
                            identityList.add(0,"Válassz IMEI számot!");
                            ArrayAdapter<String> mobileIdList;
                            mobileIdList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, identityList);
                            mobileIdList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spIdentity.setAdapter(mobileIdList);
                            spIdentity.setAlpha(1);
                            break;

                        case "Laptop":
                            item = "l";
                            sqlQuery = "SELECT a.laptopImeiSzam as identity " +
                                    "FROM laptopok as a " +
                                    "LEFT JOIN laptop_gyartmanyok AS g ON g.laptopGyartmany_id = a.laptopGyartmany_id " +
                                    "WHERE laptopMarka='" + selectedBrand + "' AND laptopTipus='"+ selectedType +
                                    "' GROUP BY a.laptopImeiSzam";
                            identityList = db.IdentityHelper(sqlQuery);
                            identityList.add(0,"Válassz IMEI számot!");
                            ArrayAdapter<String> laptopIdList;
                            laptopIdList = new ArrayAdapter(QRCreation.this, android.R.layout.simple_spinner_item, identityList);
                            laptopIdList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spIdentity.setAdapter(laptopIdList);
                            spIdentity.setAlpha(1);
                            break;

                        case "Válassz eszközt!":
                            spBrand.setAlpha(0);
                            spType.setAlpha(0);
                            spIdentity.setAlpha(0);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spIdentity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIdentity = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.setNegativeButton("Létrehozás", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (selectedIdentity == null) {
                    Toast.makeText(QRCreation.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    qrCodeText = item + " " + selectedBrand + " " + selectedType + " " + selectedIdentity;
                    if (qrCodeText != null) {
                        try {
                            bitMatrix = multiFormatWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 250, 250);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            iwQrCode.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        ls.progressDialog(QRCreation.this,"QR kód készítése folyamatban...", "Létrehozás");
                        btQrSave.setAlpha(1);
                        qrCodeText = null;
                        selectedIdentity = null;
                    }
                }
            }
        });

        alert.show();
    }

    private void init() {
        btQrCodeCreate = findViewById(R.id.btQrCodeCreate);
        btQrCreationBack = findViewById(R.id.btQrCreationBack);
        btQrSave = findViewById(R.id.btQrSave);
        iwQrCode = findViewById(R.id.iwQrCode);
        db = new Database(this);
        ls = new LoadScreen();
        multiFormatWriter = new MultiFormatWriter();
    }

}
