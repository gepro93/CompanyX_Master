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

public class FacLaptopEdit extends AppCompatActivity {

    private Button btFacLaptopNew, btFacLaptopMod, btFacLaptopEditBack;
    private ListView lwFacLaptopEdit;
    private Database db;
    private LoadScreen ls;
    private ArrayList<HashMap<String, String>> laptopBenefitList;
    private ListAdapter adapter;
    private int benefitId, modBenefitId, statusId,selectedModLaptop, selectedLaptop, pos, empId, userId;
    private String modEmpName,modLaptop, modImeiNum, modStatus, selectedBenefitId, selectedImeiNumber, modLaptopImei, modUserName, selectedUser, nameOfEmployee, laptopBrand, laptopType, laptopImeiNum, userName;
    private ArrayList<String> laptopList, empList, laptopBrandList, laptopTypeList, laptopImeiList;
    private boolean selectedStatus;
    private LinearLayout layout;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_laptop_edit);
        init();
        createList();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        lwFacLaptopEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                modEmpName = (String) obj.get("EMPLOYEE_NAME");
                modLaptop = (String) obj.get("LAPTOPTYPE");
                modImeiNum = (String) obj.get("LAPTOP_IMEINUMBER");
                modStatus = (String) obj.get("BENEFIT_STATUS");
                selectedBenefitId = (String) obj.get("BENEFIT_ID");
                modUserName = (String) obj.get("USER_NAME");
                modBenefitId = Integer.parseInt(selectedBenefitId);
                modLaptopImei = modLaptop + " " + modImeiNum;
            }
        });

        btFacLaptopNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLaptopBenefit();
            }
        });

        btFacLaptopMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editLaptopBenefit();
            }
        });

        btFacLaptopEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopEdit.this, FacLaptopHandle.class));
                Animatoo.animateSlideRight(FacLaptopEdit.this);
                finish();
            }
        });
    }

    private void editLaptopBenefit() {
        pos = lwFacLaptopEdit.getCheckedItemPosition();

        if (pos > -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(FacLaptopEdit.this);

            alert.setTitle("Kezelés");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(FacLaptopEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(0, 60, 0, 60);

            //TextView létrehozása
            final TextView empName = new TextView(FacLaptopEdit.this);
            empName.setGravity(Gravity.CENTER);
            empName.setPadding(0, 60, 0, 60);
            empName.setText(modEmpName);
            layout.addView(empName); //TextView hozzáadása layouthoz


            //Spinner létrehozása
            final Spinner spLaptop = new Spinner(FacLaptopEdit.this, Spinner.MODE_DROPDOWN);

            laptopList = db.laptopList(modUserName);

            for (int s = 0; s < laptopList.size(); s++){
                if (laptopList.get(s).equals(modLaptopImei)) {
                    selectedModLaptop = s;
                }
            }

            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(FacLaptopEdit.this, android.R.layout.simple_spinner_item, laptopList);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLaptop.setAdapter(spinnerDataAdapter);
            spLaptop.setSelection(selectedModLaptop);
            spLaptop.setBackgroundResource(R.color.colorWhite);
            spLaptop.setPadding(0, 0, 0, 60);
            layout.addView(spLaptop); //Spinner hozzáadása layouthoz



            //Spinner létrehozása
            final Spinner spStatus = new Spinner(FacLaptopEdit.this, Spinner.MODE_DROPDOWN);

            final List<String> statusList = new ArrayList<>();
            statusList.add(0, "Inaktív");
            statusList.add("Aktív");

            //Státusz lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter3;
            spinnerDataAdapter3 = new ArrayAdapter(FacLaptopEdit.this, android.R.layout.simple_spinner_item, statusList);
            spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStatus.setAdapter(spinnerDataAdapter3);
            spStatus.setBackgroundResource(R.color.colorWhite);

            if(modStatus.equals("Aktív")){
                spStatus.setSelection(1);
            }else spStatus.setSelection(0);

            spStatus.setPadding(0, 60, 0, 60);

            layout.addView(spStatus); //Spinner hozzáadása layouthoz

            alert.setView(layout);

            spLaptop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedImeiNumber = getImeiNum(adapterView.getItemAtPosition(i).toString());
                    selectedLaptop = db.laptopIdSearch(selectedImeiNumber);
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
                    if(selectedLaptop == selectedModLaptop){
                        boolean updateLaptopBenefit = db.updateBenefit(modBenefitId,selectedLaptop,selectedStatus);
                        if(updateLaptopBenefit){
                            ls.progressDialog(FacLaptopEdit.this, "Juttatás kezelése folyamatban...", "Kezelés");
                            createList();
                        }
                    }else{
                        boolean laptopBenefitCheck = db.laptopBenefitCheck(selectedLaptop);
                        if(!laptopBenefitCheck){
                            boolean updateLaptopBenefit = db.updateBenefit(modBenefitId,selectedLaptop,selectedStatus);
                            if(updateLaptopBenefit){
                                ls.progressDialog(FacLaptopEdit.this, "Juttatás kezelése folyamatban...", "Kezelés");
                                createList();
                            }
                        }else Toast.makeText(FacLaptopEdit.this, "Ez már egy kiadott laptop!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.show();}
    }

    private void addLaptopBenefit() {
        alert = new AlertDialog.Builder(FacLaptopEdit.this);

        alert.setTitle("Új laptop kiadás");

        //Linear Layout felépítése
        layout = new LinearLayout(FacLaptopEdit.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 60, 0, 60);

        //Spinner létrehozása
        final Spinner spEmployee = new Spinner(FacLaptopEdit.this, Spinner.MODE_DROPDOWN);

        empList = db.employeeUserList();
        empList.add(0,"Válassz dolgozót!");

        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(FacLaptopEdit.this, android.R.layout.simple_spinner_item, empList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmployee.setAdapter(spinnerDataAdapter);
        spEmployee.setSelection(0);
        spEmployee.setBackgroundResource(R.color.colorWhite);
        spEmployee.setPadding(0, 0, 0, 60);
        layout.addView(spEmployee); //Spinner hozzáadása layouthoz


        //TextView létrehozása
        final TextView empName = new TextView(FacLaptopEdit.this);
        empName.setGravity(Gravity.CENTER);
        empName.setPadding(0, 0, 0, 60);
        empName.setText("Dolgozó neve");
        layout.addView(empName); //TextView hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spLaptopBrand = new Spinner(FacLaptopEdit.this, Spinner.MODE_DROPDOWN);

        laptopBrandList = db.laptopBrandList();
        laptopBrandList.add(0,"Válassz márkát!");

        ArrayAdapter<String> spinnerDataAdapter1;
        spinnerDataAdapter1 = new ArrayAdapter(FacLaptopEdit.this, android.R.layout.simple_spinner_item, laptopBrandList);
        spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLaptopBrand.setAdapter(spinnerDataAdapter1);
        spLaptopBrand.setSelection(0);
        spLaptopBrand.setBackgroundResource(R.color.colorWhite);
        spLaptopBrand.setPadding(0, 0, 0, 60);
        layout.addView(spLaptopBrand); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spLaptopType = new Spinner(FacLaptopEdit.this, Spinner.MODE_DROPDOWN);

        spLaptopType.setSelection(0);
        spLaptopType.setAlpha(0);
        spLaptopType.setBackgroundResource(R.color.colorWhite);
        spLaptopType.setPadding(0, 0, 0, 60);
        layout.addView(spLaptopType); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spLaptopImei = new Spinner(FacLaptopEdit.this, Spinner.MODE_DROPDOWN);

        spLaptopImei.setAlpha(0);
        spLaptopImei.setSelection(0);
        spLaptopImei.setBackgroundResource(R.color.colorWhite);
        spLaptopImei.setPadding(0, 0, 0, 60);
        layout.addView(spLaptopImei); //Spinner hozzáadása layouthoz

        alert.setView(layout);

        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Válassz dolgozót!")){
                    selectedUser = adapterView.getItemAtPosition(i).toString();
                    nameOfEmployee = db.employeeNameSearch(selectedUser);
                    userId = Integer.parseInt(db.userIdSearch(selectedUser));
                    empId = Integer.parseInt(db.empIdSearch(selectedUser));
                    empName.setText(nameOfEmployee);
                }else selectedUser = "empty";

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spLaptopBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Válassz márkát!")){
                    laptopBrand = spLaptopBrand.getItemAtPosition(i).toString();
                    laptopTypeList = db.laptopTypeList(laptopBrand);
                    laptopTypeList.add(0,"Válassz típust!");
                    ArrayAdapter<String> spinnerDataAdapter2;
                    spinnerDataAdapter2 = new ArrayAdapter(FacLaptopEdit.this, android.R.layout.simple_spinner_item, laptopTypeList);
                    spinnerDataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spLaptopType.setAdapter(spinnerDataAdapter2);
                    spLaptopType.setAlpha(1);
                }else {
                    spLaptopType.setAlpha(0);
                    spLaptopImei.setAlpha(0);
                    laptopBrand = "empty";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spLaptopType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                if(!adapterView.getItemAtPosition(j).equals("Válassz típust!")){
                    laptopType = spLaptopType.getItemAtPosition(j).toString();
                    laptopImeiList = db.laptopImeiList(laptopBrand,laptopType);
                    laptopImeiList.add(0,"Válassz IMEI számot!");
                    ArrayAdapter<String> spinnerDataAdapter3;
                    spinnerDataAdapter3 = new ArrayAdapter(FacLaptopEdit.this, android.R.layout.simple_spinner_item, laptopImeiList);
                    spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spLaptopImei.setAdapter(spinnerDataAdapter3);
                    spLaptopImei.setAlpha(1);
                }else {
                    spLaptopImei.setAlpha(0);
                    laptopType = "empty";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spLaptopImei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Válassz IMEI számot!")){
                    laptopImeiNum = spLaptopImei.getItemAtPosition(i).toString();
                }else laptopImeiNum = "empty";
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
                if(selectedUser.equals("empty") || laptopBrand.equals("empty") || laptopType.equals("empty") || laptopImeiNum.equals("empty")){
                    Toast.makeText(FacLaptopEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }else {
                    int laptopId = db.laptopIdSearch(laptopImeiNum);
                    boolean empLaptopBenefitCheck = db.empLaptopBenefitCheck(empId);
                    if (!empLaptopBenefitCheck) {
                        boolean laptopBenefitCheck = db.laptopBenefitCheck(laptopId);
                        if (!laptopBenefitCheck) {
                            boolean empLaptopBenefitCheckForInactive = db.empLaptopBenefitCheckForInactive(empId);
                            if(!empLaptopBenefitCheckForInactive) {
                                boolean insertBenefit = db.insertBenefit(empId, "l", laptopId, true, userId, userName);
                                if (insertBenefit) {
                                    ls.progressDialog(FacLaptopEdit.this, "Laptop kiadása folyamatban...", "Kiadás");
                                    createList();
                                } else Toast.makeText(FacLaptopEdit.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }else{
                                boolean updateLaptopBenefitForInactive = db.updateLaptopBenefitForInactive(laptopId,true);
                                if(updateLaptopBenefitForInactive){
                                    ls.progressDialog(FacLaptopEdit.this, "Laptop kiadása folyamatban...", "Kiadás");
                                    createList();
                                }else Toast.makeText(FacLaptopEdit.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(FacLaptopEdit.this, "A laptop már ki van adva egy dolgozónak!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(FacLaptopEdit.this, "Ennek a dolgozónak már van kiadva laptop", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.show();
    }

    private void createList() {
        laptopBenefitList = db.viewActiveLaptopBenefit();
        adapter = new SimpleAdapter(FacLaptopEdit.this, laptopBenefitList, R.layout.laptop_benefit_edit,
                new String[]{"EMPLOYEE_NAME","LAPTOPTYPE","LAPTOP_IMEINUMBER","BENEFIT_ID","BENEFIT_STATUS","USER_NAME"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfLaptop,R.id.twLaptopImeiNumber,benefitId,statusId,R.id.twNameOfUser});

        lwFacLaptopEdit.setAdapter(adapter);
    }

    private String getImeiNum(String imeiNum){
        String[] splited = imeiNum.split("\\s+");
        String splittedImeiNum = splited[2];
        return splittedImeiNum;
    }

    private void init() {
        btFacLaptopNew = findViewById(R.id.btFacLaptopNew);
        btFacLaptopMod = findViewById(R.id.btFacLaptopMod);
        btFacLaptopEditBack = findViewById(R.id.btFacLaptopEditBack);
        lwFacLaptopEdit = findViewById(R.id.lwFacLaptopEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacLaptopEdit.this);

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
                startActivity(new Intent(FacLaptopEdit.this, Login.class));
                Animatoo.animateFade(FacLaptopEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
