package com.example.gergo.companyx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class CarEdit extends AppCompatActivity {

    private Button btCarEditMod, btCarEditDel, btCarEditBack;
    private ListView lwCarEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> carList;
    private ListAdapter adapter;
    private int pos;
    private String carType, licenseeNumber, motDate, vinNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_edit);
        init();

        carList = db.viewCars();
        adapter = new SimpleAdapter(CarEdit.this, carList, R.layout.car_edit_row,
                new String[]{"CAR_LICENSENUMBER","CAR_VINNUMBER","CAR_MOTDATE","CARTYPE"},
                new int[]{R.id.twLicenseeNumber,R.id.twVinNumber,R.id.twMotDate,R.id.twCarType});

        lwCarEdit.setAdapter(adapter);

        lwCarEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                carType = (String) obj.get("CARTYPE");
                licenseeNumber = (String) obj.get("CAR_LICENSENUMBER");
                motDate = (String) obj.get("CAR_MOTDATE");
                vinNumber = (String) obj.get("CAR_VINNUMBER");
            }
        });

    }

    private void init() {
        btCarEditMod = findViewById(R.id.btCarEditMod);
        btCarEditDel = findViewById(R.id.btCarEditDel);
        btCarEditBack = findViewById(R.id.btCarEditBack);
        lwCarEdit = findViewById(R.id.lwCarEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
