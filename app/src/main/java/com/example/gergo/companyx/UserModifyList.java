package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class UserModifyList extends AppCompatActivity {

    private Database db;
    private Button btUserModBack, btUserMod;
    private ListView lwUserModify;
    private ArrayList<String> userListByName, userListByPerm, userListByStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify_list);
        init();


        final ArrayList<HashMap<String,String>> userList = db.viewUsers();
        final ListAdapter adapter = new SimpleAdapter(UserModifyList.this, userList, R.layout.user_modify_row,
                new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});

        lwUserModify.setAdapter(adapter);

        userListByName = db.viewUsersByName();
        userListByPerm = db.viewUsersByPerm();
        userListByStatus = db.viewUsersByStatus();

        lwUserModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });

        btUserModBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(UserModifyList.this, UserMenu.class));
                    finish();
            }
        });

        btUserMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserModify(); //Felhasználó adatainak átadása
            }
        });

    }

    public void init(){
        db = new Database(this);
        btUserModBack = (Button) findViewById(R.id.btUserModBack);
        btUserMod = (Button) findViewById(R.id.btUserMod);
        lwUserModify = (ListView) findViewById(R.id.lwUserModify);
    }

    public void setUserModify(){
        int pos  = lwUserModify.getCheckedItemPosition();

        if (pos > -1)
        {
            String userName = userListByName.get(pos);
            String userPerm = userListByPerm.get(pos);

            SharedPreferences sp = getSharedPreferences("UserModify",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("username", userName);
            editor.putString("userperm", userPerm);
            if(userListByStatus.get(pos).equals("1")){
                editor.putString("userstat", "Aktív");
            }else editor.putString("userstat", "Inaktív");


            editor.apply();
            editor.commit();

            startActivity(new Intent(UserModifyList.this, UserModify.class));
            finish();
        }else{
            Toast.makeText(UserModifyList.this, "Módosításhoz jelölj ki egy elemet!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserModifyList.this);

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
}
