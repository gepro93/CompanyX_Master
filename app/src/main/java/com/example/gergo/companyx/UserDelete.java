package com.example.gergo.companyx;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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


public class UserDelete extends AppCompatActivity {

    private Database db;
    private ListView lwUserDelete;
    private Button btUserDeleteExec, btUserDeleteBack;
    private ProgressDialog progress;
    private ArrayList<String> userListByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);
        init();

        final ArrayList<HashMap<String,String>> userList = db.viewUsers();
        final ListAdapter adapter = new SimpleAdapter(UserDelete.this, userList, R.layout.user_delete_row,
                new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});

        lwUserDelete.setAdapter(adapter);

        userListByName = db.viewUsersByName();

        lwUserDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });

        btUserDeleteExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDelete.this);

                builder.setCancelable(true);
                builder.setTitle("Törlés");
                builder.setMessage("Felhasználó törlése folyamatban...");

                builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDelete(userList,adapter); //Felhasználó törlése
                    }
                });
                builder.show();
            }
        });

        btUserDeleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDelete.this, UserMenu.class));
                finish();
            }
        });

    }
    public void init(){
        db = new Database(this);
        lwUserDelete = (ListView) findViewById(R.id.lwUserDelete);
        btUserDeleteExec = (Button) findViewById(R.id.btUserDeleteExec);
        btUserDeleteBack = (Button) findViewById(R.id.btUserDeleteBack);
    }

    public void userDelete(ArrayList<HashMap<String,String>> arrayList, ListAdapter listAdapter){
        int pos  = lwUserDelete.getCheckedItemPosition();

        if (pos > -1)
        {
            String text = userListByName.get(pos);
            Boolean userDelete = db.UserDelete(text);

            if (userDelete){
                arrayList.remove(pos);
                userListByName.remove(pos);
                progressDialog();
            }else Toast.makeText(UserDelete.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserDelete.this);

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


    public void progressDialog(){
        progress = new ProgressDialog(UserDelete.this);
        progress.setMax(100);
        progress.setMessage("Felhasználó törlése folyamatban...");
        progress.setTitle("Eltávolítás...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(progress.getProgress() <= progress.getMax()){
                        Thread.sleep(40);
                        handler.sendMessage(handler.obtainMessage());
                        if(progress.getProgress() == progress.getMax()){
                            progress.dismiss();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.incrementProgressBy(1);
        }
    };

}
