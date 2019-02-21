package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class UserList extends AppCompatActivity {

    private Database db;
    private ListView lwUserList;
    private Button btUserListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        init();

        ArrayList<HashMap<String,String>> userList = db.viewUsers();
        ListAdapter adapter = new SimpleAdapter(UserList.this, userList, R.layout.user_list_row,new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});
        lwUserList.setAdapter(adapter);

        btUserListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserListBack = new Intent(UserList.this, UserMenu.class);
                startActivity(UserListBack);
                finish();
            }
        });
    }

    public void init(){
        db = new Database(this);
        lwUserList = (ListView) findViewById(R.id.lwUserList);
        btUserListBack = (Button) findViewById(R.id.btUserListBack);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserList.this);

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
