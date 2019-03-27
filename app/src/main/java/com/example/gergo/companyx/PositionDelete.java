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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionDelete extends AppCompatActivity {

    private Button btPositionDeleteBack, btPositionDeleteExec;
    private ListView lwPositionDelete;
    private Database db;
    private String positionNameForDelete;
    private ArrayList<HashMap<String, String>> positionDeleteList;
    private ListAdapter adapter;
    private LoadScreen ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_delete);
        init();

        positionDeleteList = db.viewPositions();

        adapter = new SimpleAdapter(PositionDelete.this, positionDeleteList, R.layout.position_del_row,
                new String[]{"POSITION_NAME", "GRADE_NAME", "SALARY_MIN_VALUE", "SALARY_MAX_VALUE"},
                new int[]{R.id.twPositionName, R.id.twPositionGradeName, R.id.twPositionSalaryFrom, R.id.twPositionSalaryTo});

        lwPositionDelete.setAdapter(adapter);

        lwPositionDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                positionNameForDelete = (String) obj.get("POSITION_NAME");
            }
        });

        btPositionDeleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionDelete.this,PositionMenu.class));
                Animatoo.animateSlideRight(PositionDelete.this);
                finish();
            }
        });

        btPositionDeleteExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PositionDelete.this);

                builder.setCancelable(true);
                builder.setTitle("Törlés");
                builder.setMessage("Biztosan törlöd a pozíciót?");

                builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positionDelete(positionDeleteList,adapter); //Pozició törlése
                    }
                });
                builder.show();
            }
        });

    }

    private void init() {
        btPositionDeleteBack = findViewById(R.id.btPositionDeleteBack);
        btPositionDeleteExec = findViewById(R.id.btPositionDeleteExec);
        lwPositionDelete = findViewById(R.id.lwPositionDelete);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void positionDelete(ArrayList<HashMap<String,String>> arrayList, ListAdapter listAdapter){
        int pos  = lwPositionDelete.getCheckedItemPosition();


        if (pos > -1)
        {
            Boolean positionDelete = db.positionDelete(positionNameForDelete);

            if (positionDelete){
                arrayList.remove(pos);
                ls.progressDialog(this,"Pozíció törlése folyamatban...", "Eltávolítás");
            }else Toast.makeText(PositionDelete.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PositionDelete.this);

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
                startActivity(new Intent(PositionDelete.this, Login.class));
                Animatoo.animateFade(PositionDelete.this);
                finish();
            }
        });
        builder.show();
    }

}
