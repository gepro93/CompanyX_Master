package com.example.gergo.companyx;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HRMenu extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView iwMenuHeader;
    private TextView twLogin;
    private String userName, permission, selectedEmpName, selectedUserName;
    private Database db;
    private LoadScreen ls;
    private ArrayList<String> depList, posList, empList, userList, empListCheck, userListCheck;
    private int selectedDep, selectedPos, userId;
    private boolean selectedStatus, selectedGender;
    private Calendar c;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmenu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");
        permission = sp.getString("Permission","Nincs adat");
        twLogin.setText(userName);
        iwMenuHeader.setImageResource(R.mipmap.ic_nav_hr_round);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navCreateEmployee:
                        createEmployee();
                        break;

                    case R.id.navEmployeeEdit:
                        startActivity(new Intent(HRMenu.this, EmployeeEdit.class));
                        Animatoo.animateSlideLeft(HRMenu.this);
                        finish();
                        break;

                    case R.id.navAddUser:
                        addUser();
                        break;


                    case R.id.navLogout:
                        startActivity(new Intent(HRMenu.this, Login.class));
                        Animatoo.animateFade(HRMenu.this);
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    private void addUser() {

        AlertDialog.Builder alert = new AlertDialog.Builder(HRMenu.this);

        alert.setTitle("Válassz az alábbi lehetőségek közül!");

        alert.setPositiveButton("Törlés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                empListCheck = db.empNameListWithUser();

                if (empListCheck.size()>0){

                    AlertDialog.Builder alert = new AlertDialog.Builder(HRMenu.this);

                    alert.setTitle("Felhasználó törlése a dolgozói profilból");

                    //Linear Layout felépítése
                    LinearLayout layout = new LinearLayout(HRMenu.this);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    //Spinner létrehozása
                    final Spinner spEmpList = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

                    empList = db.empNameListWithUser();
                    empList.add(0,"Válassz dolgozót!");

                    ArrayAdapter<String> spinnerDataAdapter;
                    spinnerDataAdapter = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, empList);
                    spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEmpList.setAdapter(spinnerDataAdapter);
                    spEmpList.setBackgroundResource(R.color.colorWhite);
                    spEmpList.setPadding(0, 30, 0, 30);
                    layout.addView(spEmpList); //Spinner hozzáadása layouthoz


                    alert.setView(layout);

                    spEmpList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedUserName = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    alert.setNeutralButton("Törlés", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!selectedUserName.equals("Válassz dolgozót!")){
                                boolean deleteUserFromEmp = db.deleteUserFromEmp(selectedUserName);
                                if(deleteUserFromEmp){
                                    ls.progressDialog(HRMenu.this, "Felhasználó eltávoltítása a dolgozói profilból...","Törlés");
                                }else Toast.makeText(HRMenu.this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
                            }else Toast.makeText(HRMenu.this, "Hoppá, nem választottál ki dolgozót!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    alert.show();
                }else Toast.makeText(HRMenu.this, "Nem található felhasználó egyik dolgozói profilon sem!", Toast.LENGTH_SHORT).show();
            }
        });


        alert.setNegativeButton("Hozzáadás", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                empListCheck = db.empNameList();
                userListCheck = db.userNameList();

                if(empListCheck.size()>0 && userListCheck.size()>0){

                    AlertDialog.Builder alert = new AlertDialog.Builder(HRMenu.this);

                    alert.setTitle("Felhasználó hozzáadása a dolgozói profilhoz");

                    //Linear Layout felépítése
                    LinearLayout layout = new LinearLayout(HRMenu.this);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    //Spinner létrehozása
                    final Spinner spEmpList = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

                    empList = db.empNameList();
                    empList.add(0,"Válassz dolgozót!");

                    ArrayAdapter<String> spinnerDataAdapter;
                    spinnerDataAdapter = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, empList);
                    spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEmpList.setAdapter(spinnerDataAdapter);
                    spEmpList.setBackgroundResource(R.color.colorWhite);
                    spEmpList.setPadding(0, 60, 0, 60);
                    layout.addView(spEmpList); //Spinner hozzáadása layouthoz

                    //Spinner létrehozása
                    final Spinner spUserList = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

                    userList = db.userNameList();
                    userList.add(0,"Válassz felhasználónevet!");

                    ArrayAdapter<String> spinnerDataAdapter1;
                    spinnerDataAdapter1 = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, userList);
                    spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spUserList.setAdapter(spinnerDataAdapter1);
                    spUserList.setBackgroundResource(R.color.colorWhite);
                    spUserList.setPadding(0, 60, 0, 60);
                    layout.addView(spUserList); //Spinner hozzáadása layouthoz

                    alert.setView(layout);

                    spEmpList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedEmpName = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    spUserList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedUserName = adapterView.getItemAtPosition(i).toString();
                            userId = Integer.parseInt(db.userIdSearch(selectedUserName));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    alert.setNeutralButton("Mentés", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (!selectedEmpName.equals("Válassz dolgozót!") || !selectedUserName.equals("Válassz felhasználónevet!")){
                            boolean addUserToEmp = db.addUserToEmp(selectedEmpName,userId);
                            if(addUserToEmp){
                                ls.progressDialog(HRMenu.this, "Felhasználó hozzáadása a dolgozói profilhoz...", "Hozzáadás");
                                }
                            }else Toast.makeText(HRMenu.this, "Hoppá, nem válaszottál dolgozót vagy felhasználót!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    alert.show();
                }else if(empListCheck.size() == 0 && userListCheck.size()>0){Toast.makeText(HRMenu.this, "Minden dolgozó kiosztva!", Toast.LENGTH_SHORT).show();}
                else if(empListCheck.size()>0 && userListCheck.size() == 0){Toast.makeText(HRMenu.this, "Minden felhasználó kiosztva!", Toast.LENGTH_SHORT).show();}
                else if(empListCheck.size() == 0 && userListCheck.size() == 0){Toast.makeText(HRMenu.this, "Összes dolgozó és felhasználó kiosztva!", Toast.LENGTH_SHORT).show();}
            }
        });

        alert.setNeutralButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.show();

    }

    private void createEmployee() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HRMenu.this);

        alert.setTitle("Dolgozó felvétele:");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(HRMenu.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText empName = new EditText(HRMenu.this);
        empName.setHint("Név");
        empName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        empName.setGravity(Gravity.CENTER);
        empName.setPadding(0, 30, 0, 30);
        layout.addView(empName); //Edit Text hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spGender = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

        final List<String> genderList = new ArrayList<>();
        genderList.add(0, "Nő");
        genderList.add("Férfi");

        //Nem lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, genderList);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(spinnerDataAdapter);
        spGender.setBackgroundResource(R.color.colorWhite);
        spGender.setPadding(0,30,0,30);
        layout.addView(spGender); //Spinner hozzáadása layouthoz


        //Edit Text létrehozása
        final EditText birthDate = new EditText(HRMenu.this);
        birthDate.setHint("Születési dátum");
        birthDate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        birthDate.setGravity(Gravity.CENTER);
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

                dpd = new DatePickerDialog(HRMenu.this, new DatePickerDialog.OnDateSetListener() {
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
        final EditText motherName = new EditText(HRMenu.this);
        motherName.setHint("Anyja neve");
        motherName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        motherName.setGravity(Gravity.CENTER);
        motherName.setPadding(0, 30, 0, 30);
        layout.addView(motherName); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText salary = new EditText(HRMenu.this);
        salary.setHint("Fizetés");
        salary.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        salary.setGravity(Gravity.CENTER);
        salary.setInputType(InputType.TYPE_CLASS_NUMBER);
        salary.setPadding(0, 30, 0, 30);
        layout.addView(salary); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spDep = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

        depList = db.departmentList();
        depList.add(0,"Válassz osztályt!");

        //Osztály lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter1;
        spinnerDataAdapter1 = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, depList);
        spinnerDataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDep.setAdapter(spinnerDataAdapter1);
        spDep.setBackgroundResource(R.color.colorWhite);
        spDep.setPadding(0,30,0,30);
        layout.addView(spDep); //Spinner hozzáadása layouthoz


        //Spinner létrehozása
        final Spinner spPos = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

        posList = db.positionList();
        posList.add(0,"Válassz pozíciót!");

        //Pozíció lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter2;
        spinnerDataAdapter2 = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, posList);
        spinnerDataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPos.setAdapter(spinnerDataAdapter2);
        spPos.setBackgroundResource(R.color.colorWhite);
        spPos.setPadding(0,30,0,30);
        layout.addView(spPos); //Spinner hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spStatus = new Spinner(HRMenu.this, Spinner.MODE_DROPDOWN);

        final List<String> statusList = new ArrayList<>();
        statusList.add(0, "Aktív");
        statusList.add("Inaktív");

        //Pozíció lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter3;
        spinnerDataAdapter3 = new ArrayAdapter(HRMenu.this, android.R.layout.simple_spinner_item, statusList);
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
                    Toast.makeText(HRMenu.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean employeeCheck = db.employeeCheck(etEmpName, etMotherName);
                    if (!employeeCheck) {
                        boolean createMobile = db.insertEmployee(etEmpName,selectedGender,etBirtDate,etMotherName,selectedStatus,etSalary,selectedDep,selectedPos);
                        if (createMobile) {
                            ls.progressDialog(HRMenu.this, "Dolgozó felvétele folyamatban...", "Létrehozás");
                        } else
                            Toast.makeText(HRMenu.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(HRMenu.this, "Ilyen dolgozó már létezik!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.show();
    }

    private void init() {
        iwMenuHeader = findViewById(R.id.iwMenuHeader);
        db =  new Database(this);
        ls = new LoadScreen();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Főmenü");
        drawer = findViewById(R.id.hr_layout);
        navigationView = findViewById(R.id.nav_view_hr);
        twLogin = findViewById(R.id.twLogin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HRMenu.this);

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
                startActivity(new Intent(HRMenu.this, Login.class));
                Animatoo.animateFade(HRMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
