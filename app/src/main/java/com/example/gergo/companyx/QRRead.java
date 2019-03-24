package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QRRead extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private String item, brand, type, identity, sqlQuery, identityOut;
    private Database db;
    private boolean itemCheck;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(scannerView);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkPermission()){
                Toast.makeText(this, "Olvass be egy QR kódot!", Toast.LENGTH_SHORT).show();
            }else{
                requestPermissions();
            }
        }
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(QRRead.this, CAMERA) == PackageManager.PERMISSION_GRANTED);

    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CAMERA:
            if(grantResults.length>0){
                boolean camereAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (camereAccepted){
                    Toast.makeText(this, "Hozzáférés megadva!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Hozzáférés megtagadva!", Toast.LENGTH_SHORT).show();
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (shouldShowRequestPermissionRationale(CAMERA)){
                            displayAlertMessage("QR kód olvasáshoz kamera hozzáférésre van szükség!",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else{
                requestPermissions();
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        final String qrText = result.getText();
        splitText(qrText);
    }

    private void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(QRRead.this)
                .setMessage(message)
                .setPositiveButton("Oké", listener)
                .setNegativeButton("Mégsem", null)
                .create()
                .show();
    }

    private void splitText(String scanResult){
        String[] splited = scanResult.split("\\s+");

        if (splited.length != 4){
            Toast.makeText(this, "Nincs találat az adatbázisban!", Toast.LENGTH_SHORT).show();
            onResume();
        }else {
            item = splited[0];
            brand = splited[1];
            type = splited[2];
            identity = splited[3];
            displayResult();
        }
    }

    private void displayResult(){
        switch (item){
            case "a":
                sqlQuery = "SELECT a.autoRendszam as identity " +
                        "FROM autok as a " +
                        "LEFT JOIN auto_gyartmanyok AS g ON g.autoGyartmany_id = a.autoGyartmany_id " +
                        "WHERE autoMarka='" + brand + "' AND autoTipus='"+ type +
                        "' GROUP BY a.autoRendszam";
                itemCheck = db.itemCheck(sqlQuery);
                identityOut = "Rendszám: ";
                break;
            case "m":
                sqlQuery = "SELECT a.mobilImeiSzam as identity " +
                        "FROM mobilok as a " +
                        "LEFT JOIN mobil_gyartmanyok AS g ON g.mobilGyartmany_id = a.mobilGyartmany_id " +
                        "WHERE mobilMarka='" + brand + "' AND mobilTipus='"+ type +
                        "' GROUP BY a.mobilImeiSzam";
                itemCheck = db.itemCheck(sqlQuery);
                identityOut = "IMEI szám: ";
                break;
            case "l":
                sqlQuery = "SELECT a.laptopImeiSzam as identity " +
                        "FROM laptopok as a " +
                        "LEFT JOIN laptop_gyartmanyok AS g ON g.laptopGyartmany_id = a.laptopGyartmany_id " +
                        "WHERE laptopMarka='" + brand + "' AND laptopTipus='"+ type +
                        "' GROUP BY a.laptopImeiSzam";
                itemCheck = db.itemCheck(sqlQuery);
                identityOut = "IMEI szám: ";
                break;
            default:
                Toast.makeText(this, "Nincs találat az adatbázisban!", Toast.LENGTH_SHORT).show();
                break;
        }

        if(!itemCheck){
            Toast.makeText(this, "Nincs találat az adatbázisban!", Toast.LENGTH_SHORT).show();
        }else {

            AlertDialog.Builder alert = new AlertDialog.Builder(QRRead.this);

            alert.setTitle("Adatbázis találat!");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(QRRead.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout brandName = new LinearLayout(QRRead.this);
            brandName.setOrientation(LinearLayout.HORIZONTAL);
            brandName.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(brandName);

            //TextView létrehozása
            final TextView nameOfBrand = new TextView(QRRead.this);
            nameOfBrand.setGravity(Gravity.CENTER);
            nameOfBrand.setPadding(0, 60, 0, 60);
            nameOfBrand.setText("Márka: ");
            brandName.addView(nameOfBrand); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView brandItem = new TextView(QRRead.this);
            brandItem.setGravity(Gravity.CENTER);
            brandItem.setPadding(0, 60, 0, 60);
            brandItem.setText(brand);
            brandName.addView(brandItem); //TextView hozzáadása layouthoz

            LinearLayout typeName = new LinearLayout(QRRead.this);
            typeName.setOrientation(LinearLayout.HORIZONTAL);
            typeName.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(typeName);

            //TextView létrehozása
            final TextView nameOfType = new TextView(QRRead.this);
            nameOfType.setGravity(Gravity.CENTER);
            nameOfType.setPadding(0, 60, 0, 60);
            nameOfType.setText("Típus: ");
            typeName.addView(nameOfType); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView typeItem = new TextView(QRRead.this);
            typeItem.setGravity(Gravity.CENTER);
            typeItem.setPadding(0, 60, 0, 60);
            typeItem.setText(type);
            typeName.addView(typeItem); //TextView hozzáadása layouthoz

            LinearLayout identityName = new LinearLayout(QRRead.this);
            identityName.setOrientation(LinearLayout.HORIZONTAL);
            identityName.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(identityName);

            //TextView létrehozása
            final TextView nameOfIdentity = new TextView(QRRead.this);
            nameOfIdentity.setGravity(Gravity.CENTER);
            nameOfIdentity.setPadding(0, 60, 0, 60);
            nameOfIdentity.setText(identityOut);
            identityName.addView(nameOfIdentity); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView identityItem = new TextView(QRRead.this);
            identityItem.setGravity(Gravity.CENTER);
            identityItem.setPadding(0, 60, 0, 60);
            identityItem.setText(identity);
            identityName.addView(identityItem); //TextView hozzáadása layouthoz

            alert.setView(layout);

            alert.setPositiveButton("Bezárás", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(QRRead.this, FacilitiesMenu.class));
                    finish();
                }
            });

            alert.setNegativeButton("Új beolvasás", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    scannerView.resumeCameraPreview(QRRead.this);
                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }

    private void init() {
        db = new Database(this);
        scannerView = new ZXingScannerView(this);
    }

    public void onBackPressed(){
        startActivity(new Intent(QRRead.this, FacilitiesMenu.class));
        finish();
    }
}
