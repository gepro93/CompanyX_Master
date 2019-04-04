package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class UserMenu extends AppCompatActivity {

    private Button btUserCreate, btUserModify, btUserDelete, btUserList, btUserMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_menu);
        init();


        btUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenu.this, UserList.class));
                Animatoo.animateSlideLeft(UserMenu.this);
                finish();
            }
        });

        btUserMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenu.this, AdminMenu.class));
                Animatoo.animateSlideRight(UserMenu.this);
                finish();
            }
        });

        btUserCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenu.this, UserAdd.class));
                Animatoo.animateSlideLeft(UserMenu.this);
                finish();
            }
        });

        btUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenu.this, UserDelete.class));
                Animatoo.animateSlideLeft(UserMenu.this);
                finish();
            }
        });

        btUserModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenu.this, UserModifyList.class));
                Animatoo.animateSlideLeft(UserMenu.this);
                finish();
            }
        });
    }

    public void init(){
        btUserCreate = (Button) findViewById(R.id.btUserCreate);
        btUserModify = (Button) findViewById(R.id.btUserModify);
        btUserDelete = (Button) findViewById(R.id.btUserDelete);
        btUserList = (Button) findViewById(R.id.btUserList);
        btUserMenuBack = (Button) findViewById(R.id.btUserMenuBack);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMenu.this);

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
                startActivity(new Intent(UserMenu.this, Login.class));
                Animatoo.animateFade(UserMenu.this);
                finish();
            }
        });
        builder.show();
    }

}
