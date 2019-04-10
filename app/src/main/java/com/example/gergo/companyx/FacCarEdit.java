package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FacCarEdit extends AppCompatActivity {

    private Button btFacCarNew, btFacCarMod, btFacCarEditBack;
    private ListView lwFacCarEdit;
    private Database db;
    private LoadScreen ls;
    private ArrayList<HashMap<String, String>> carBenefitList;
    private ListAdapter adapter;
    private int benefitId, modBenefitId, statusId,selectedModCar, selectedCar, pos, empId, userId;
    private String modEmpName,modCar, modLicNum, modStatus, selectedBenefitId, selectedLicNum, modCarLic, modUserName, selectedUser, nameOfEmployee, carBrand, carType, carLicNum, userName;
    private ArrayList<String> carList, empList, carBrandList, carTypeList, carLicList;
    private boolean selectedStatus;
    private LinearLayout layout;
    private AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_car_edit);
        init();
        createList();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        lwFacCarEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modEmpName = (String) obj.get("EMPLOYEE_NAME");
                modCar = (String) obj.get("CARTYPE");
                modLicNum = (String) obj.get("CAR_LICENSENUMBER");
                modStatus = (String) obj.get("BENEFIT_STATUS");
                selectedBenefitId = (String) obj.get("BENEFIT_ID");
                modUserName = (String) obj.get("USER_NAME");
                modBenefitId = Integer.parseInt(selectedBenefitId);
                modCarLic = modCar + " " + modLicNum;
            }
        });

        btFacCarNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarBenefit();
            }
        });

        btFacCarMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCarBenefit();
            }
        });


        btFacCarEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarEdit.this, FacCarHandle.class));
                Animatoo.animateSlideRight(FacCarEdit.this);
                finish();
            }
        });
    }

    private void errorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacCarEdit.this);

        builder.setCancelable(true);
        builder.setTitle("Figyelmeztetés!");
        builder.setMessage("Ez a dolgozó a beosztása alapján, nem jogosult autó használatra vagy nincs autó a flottában!");
        builder.setIcon(R.drawable.ic_dialog_error);

        builder.setPositiveButton("Oké", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void addCarBenefit() {
        alert = new AlertDialog.Builder(FacCarEdit.this);

        alert.setTitle("Új autó kiadás");

        //Linear Layout felépítése
        layout = new LinearLayout(FacCarEdit.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 60, 0, 60);

        //Spinner létrehozása
        final Spinner spEmployee = new Spinner(FacCarEdit.this, Spinner.MODE_DROPDOWN);

        empList = db.employeeUserList();
        empList.add(0,"Válassz dolgozót!");

        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(FacCarEdit.this, android.R.layout.simple_spinner_item, empList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmployee.setAdapter(spinnerDataAdapter);
        spEmployee.setSelection(0);
        spEmployee.setBackgroundResource(R.color.colorWhite);
        spEmployee.setPadding(0, 0, 0, 60);
        layout.addView(spEmployee); //Spinner hozzáadása layouthoz


        //TextView létrehozása
        final TextView empName = new TextView(FacCarEdit.this);
        empName.setGravity(Gravity.CENTER);
        empName.setPadding(0, 0, 0, 60);
        empName.setText("Dolgozó neve");
        layout.addView(empName); //TextView hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spCarBrand = new Spinner(FacCarEdit.this, Spinner.MODE_DROPDOWN);

        spCarBrand.setSelection(0);
        spCarBrand.setAlpha(0);
        spCarBrand.setBackgroundResource(R.color.colorWhite);
        spCarBrand.setPadding(0, 0, 0, 60);
        layout.addView(spCarBrand); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spCarType = new Spinner(FacCarEdit.this, Spinner.MODE_DROPDOWN);

        spCarType.setSelection(0);
        spCarType.setAlpha(0);
        spCarType.setBackgroundResource(R.color.colorWhite);
        spCarType.setPadding(0, 0, 0, 60);
        layout.addView(spCarType); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spCarLic = new Spinner(FacCarEdit.this, Spinner.MODE_DROPDOWN);

        spCarLic.setAlpha(0);
        spCarLic.setSelection(0);
        spCarLic.setBackgroundResource(R.color.colorWhite);
        spCarLic.setPadding(0, 0, 0, 60);
        layout.addView(spCarLic); //Spinner hozzáadása layouthoz

        alert.setView(layout);

        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Válassz dolgozót!")){
                    spCarBrand.setAlpha(0);
                    selectedUser = adapterView.getItemAtPosition(i).toString();
                    nameOfEmployee = db.employeeNameSearch(selectedUser);
                    userId = Integer.parseInt(db.userIdSearch(selectedUser));
                    empId = Integer.parseInt(db.empIdSearch(selectedUser));
                    empName.setText(nameOfEmployee);

                    String brandQuery = "SELECT agy.autoMarka AS autoMarka " +
                            "FROM auto_gyartmanyok AS agy " +
                            "LEFT JOIN autok AS a ON a.autoGyartmany_id = agy.autoGyartmany_id " +
                            "LEFT JOIN gradek AS g ON g.grade_id = agy.grade_id " +
                            "LEFT JOIN beosztasok AS b ON b.grade_id = g.grade_id " +
                            "LEFT JOIN dolgozok AS d ON d.beosztas_id = b.beosztas_id " +
                            "LEFT JOIN felhasznalok AS f ON f.felh_id = d.felh_id " +
                            "WHERE felhNeve="+"'"+selectedUser+"' "+
                            "GROUP BY agy.autoMarka";
                    carBrandList = db.queryFillList(brandQuery,"autoMarka");
                    carBrandList.add(0,"Válassz márkát!");

                    if (carBrandList.size() <= 1){
                        errorMessage();
                    }else{
                        ArrayAdapter<String> spinnerDataAdapter1;
                        spinnerDataAdapter1 = new ArrayAdapter(FacCarEdit.this, android.R.layout.simple_spinner_item, carBrandList);
                        spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spCarBrand.setAdapter(spinnerDataAdapter1);
                        spCarBrand.setAlpha(1);
                    }
                }else {
                    selectedUser = "empty";
                    spCarBrand.setAlpha(0);
                    spCarType.setAlpha(0);
                    spCarLic.setAlpha(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCarBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Válassz márkát!")){
                    carBrand = spCarBrand.getItemAtPosition(i).toString();

                    String carTypeQuery = "SELECT agy.autoTipus AS autoTipus " +
                            "FROM auto_gyartmanyok AS agy " +
                            "LEFT JOIN autok AS a ON a.autoGyartmany_id = agy.autoGyartmany_id " +
                            "LEFT JOIN gradek AS g ON g.grade_id = agy.grade_id " +
                            "LEFT JOIN beosztasok AS b ON b.grade_id = g.grade_id " +
                            "LEFT JOIN dolgozok AS d ON d.beosztas_id = b.beosztas_id " +
                            "LEFT JOIN felhasznalok AS f ON f.felh_id = d.felh_id " +
                            "WHERE felhNeve="+"'"+selectedUser+"' AND agy.autoMarka="+"'"+carBrand+"' " +
                            "GROUP BY agy.autoTipus";
                    carTypeList = db.queryFillList(carTypeQuery, "autoTipus");
                    carTypeList.add(0,"Válassz típust!");
                    ArrayAdapter<String> spinnerDataAdapter2;
                    spinnerDataAdapter2 = new ArrayAdapter(FacCarEdit.this, android.R.layout.simple_spinner_item, carTypeList);
                    spinnerDataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCarType.setAdapter(spinnerDataAdapter2);
                    spCarType.setAlpha(1);
                }else {
                    spCarType.setAlpha(0);
                    spCarLic.setAlpha(0);
                    carBrand = "empty";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                if(!adapterView.getItemAtPosition(j).equals("Válassz típust!")){
                    carType = spCarType.getItemAtPosition(j).toString();
                    carLicList = db.carLicList(carBrand,carType);
                    carLicList.add(0,"Válassz rendszámot!");
                    ArrayAdapter<String> spinnerDataAdapter3;
                    spinnerDataAdapter3 = new ArrayAdapter(FacCarEdit.this, android.R.layout.simple_spinner_item, carLicList);
                    spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCarLic.setAdapter(spinnerDataAdapter3);
                    spCarLic.setAlpha(1);
                }else {
                    spCarLic.setAlpha(0);
                    carType = "empty";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCarLic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Válassz rendszámot!")){
                    carLicNum = spCarLic.getItemAtPosition(i).toString();
                }else carLicNum = "empty";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(selectedUser.equals("empty") || carBrand.equals("empty") || carType.equals("empty") || carLicNum.equals("empty")){
                    Toast.makeText(FacCarEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }else {
                    int carId = db.carIdSearch(carLicNum);
                    boolean empCarBenefitCheck = db.empCarBenefitCheck(empId);
                    if (!empCarBenefitCheck) {
                        boolean carBenefitCheck = db.carBenefitCheck(carId);
                        if (!carBenefitCheck) {
                            boolean empCarBenefitCheckForInactive = db.empCarBenefitCheckForInactive(empId);
                            if(!empCarBenefitCheckForInactive) {
                                boolean insertBenefit = db.insertBenefit(empId, "a", carId, true, userId,userName);
                                if (insertBenefit) {
                                    ls.progressDialog(FacCarEdit.this, "Autó kiadása folyamatban...", "Kiadás");
                                    createList();
                                } else Toast.makeText(FacCarEdit.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }else{
                                boolean updateCarBenefitForInactive = db.updateCarBenefitForInactive(carId,true,userName);
                                if(updateCarBenefitForInactive){
                                    ls.progressDialog(FacCarEdit.this, "Autó kiadása folyamatban...", "Kiadás");
                                    createList();
                                }else Toast.makeText(FacCarEdit.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }
                        }else
                        Toast.makeText(FacCarEdit.this, "Az autó már ki van adva egy dolgozónak!", Toast.LENGTH_SHORT).show();
                    }else
                    Toast.makeText(FacCarEdit.this, "Ennek a dolgozónak már van kiadva autó", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.show();
    }

    private void editCarBenefit() {
        pos = lwFacCarEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(FacCarEdit.this);

            alert.setTitle("Kezelés");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(FacCarEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //TextView létrehozása
            final TextView empName = new TextView(FacCarEdit.this);
            empName.setGravity(Gravity.CENTER);
            empName.setPadding(0, 60, 0, 60);
            empName.setText(modEmpName);
            layout.addView(empName); //TextView hozzáadása layouthoz


            //Spinner létrehozása
            final Spinner spCar = new Spinner(FacCarEdit.this, Spinner.MODE_DROPDOWN);

            carList = db.carList(modUserName);

            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(FacCarEdit.this, android.R.layout.simple_spinner_item, carList);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCar.setAdapter(spinnerDataAdapter);
            spCar.setSelection(selectedModCar);
            spCar.setBackgroundResource(R.color.colorWhite);
            layout.addView(spCar); //Spinner hozzáadása layouthoz

            layout.setPadding(0, 60, 0, 60);

            //Spinner létrehozása
            final Spinner spStatus = new Spinner(FacCarEdit.this, Spinner.MODE_DROPDOWN);

            final List<String> statusList = new ArrayList<>();
            statusList.add(0, "Inaktív");
            statusList.add("Aktív");

            //Státusz lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter3;
            spinnerDataAdapter3 = new ArrayAdapter(FacCarEdit.this, android.R.layout.simple_spinner_item, statusList);
            spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStatus.setAdapter(spinnerDataAdapter3);
            spStatus.setBackgroundResource(R.color.colorWhite);

            if(modStatus.equals("Aktív")){
                spStatus.setSelection(1);
            }else spStatus.setSelection(0);

            spStatus.setPadding(0, 60, 0, 60);

            layout.addView(spStatus); //Spinner hozzáadása layouthoz

            alert.setView(layout);

            for (int s = 0; s < carList.size(); s++){
                if (carList.get(s).equals(modCarLic)) {
                    selectedModCar = db.carIdSearch(modLicNum);
                }
            }

            spCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedLicNum = getCarLic(adapterView.getItemAtPosition(i).toString());
                    selectedCar = db.carIdSearch(selectedLicNum);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getItemAtPosition(i).equals("Inaktív")) {
                        selectedStatus = false;
                    } else selectedStatus = true;

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                        if(selectedCar == selectedModCar){
                            boolean updateCarBenefit = db.updateBenefit(modBenefitId,selectedCar,selectedStatus,userName);
                            if(updateCarBenefit){
                                ls.progressDialog(FacCarEdit.this, "Juttatás kezelése folyamatban...", "Kezelés");
                                createList();
                            }
                        }else{
                            boolean carBenefitCheck = db.carBenefitCheck(selectedCar);
                            if(!carBenefitCheck){
                                boolean updateCarBenefit = db.updateBenefit(modBenefitId,selectedCar,selectedStatus,userName);
                                if(updateCarBenefit){
                                    ls.progressDialog(FacCarEdit.this, "Juttatás kezelése folyamatban...", "Kezelés");
                                    createList();
                                }
                            }else Toast.makeText(FacCarEdit.this, "Ez már egy kiadott autó!", Toast.LENGTH_SHORT).show();
                        }
                }
            });

            alert.show();}
    }

    private void createList() {
        carBenefitList = db.viewActiveCarBenefit();
        adapter = new SimpleAdapter(FacCarEdit.this, carBenefitList, R.layout.car_benefit_edit,
                new String[]{"EMPLOYEE_NAME","CARTYPE","CAR_LICENSENUMBER","BENEFIT_ID","BENEFIT_STATUS","USER_NAME"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfCar,R.id.twLicNumber,benefitId,statusId,R.id.twNameOfUser});

        lwFacCarEdit.setAdapter(adapter);
    }

    private String getCarLic(String licNum){
        String[] splited = licNum.split("\\s+");
        String splittedLicNum = splited[2];
        return splittedLicNum;
    }

    private void init() {
        btFacCarNew = findViewById(R.id.btFacCarNew);
        btFacCarMod = findViewById(R.id.btFacCarMod);
        btFacCarEditBack = findViewById(R.id.btFacCarEditBack);
        lwFacCarEdit = findViewById(R.id.lwFacCarEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacCarEdit.this);

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
                startActivity(new Intent(FacCarEdit.this, Login.class));
                Animatoo.animateFade(FacCarEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
