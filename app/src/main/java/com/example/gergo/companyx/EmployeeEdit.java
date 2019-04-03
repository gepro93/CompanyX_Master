package com.example.gergo.companyx;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EmployeeEdit extends AppCompatActivity {

    private Button btEmployeeEditMod, btEmployeeEditDel, btEmployeeEditBack;
    private ListView lwEmployeeEdit;
    private LoadScreen ls;
    private Database db;
    private ArrayList<HashMap<String, String>> empList;
    private ListAdapter adapter;
    private ArrayList<String> depList, posList;
    private int  selectedDep, selectedPos, selectedModDep, selectedModPos, pos, empId;
    private String selectedEmpId, modEmpName, modEmpBirth, modEmpMoName, modEmpDep, modEmpPos ,modEmpGender, modEmpStatus, modEmpSalary, permission;
    private boolean selectedStatus, selectedGender;
    private Calendar c;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_edit);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        permission = sp.getString("Permission","Nincs adat");

        createList();

        lwEmployeeEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(i);
                selectedEmpId = (String) obj.get("EMPLOYEE_ID");
                modEmpName = (String) obj.get("EMPLOYEE_NAME");
                modEmpGender = (String) obj.get("EMPLOYEE_GENDER");
                modEmpBirth = (String) obj.get("EMPLOYEE_BIRTH");
                modEmpMoName = (String) obj.get("EMPLOYEE_MOTHERS_NAME");
                modEmpStatus = (String) obj.get("EMPLOYEE_STATUS");
                modEmpSalary = (String) obj.get("EMPLOYEE_SALARY");
                modEmpDep = (String) obj.get("DEPARTMENT_NAME");
                modEmpPos = (String) obj.get("POSITION_NAME");
                empId = Integer.parseInt(selectedEmpId);
            }
        });

        btEmployeeEditMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyEmployee();
            }
        });

        btEmployeeEditDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeEdit.this);

                builder.setCancelable(true);
                builder.setTitle("Törlés");
                builder.setMessage("Biztosan törlöd a dolgozót?");

                builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee(empList, adapter); //Osztály törlése
                    }
                });
                builder.show();
            }
        });

        btEmployeeEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Human Resources")){
                    startActivity(new Intent(EmployeeEdit.this, HRMenu.class));
                    Animatoo.animateSlideRight(EmployeeEdit.this);
                    finish();
                }else{
                    startActivity(new Intent(EmployeeEdit.this, EmployeeHandle.class));
                    Animatoo.animateSlideRight(EmployeeEdit.this);
                    finish();
                }
            }
        });
    }

    private void deleteEmployee(ArrayList<HashMap<String, String>> arrayList, ListAdapter listAdapter) {
        int pos = lwEmployeeEdit.getCheckedItemPosition();


        if (pos > -1) {
            Boolean employeeDelete = db.employeeDelete(empId);

            if (employeeDelete) {
                arrayList.remove(pos);
                ls.progressDialog(this, "Dolgozó törlése folyamatban...", "Eltávolítás...");
            } else
                Toast.makeText(EmployeeEdit.this, "Adatbázis hiba a törléskor!", Toast.LENGTH_SHORT).show();
        }
        ((SimpleAdapter) listAdapter).notifyDataSetChanged();
    }

    private void createList() {
        empList = db.viewEmployees();
        adapter = new SimpleAdapter(EmployeeEdit.this, empList, R.layout.employee_edit_row,
                new String[]{"EMPLOYEE_ID","EMPLOYEE_NAME","EMPLOYEE_GENDER","EMPLOYEE_BIRTH","EMPLOYEE_MOTHERS_NAME","EMPLOYEE_STATUS",
                        "EMPLOYEE_SALARY","DEPARTMENT_NAME","POSITION_NAME"},
                new int[]{R.id.twEmpId,R.id.twEmpName,R.id.twEmpGender,R.id.twEmpBirth,R.id.twEmpyMoName,R.id.twEmpStatus,R.id.twEmpSalary,R.id.twEmpDepName,R.id.twEmpPosName});

        lwEmployeeEdit.setAdapter(adapter);
    }

    private void modifyEmployee() {
        pos = lwEmployeeEdit.getCheckedItemPosition();

        if (pos > -1) {

            AlertDialog.Builder alert = new AlertDialog.Builder(EmployeeEdit.this);

            alert.setTitle("Dolgozó módosítása:");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(EmployeeEdit.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText empName = new EditText(EmployeeEdit.this);
            empName.setHint("Név");
            empName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            empName.setGravity(Gravity.CENTER);
            empName.setPadding(0, 20, 0, 20);
            empName.setText(modEmpName);
            layout.addView(empName); //Edit Text hozzáadása layouthoz


            //Spinner létrehozása
            final Spinner spGender = new Spinner(EmployeeEdit.this, Spinner.MODE_DROPDOWN);

            final List<String> genderList = new ArrayList<>();
            genderList.add(0, "Nő");
            genderList.add("Férfi");

            //Nem lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(EmployeeEdit.this, android.R.layout.simple_spinner_item, genderList);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGender.setAdapter(spinnerDataAdapter);
            spGender.setBackgroundResource(R.color.colorWhite);
            spGender.setPadding(0, 30, 0, 30);

            if(modEmpGender.equals("Nő")){
                spGender.setSelection(0);
            }else spGender.setSelection(1);

            layout.addView(spGender); //Spinner hozzáadása layouthoz


            //Edit Text létrehozása
            final EditText birthDate = new EditText(EmployeeEdit.this);
            birthDate.setHint("Születési dátum");
            birthDate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            birthDate.setGravity(Gravity.CENTER);
            birthDate.setText(modEmpBirth);
            birthDate.setFocusable(false);
            birthDate.setClickable(true);
            birthDate.setPadding(0, 30, 0, 30);

            birthDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    dpd = new DatePickerDialog(EmployeeEdit.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                            if (mMonth < 10 && mDay < 10) {
                                birthDate.setText(mYear + "-0" + (mMonth + 1) + "-0" + mDay);
                            } else if (mMonth < 10) {
                                birthDate.setText(mYear + "-0" + (mMonth + 1) + "-" + mDay);
                            } else if (mDay < 10) {
                                birthDate.setText(mYear + "-" + (mMonth + 1) + "-0" + mDay);
                            } else birthDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                        }
                    }, year, month, day);
                    dpd.show();
                }
            });

            layout.addView(birthDate); //Edit Text hozzáadása layouthoz

            //Edit Text létrehozása
            final EditText motherName = new EditText(EmployeeEdit.this);
            motherName.setHint("Anyja neve");
            motherName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            motherName.setGravity(Gravity.CENTER);
            motherName.setPadding(0, 30, 0, 30);
            motherName.setText(modEmpMoName);
            layout.addView(motherName); //Edit Text hozzáadása layouthoz

            //Edit Text létrehozása
            final EditText salary = new EditText(EmployeeEdit.this);
            salary.setHint("Fizetés");
            salary.setInputType(InputType.TYPE_CLASS_NUMBER);
            salary.setGravity(Gravity.CENTER);
            salary.setPadding(0, 30, 0, 30);
            salary.setText(modEmpSalary);
            layout.addView(salary); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spDep = new Spinner(EmployeeEdit.this, Spinner.MODE_DROPDOWN);

            depList = db.departmentList();

            for (int d = 0; d < depList.size(); d++){
                if (depList.get(d).equals(modEmpDep)) {
                    selectedModDep = d;
                }
            }

            //Osztály lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter1;
            spinnerDataAdapter1 = new ArrayAdapter(EmployeeEdit.this, android.R.layout.simple_spinner_item, depList);
            spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDep.setAdapter(spinnerDataAdapter1);
            spDep.setBackgroundResource(R.color.colorWhite);
            spDep.setPadding(0, 30, 0, 30);
            spDep.setSelection(selectedModDep);
            layout.addView(spDep); //Spinner hozzáadása layouthoz


            //Spinner létrehozása
            final Spinner spPos = new Spinner(EmployeeEdit.this, Spinner.MODE_DROPDOWN);

            posList = db.positionList();

            for (int d = 0; d < posList.size(); d++){
                if (posList.get(d).equals(modEmpPos)) {
                    selectedModPos = d;
                }
            }

            //Pozíció lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter2;
            spinnerDataAdapter2 = new ArrayAdapter(EmployeeEdit.this, android.R.layout.simple_spinner_item, posList);
            spinnerDataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPos.setAdapter(spinnerDataAdapter2);
            spPos.setBackgroundResource(R.color.colorWhite);
            spPos.setPadding(0, 30, 0, 30);
            spPos.setSelection(selectedModPos);
            layout.addView(spPos); //Spinner hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner spStatus = new Spinner(EmployeeEdit.this, Spinner.MODE_DROPDOWN);

            final List<String> statusList = new ArrayList<>();
            statusList.add(0, "Inaktív");
            statusList.add("Aktív");

            //Státusz lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter3;
            spinnerDataAdapter3 = new ArrayAdapter(EmployeeEdit.this, android.R.layout.simple_spinner_item, statusList);
            spinnerDataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStatus.setAdapter(spinnerDataAdapter3);
            spStatus.setBackgroundResource(R.color.colorWhite);

            if(modEmpStatus.equals("Aktív")){
                spStatus.setSelection(1);
            }else spStatus.setSelection(0);

            spStatus.setPadding(0, 30, 0, 30);

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
                    selectedDep = adapterView.getSelectedItemPosition()+1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedPos = adapterView.getSelectedItemPosition()+1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

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

            alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String etEmpName = empName.getText().toString();
                    String etMotherName = motherName.getText().toString();
                    String etBirtDate = birthDate.getText().toString();
                    String etSalary = salary.getText().toString();


                    if (etEmpName.equals("") || etMotherName.equals("") || etBirtDate.equals("") || etSalary.equals("")) {
                        Toast.makeText(EmployeeEdit.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                    } else if (modEmpName.equals(etEmpName) && modEmpMoName.equals(etMotherName)) {
                        boolean modifyEmployee = db.modifyEmployee(empId,etEmpName, selectedGender, etBirtDate, etMotherName, selectedStatus, etSalary, selectedDep, selectedPos);
                        if (modifyEmployee) {
                            ls.progressDialog(EmployeeEdit.this, "Dolgozó módosítása folyamatban...", "Módosítás");
                            createList();
                        } else
                            Toast.makeText(EmployeeEdit.this, "Adatbázis hiba módosításkor!", Toast.LENGTH_SHORT).show();
                    }else{
                            boolean employeeCheck = db.employeeCheck(etEmpName, etMotherName);
                            if (!employeeCheck) {
                                boolean modifyEmployee = db.modifyEmployee(empId,etEmpName, selectedGender, etBirtDate, etMotherName, selectedStatus, etSalary, selectedDep, selectedPos);
                                if (modifyEmployee) {
                                    ls.progressDialog(EmployeeEdit.this, "Dolgozó módosítása folyamatban...", "Módosítás");
                                    createList();
                                } else
                                    Toast.makeText(EmployeeEdit.this, "Adatbázis hiba módosításkor!", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(EmployeeEdit.this, "Ilyen dolgozó már létezik!", Toast.LENGTH_SHORT).show();
                        }
                    }
            });
            alert.show();
        }
    }

    private void init() {
        btEmployeeEditMod = findViewById(R.id.btEmployeeEditMod);
        btEmployeeEditDel = findViewById(R.id.btEmployeeEditDel);
        btEmployeeEditBack = findViewById(R.id.btEmployeeEditBack);
        lwEmployeeEdit = findViewById(R.id.lwEmployeeEdit);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeEdit.this);

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
                startActivity(new Intent(EmployeeEdit.this, Login.class));
                Animatoo.animateFade(EmployeeEdit.this);
                finish();
            }
        });
        builder.show();
    }
}
