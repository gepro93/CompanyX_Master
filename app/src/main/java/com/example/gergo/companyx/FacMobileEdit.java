package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FacMobileEdit extends AppCompatActivity {

    private Button btFacMobileNew, btFacMobileMod, btFacMobileEditBack;
    private ListView lwFacMobileEdit;
    private Database db;
    private LoadScreen ls;
    private ArrayList<HashMap<String, String>> mobileBenefitList;
    private ListAdapter adapter;
    private int benefitId, modBenefitId, statusId,selectedModMobile, selectedMobile, pos, empId;
    private String modEmpName,modMobile, modImeiNum, modStatus, selectedBenefitId, modMobileImei, selectedEmp, nameOfEmployee, mobileBrand, mobileType, mobileImeiNum;
    private ArrayList<String> mobileList, empList, mobileBrandList, mobileTypeList, mobileImeiList;
    private boolean selectedStatus;
    private LinearLayout layout;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_mobile_edit);
        init();
        createList();

        lwFacMobileEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modEmpName = (String) obj.get("EMPLOYEE_NAME");
                modMobile = (String) obj.get("MOBILTYPE");
                modImeiNum = (String) obj.get("MOBIL_IMEINUMBER");
                modStatus = (String) obj.get("BENEFIT_STATUS");
                selectedBenefitId = (String) obj.get("BENEFIT_ID");
                modBenefitId = Integer.parseInt(selectedBenefitId);
                modMobileImei = modMobile + " " + modImeiNum;
            }
        });

        btFacMobileNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMobileBenefit();
            }
        });

        btFacMobileMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMobileBenefit();
            }
        });

        btFacMobileEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileEdit.this, FacMobileHandle.class));
                finish();
            }
        });
    }

    private void addMobileBenefit() {
        alert = new AlertDialog.Builder(FacMobileEdit.this);

        alert.setTitle("Új mobil kiadás");

        //Linear Layout felépítése
        layout = new LinearLayout(FacMobileEdit.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 60, 0, 60);

        //Spinner létrehozása
        final Spinner spEmployee = new Spinner(FacMobileEdit.this, Spinner.MODE_DROPDOWN);

        empList = db.employeeUserList();
        empList.add(0,"Válassz dolgozót!");

        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(FacMobileEdit.this, android.R.layout.simple_spinner_item, empList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmployee.setAdapter(spinnerDataAdapter);
        spEmployee.setSelection(0);
        spEmployee.setBackgroundResource(R.color.colorWhite);
        spEmployee.setPadding(0, 0, 0, 60);
        layout.addView(spEmployee); //Spinner hozzáadása layouthoz


        //TextView létrehozása
        final TextView empName = new TextView(FacMobileEdit.this);
        empName.setGravity(Gravity.CENTER);
        empName.setPadding(0, 0, 0, 60);
        empName.setText("Dolgozó neve");
        layout.addView(empName); //TextView hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spMobileBrand = new Spinner(FacMobileEdit.this, Spinner.MODE_DROPDOWN);

        mobileBrandList = db.mobileBrandList();
        mobileBrandList.add(0,"Válassz márkát!");

        ArrayAdapter<String> spinnerDataAdapter1;
        spinnerDataAdapter1 = new ArrayAdapter(FacMobileEdit.this, android.R.layout.simple_spinner_item, mobileBrandList);
        spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMobileBrand.setAdapter(spinnerDataAdapter1);
        spMobileBrand.setSelection(0);
        spMobileBrand.setBackgroundResource(R.color.colorWhite);
        spMobileBrand.setPadding(0, 0, 0, 60);
        layout.addView(spMobileBrand); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spMobileType = new Spinner(FacMobileEdit.this, Spinner.MODE_DROPDOWN);

        spMobileType.setSelection(0);
        spMobileType.setAlpha(0);
        spMobileType.setBackgroundResource(R.color.colorWhite);
        spMobileType.setPadding(0, 0, 0, 60);
        layout.addView(spMobileType); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spMobileImei = new Spinner(FacMobileEdit.this, Spinner.MODE_DROPDOWN);

        spMobileImei.setAlpha(0);
        spMobileImei.setSelection(0);
        spMobileImei.setBackgroundResource(R.color.colorWhite);
        spMobileImei.setPadding(0, 0, 0, 60);
        layout.addView(spMobileImei); //Spinner hozzáadása layouthoz

        alert.setView(layout);

        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Válassz dolgozót!")){
                    selectedEmp = adapterView.getItemAtPosition(i).toString();
                    nameOfEmployee = db.employeeNameSearch(selectedEmp);
                    empId = Integer.parseInt(db.empIdSearch(selectedEmp));
                    empName.setText(nameOfEmployee);
                }else selectedEmp = "empty";

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMobileBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Válassz márkát!")){
                    mobileBrand = spMobileBrand.getItemAtPosition(i).toString();
                    mobileTypeList = db.mobileTypeList(mobileBrand);
                    mobileTypeList.add(0,"Válassz típust!");
                    ArrayAdapter<String> spinnerDataAdapter2;
                    spinnerDataAdapter2 = new ArrayAdapter(FacMobileEdit.this, android.R.layout.simple_spinner_item, mobileTypeList);
                    spinnerDataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spMobileType.setAdapter(spinnerDataAdapter2);
                    spMobileType.setAlpha(1);
                }else {
                    spMobileType.setAlpha(0);
                    spMobileImei.setAlpha(0);
                    mobileBrand = "empty";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMobileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                if(!adapterView.getItemAtPosition(j).equals("Válassz típust!")){
                    mobileType = spMobileType.getItemAtPosition(j).toString();
                    mobileImeiList = db.mobileImeiList(mobileBrand,mobileType);
                    mobileImeiList.add(0,"Válassz IMEI számot!");
                    ArrayAdapter<String> spinnerDataAdapter3;
                    spinnerDataAdapter3 = new ArrayAdapter(FacMobileEdit.this, android.R.layout.simple_spinner_item, mobileImeiList);
                    spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spMobileImei.setAdapter(spinnerDataAdapter3);
                    spMobileImei.setAlpha(1);
                }else {
                    spMobileImei.setAlpha(0);
                    mobileType = "empty";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMobileImei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Válassz IMEI számot!")){
                    mobileImeiNum = spMobileImei.getItemAtPosition(i).toString();
                }else mobileImeiNum = "empty";
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
                if(selectedEmp.equals("empty") || mobileBrand.equals("empty") || mobileType.equals("empty") || mobileImeiNum.equals("empty")){
                    Toast.makeText(FacMobileEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }else {
                    int mobileId = db.mobileIdSearch(mobileImeiNum);
                    boolean empMobileBenefitCheck = db.empMobileBenefitCheck(empId);
                    if (!empMobileBenefitCheck) {
                        boolean mobileBenefitCheck = db.mobileBenefitCheck(mobileId);
                        if (!mobileBenefitCheck) {
                            boolean empMobileBenefitCheckForInactive = db.empMobileBenefitCheckForInactive(empId);
                            if(!empMobileBenefitCheckForInactive) {
                                boolean insertBenefit = db.insertBenefit(empId, "m", mobileId, true);
                                if (insertBenefit) {
                                    ls.progressDialog(FacMobileEdit.this, "Mobil kiadása folyamatban...", "Kiadás");
                                    createList();
                                } else Toast.makeText(FacMobileEdit.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }else{
                                boolean updateMobileBenefitForInactive = db.updateMobileBenefitForInactive(mobileId,true);
                                if(updateMobileBenefitForInactive){
                                    ls.progressDialog(FacMobileEdit.this, "Mobil kiadása folyamatban...", "Kiadás");
                                    createList();
                                }else Toast.makeText(FacMobileEdit.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(FacMobileEdit.this, "A mobil már ki van adva egy dolgozónak!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(FacMobileEdit.this, "Ennek a dolgozónak már van kiadva mobil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.show();
    }

    private void editMobileBenefit() {
        pos = lwFacMobileEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(FacMobileEdit.this);

            alert.setTitle("Kezelés");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(FacMobileEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(0, 60, 0, 60);

            //TextView létrehozása
            final TextView empName = new TextView(FacMobileEdit.this);
            empName.setGravity(Gravity.CENTER);
            empName.setPadding(0, 60, 0, 60);
            empName.setText(modEmpName);
            layout.addView(empName); //TextView hozzáadása layouthoz


            //Spinner létrehozása
            final Spinner spMobile = new Spinner(FacMobileEdit.this, Spinner.MODE_DROPDOWN);

            mobileList = db.mobileList();

            for (int s = 0; s < mobileList.size(); s++){
                if (mobileList.get(s).equals(modMobileImei)) {
                    selectedModMobile = s;
                }
            }

            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(FacMobileEdit.this, android.R.layout.simple_spinner_item, mobileList);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMobile.setAdapter(spinnerDataAdapter);
            spMobile.setSelection(selectedModMobile);
            spMobile.setBackgroundResource(R.color.colorWhite);
            spMobile.setPadding(0, 0, 0, 60);
            layout.addView(spMobile); //Spinner hozzáadása layouthoz



            //Spinner létrehozása
            final Spinner spStatus = new Spinner(FacMobileEdit.this, Spinner.MODE_DROPDOWN);

            final List<String> statusList = new ArrayList<>();
            statusList.add(0, "Inaktív");
            statusList.add("Aktív");

            //Státusz lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter3;
            spinnerDataAdapter3 = new ArrayAdapter(FacMobileEdit.this, android.R.layout.simple_spinner_item, statusList);
            spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStatus.setAdapter(spinnerDataAdapter3);
            spStatus.setBackgroundResource(R.color.colorWhite);

            if(modStatus.equals("Aktív")){
                spStatus.setSelection(1);
            }else spStatus.setSelection(0);

            spStatus.setPadding(0, 60, 0, 60);

            layout.addView(spStatus); //Spinner hozzáadása layouthoz

            alert.setView(layout);

            spMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedMobile = adapterView.getSelectedItemPosition()+1;
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
                    if(selectedMobile == selectedModMobile){
                        boolean updateMobileBenefit = db.updateBenefit(modBenefitId,selectedMobile,selectedStatus);
                        if(updateMobileBenefit){
                            ls.progressDialog(FacMobileEdit.this, "Juttatás kezelése folyamatban...", "Kezelés");
                            createList();
                        }
                    }else{
                        boolean mobileBenefitCheck = db.mobileBenefitCheck(selectedMobile);
                        if(!mobileBenefitCheck){
                            boolean updateMobileBenefit = db.updateBenefit(modBenefitId,selectedMobile,selectedStatus);
                            if(updateMobileBenefit){
                                ls.progressDialog(FacMobileEdit.this, "Juttatás kezelése folyamatban...", "Kezelés");
                                createList();
                            }
                        }else Toast.makeText(FacMobileEdit.this, "Ez már egy kiadott mobil!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();}
    }

    private void createList() {
        mobileBenefitList = db.viewActiveMobileBenefit();
        adapter = new SimpleAdapter(FacMobileEdit.this, mobileBenefitList, R.layout.mobile_benefit_edit,
                new String[]{"EMPLOYEE_NAME","MOBILTYPE","MOBIL_IMEINUMBER","BENEFIT_ID","BENEFIT_STATUS"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfMobile,R.id.twMobileImeiNumber,benefitId,statusId});

        lwFacMobileEdit.setAdapter(adapter);
    }

    private void init() {
        btFacMobileNew = findViewById(R.id.btFacMobileNew);
        btFacMobileMod = findViewById(R.id.btFacMobileMod);
        btFacMobileEditBack = findViewById(R.id.btFacMobileEditBack);
        lwFacMobileEdit = findViewById(R.id.lwFacMobileEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
