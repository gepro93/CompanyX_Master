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

public class PositionDelete extends AppCompatActivity {

    private Button btPositionDeleteBack, btPositionDeleteExec;
    private ListView lwPositionDelete;
    private Database db;
    private ProgressDialog progress;
    private String positionNameForDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_delete);
        init();

        final ArrayList<HashMap<String, String>> positionDeleteList = db.viewPositions();

        final ListAdapter adapter = new SimpleAdapter(PositionDelete.this, positionDeleteList, R.layout.position_del_row,
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
    }

    public void positionDelete(ArrayList<HashMap<String,String>> arrayList, ListAdapter listAdapter){
        int pos  = lwPositionDelete.getCheckedItemPosition();


        if (pos > -1)
        {
            Boolean positionDelete = db.positionDelete(positionNameForDelete);

            if (positionDelete){
                arrayList.remove(pos);
                progressDialog();
            }else Toast.makeText(PositionDelete.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    public void progressDialog(){
        progress = new ProgressDialog(PositionDelete.this);
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
                        Thread.sleep(20);
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
