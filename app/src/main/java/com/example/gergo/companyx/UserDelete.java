package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
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
    private LoadScreen ls;
    private String userNameForDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);
        init();

        final ArrayList<HashMap<String,String>> userList = db.viewUsers();
        final ListAdapter adapter = new SimpleAdapter(UserDelete.this, userList, R.layout.user_delete_row,
                new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});

        lwUserDelete.setAdapter(adapter);


        lwUserDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                userNameForDelete = (String) obj.get("POSITION_NAME");
            }
        });

        btUserDeleteExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDelete.this);

                builder.setCancelable(true);
                builder.setTitle("Törlés");
                builder.setMessage("Biztosan törlöd a felhasználót?");

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
            Boolean userDelete = db.UserDelete(userNameForDelete);

            if (userDelete){
                arrayList.remove(pos);
                ls.progressDialog(UserDelete.this, "Felhasználó törlése folyamatban...", "Eltávolítás");
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


}
