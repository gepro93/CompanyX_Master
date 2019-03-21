package com.example.gergo.companyx;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeeHandle extends AppCompatActivity {

    private Button btEmployeeCreate, btEmployeeEdit, btEmployeeList, btEmployeeHandleBack;
    private Database db;
    private LoadScreen ls;
    private ArrayList<String> depList, posList;
    private int selectedDep, selectedPos;
    private boolean selectedStatus, selectedGender;
    private Calendar c;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_handle);
        init();

        btEmployeeCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEmployee();
            }
        });

        btEmployeeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeHandle.this,EmployeeEdit.class));
                finish();
            }
        });

        btEmployeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeHandle.this,EmployeeList.class));
                finish();
            }
        });

        btEmployeeHandleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeHandle.this,AdminMenu.class));
                finish();
            }
        });
    }

    private void createEmployee() {
        AlertDialog.Builder alert = new AlertDialog.Builder(EmployeeHandle.this);

        alert.setTitle("Dolgozó felvétele:");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(EmployeeHandle.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText empName = new EditText(EmployeeHandle.this);
        empName.setHint("Név");
        empName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        empName.setGravity(Gravity.CENTER);
        empName.setPadding(0, 30, 0, 30);
        layout.addView(empName); //Edit Text hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spGender = new Spinner(EmployeeHandle.this, Spinner.MODE_DROPDOWN);

        final List<String> genderList = new ArrayList<>();
        genderList.add(0, "Nő");
        genderList.add("Férfi");

        //Nem lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(EmployeeHandle.this, android.R.layout.simple_spinner_item, genderList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(spinnerDataAdapter);
        spGender.setBackgroundResource(R.color.colorWhite);
        spGender.setPadding(0,30,0,30);
        layout.addView(spGender); //Spinner hozzáadása layouthoz


        //Edit Text létrehozása
        final EditText birthDate = new EditText(EmployeeHandle.this);
        birthDate.setHint("Születési dátum");
        birthDate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        birthDate.setGravity(Gravity.CENTER);
        birthDate.setPadding(0, 30, 0, 30);

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(EmployeeHandle.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        if(mMonth < 10 && mDay < 10){
                            birthDate.setText(mYear + "-0" + (mMonth+1) + "-0" + mDay);
                        }else if (mMonth < 10){
                            birthDate.setText(mYear + "-0" + (mMonth+1) + "-" + mDay);
                        }else if(mDay < 10){
                            birthDate.setText(mYear + "-" + (mMonth+1) + "-0" + mDay);
                        }else birthDate.setText(mYear + "-" + (mMonth+1) + "-" + mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        layout.addView(birthDate); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText motherName = new EditText(EmployeeHandle.this);
        motherName.setHint("Anyja neve");
        motherName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        motherName.setGravity(Gravity.CENTER);
        motherName.setPadding(0, 30, 0, 30);
        layout.addView(motherName); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText salary = new EditText(EmployeeHandle.this);
        salary.setHint("Fizetés");
        salary.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        salary.setGravity(Gravity.CENTER);
        salary.setPadding(0, 30, 0, 30);
        layout.addView(salary); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spDep = new Spinner(EmployeeHandle.this, Spinner.MODE_DROPDOWN);

        depList = db.departmentList();
        depList.add(0,"Válassz osztályt!");

        //Osztály lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter1;
        spinnerDataAdapter1 = new ArrayAdapter(EmployeeHandle.this, android.R.layout.simple_spinner_item, depList);
        spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDep.setAdapter(spinnerDataAdapter1);
        spDep.setBackgroundResource(R.color.colorWhite);
        spDep.setPadding(0,30,0,30);
        layout.addView(spDep); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spPos = new Spinner(EmployeeHandle.this, Spinner.MODE_DROPDOWN);

        posList = db.positionList();
        posList.add(0,"Válassz pozíciót!");

        //Pozíció lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter2;
        spinnerDataAdapter2 = new ArrayAdapter(EmployeeHandle.this, android.R.layout.simple_spinner_item, posList);
        spinnerDataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPos.setAdapter(spinnerDataAdapter2);
        spPos.setBackgroundResource(R.color.colorWhite);
        spPos.setPadding(0,30,0,30);
        layout.addView(spPos); //Spinner hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spStatus = new Spinner(EmployeeHandle.this, Spinner.MODE_DROPDOWN);

        final List<String> statusList = new ArrayList<>();
        statusList.add(0, "Akív");
        statusList.add("Inaktív");

        //Pozíció lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter3;
        spinnerDataAdapter3 = new ArrayAdapter(EmployeeHandle.this, android.R.layout.simple_spinner_item, statusList);
        spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(spinnerDataAdapter3);
        spStatus.setBackgroundResource(R.color.colorWhite);
        spStatus.setPadding(0,30,0,30);
        layout.addView(spStatus); //Spinner hozzáadása layouthoz

        alert.setView(layout);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Nő")) {
                    selectedGender = false;
                } else {
                    selectedGender = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Válassz osztályt!")) {
                    selectedDep = 0;
                } else {
                    selectedDep = adapterView.getSelectedItemPosition();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Válassz pozíciót!")) {
                    selectedPos = 0;
                } else {
                    selectedPos = adapterView.getSelectedItemPosition();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Inakív")) {
                    selectedStatus = false;
                } else {
                    selectedStatus = true;
                }
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
                    String etEmpName = empName.getText().toString();
                    String etMotherName = motherName.getText().toString();
                    String etBirtDate = birthDate.getText().toString();
                    String etSalary = salary.getText().toString();

                    if (etEmpName.equals("") || etMotherName.equals("") || etBirtDate.equals("") || etSalary.equals("") || selectedDep == 0 || selectedPos == 0) {
                        Toast.makeText(EmployeeHandle.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean employeeCheck = db.employeeCheck(etEmpName, etMotherName);
                        if (!employeeCheck) {
                            boolean createMobile = db.insertEmployee(etEmpName,selectedGender,etBirtDate,etMotherName,selectedStatus,etSalary,selectedDep,selectedPos);
                            if (createMobile) {
                                ls.progressDialog(EmployeeHandle.this, "Dolgozó felvétele folyamatban...", "Létrehozás");
                            } else
                                Toast.makeText(EmployeeHandle.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(EmployeeHandle.this, "Ilyen dolgozó már létezik!", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        alert.show();
    }

    private void init() {
        btEmployeeCreate = findViewById(R.id.btEmployeeCreate);
        btEmployeeEdit = findViewById(R.id.btEmployeeEdit);
        btEmployeeList = findViewById(R.id.btEmployeeList);
        btEmployeeHandleBack = findViewById(R.id.btEmployeeHandleBack);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
