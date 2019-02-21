package com.example.gergo.companyx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserModify extends AppCompatActivity {

    private EditText etUsernameMod, etPasswordMod1, etPasswordMod2;
    private Spinner spPermissionMod, spStatusMod;
    private Button btUserModifyExecute, btUserModifyBack;
    private int selectedPermission, selectedStatus;
    private Database db;
    private TextView twUserName;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);
        init();

        //Jogosultság lista létrehozása
        List<String> permission = new ArrayList<>();
        permission.add(0,"Admin");
        permission.add("HR");
        permission.add("Beszerzés");
        permission.add("Dolgozó");

        //Státusz lista létrehozás
        final List<String> status = new ArrayList<>();
        status.add(0,"Aktív");
        status.add("Inaktív");

        //Jogosultság lista adapter létrehozása
        ArrayAdapter<String> permissionDataAdapter;
        permissionDataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, permission);
        permissionDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPermissionMod.setAdapter(permissionDataAdapter);

        //Státusz lista adapter létrehozása
        ArrayAdapter<String> statusDataAdapter;
        statusDataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, status);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatusMod.setAdapter(statusDataAdapter);

        SharedPreferences sp = getSharedPreferences("UserModify",MODE_PRIVATE);
        String userName = sp.getString("username","Nincs adat");
        String userPerm = sp.getString("userperm","Nincs adat");
        String userStat = sp.getString("userstat","Nincs adat");

        twUserName.setText(userName);
        etUsernameMod.setText(userName);

        switch (userPerm){
            case "Admin": spPermissionMod.setSelection(0);
                break;
            case "HR": spPermissionMod.setSelection(1);
                break;
            case "Beszerzés": spPermissionMod.setSelection(2);
                break;
            case "Dolgozó": spPermissionMod.setSelection(3);
                break;
            default: break;
        }

        switch (userStat){
            case "Aktív": spStatusMod.setSelection(0);
                break;
            case "Inaktív": spStatusMod.setSelection(1);
                break;
            default: break;
        }

        spPermissionMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPermission = adapterView.getSelectedItemPosition(); //Kiválasztott jogosultág elmentése egy változóba
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spStatusMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatus = adapterView.getSelectedItemPosition(); //Kiválasztott státusz elmentése egy változóba
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btUserModifyExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userModify(); //Felhasználó módosítása
            }
        });

        btUserModifyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserModify.this, UserModifyList.class));
                finish();
            }
        });

    }

    public void init(){
        etUsernameMod = (EditText) findViewById(R.id.etUsernameMod);
        etPasswordMod1 = (EditText) findViewById(R.id.etPasswordMod1);
        etPasswordMod2 = (EditText) findViewById(R.id.etPasswordMod2);
        spPermissionMod = (Spinner) findViewById(R.id.spPermissionMod);
        spStatusMod = (Spinner) findViewById(R.id.spStatusMod);
        btUserModifyExecute = (Button) findViewById(R.id.btUserModifyExecute);
        btUserModifyBack = (Button) findViewById(R.id.btUserModifyBack);
        db = new Database(this);
        twUserName = (TextView) findViewById(R.id.twUserName);
    }

    public void onBackPressed(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserModify.this);

        builder.setCancelable(true);
        builder.setTitle("Kilépés");
        builder.setMessage("Valóban be szeretnéd zárni az alkalmazást?");

        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        builder.show();
    }

    public void userModify(){
        final String modifiedUserName = etUsernameMod.getText().toString();
        final String modifiedPassword1 = etPasswordMod1.getText().toString();
        final String modifiedPassword2 = etPasswordMod2.getText().toString();
        final String userNameOld = twUserName.getText().toString();
        final boolean userStatus;
        final int userPerm = selectedPermission+1;

        if(selectedStatus == 0){
            userStatus = true;
        }else {userStatus = false;}


        if(modifiedUserName.equals("")){
            etUsernameMod.setError("A felhasználónév megadás kötelező!");
        }else if(modifiedPassword1.equals("") && modifiedPassword2.equals("")){
            if(modifiedUserName.equals(userNameOld)){
                AlertDialog.Builder builder = new AlertDialog.Builder(UserModify.this);

                builder.setCancelable(true);
                builder.setTitle("Módosítás");
                builder.setMessage("Valóban módosítani szeretnéd " + userNameOld + "felhasználót?");

                builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Boolean userModify = db.userModifyWithoutPassword(userNameOld,modifiedUserName,userStatus,userPerm);
                        if(userModify){
                            new Task1().execute();
                            twUserName.setText(modifiedUserName);
                        }
                    }
                });
                builder.show();

            }else {
                Integer userCountCheck = db.userNameCheckForModify(modifiedUserName);

                if (userCountCheck >= 1){
                    Toast.makeText(UserModify.this, "Felhasználónév foglalt!", Toast.LENGTH_SHORT).show();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserModify.this);

                    builder.setCancelable(true);
                    builder.setTitle("Módosítás");
                    builder.setMessage("Valóban módosítani szeretnéd "+'"'+ userNameOld +'"'+" felhasználót?");

                    builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Boolean userModify = db.userModifyWithoutPassword(userNameOld,modifiedUserName,userStatus,userPerm);
                            if(userModify){
                                new Task1().execute();
                                twUserName.setText(modifiedUserName);
                            }
                        }
                    });
                    builder.show();
                }
            }
        }else if(!modifiedPassword1.equals(modifiedPassword2)){
            etPasswordMod1.setError("A jelszavaknak meg kell egyezniük!");
            etPasswordMod2.setError("A jelszavaknak meg kell egyezniük!");

        }

    }

    class Task1 extends AsyncTask<Void, Void, String> {

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progress.incrementProgressBy(1);
            }
        };

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(UserModify.this);
            progress.setMax(100);
            progress.setMessage("Felhasználó módosítása folyamatban...");
            progress.setTitle("Módosítás");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                while (progress.getProgress() <= progress.getMax()) {
                    Thread.sleep(40);
                    handler.sendMessage(handler.obtainMessage());
                    if(progress.getProgress() == progress.getMax()){
                        progress.dismiss();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(UserModify.this, "Sikeres módosítás!", Toast.LENGTH_LONG).show();
        }

    }
}
