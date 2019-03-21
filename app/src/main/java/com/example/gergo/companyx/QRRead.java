package com.example.gergo.companyx;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;

import java.io.IOException;

public class QRRead extends AppCompatActivity {

    private SurfaceView cameraView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private TextView twQrText;
    private String qrText, item, brand, type, identity, sqlQuery;
    private Database db;
    private boolean itemCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrread);
        init();

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(680, 480).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(QRRead.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                   return;
                }
                try {
                    cameraSource.start(surfaceHolder);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size()!=0){
                    twQrText.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            displayResult();
                            twQrText.setText(qrCodes.valueAt(0).displayValue);
                            splitText(twQrText);
                        }
                    });
                }
            }
        });

    }

    private void splitText(TextView textView){
        qrText = textView.getText().toString();
        String[] splited = qrText.split("\\s+");
        item = splited[0];
        brand = splited[1];
        type = splited[2];
        identity = splited[3];
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
                break;
            case "m":
                sqlQuery = "SELECT a.mobilImeiSzam as identity " +
                        "FROM mobilok as a " +
                        "LEFT JOIN mobil_gyartmanyok AS g ON g.mobilGyartmany_id = a.mobilGyartmany_id " +
                        "WHERE mobilMarka='" + brand + "' AND mobilTipus='"+ type +
                        "' GROUP BY a.mobilImeiSzam";
                itemCheck = db.itemCheck(sqlQuery);
                break;
            case "l":
                sqlQuery = "SELECT a.laptopImeiSzam as identity " +
                        "FROM laptopok as a " +
                        "LEFT JOIN laptop_gyartmanyok AS g ON g.laptopGyartmany_id = a.laptopGyartmany_id " +
                        "WHERE laptopMarka='" + brand + "' AND laptopTipus='"+ type +
                        "' GROUP BY a.laptopImeiSzam";
                itemCheck = db.itemCheck(sqlQuery);
                break;
        }

        if(!itemCheck){
            Toast.makeText(this, "Nincs találat az adatbázisban!", Toast.LENGTH_SHORT).show();
        }else {

            AlertDialog.Builder alert = new AlertDialog.Builder(QRRead.this);

            alert.setTitle("Kezelés");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(QRRead.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //TextView létrehozása
            final TextView brandItem = new TextView(QRRead.this);
            brandItem.setGravity(Gravity.CENTER);
            brandItem.setPadding(0, 60, 0, 60);
            brandItem.setText(brand);
            layout.addView(brandItem); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView typeItem = new TextView(QRRead.this);
            typeItem.setGravity(Gravity.CENTER);
            typeItem.setPadding(0, 60, 0, 60);
            typeItem.setText(type);
            layout.addView(typeItem); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView identityItem = new TextView(QRRead.this);
            identityItem.setGravity(Gravity.CENTER);
            identityItem.setPadding(0, 60, 0, 60);
            identityItem.setText(identity);
            layout.addView(identityItem); //TextView hozzáadása layouthoz

            alert.setView(layout);
        }
    }

    private void init() {
        cameraView = findViewById(R.id.cameraView);
        twQrText = findViewById(R.id.twQrText);
        db = new Database(this);
    }

    public void onBackPressed(){
        startActivity(new Intent(QRRead.this, FacilitiesMenu.class));
        finish();
    }
}
