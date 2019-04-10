package com.example.gergo.companyx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper{

    //adatbázis létrehozása
    public static final String DATABASE_NAME = "companyx.db";

    //adatbázis konstruktora
    public Database(Context context){super(context,DATABASE_NAME,null,1);}

    //felhasznalok tábla és oszlopok definiálása
    public static final String USER_TABLE = "felhasznalok";

    public static final String USER_ID = "felh_id";
    public static final String USER_NAME = "felhNeve";
    public static final String USER_PASSWORD = "felhJelszo";
    public static final String USER_STATUS = "felhStatusz";
    public static final String USER_PERMISSION_ID = "jogosultsag_id";

    //dolgozok tábla és oszlopok definiálása
    public static final String EMPLOYEE_TABLE = "dolgozok";

    public static final String EMPLOYEE_ID = "dolg_id";
    public static final String EMPLOYEE_NAME = "dolgNev";
    public static final String EMPLOYEE_GENDER = "dolgNeme";
    public static final String EMPLOYEE_BIRTH = "dolgSzuletesiDatum";
    public static final String EMPLOYEE_MOTHERS_NAME = "dolgAnyjaNeve";
    public static final String EMPLOYEE_STATUS = "dolgStatusz";
    public static final String EMPLOYEE_SALARY = "dolgFizetes";
    public static final String EMPLOYEE_DEPARTMANET_ID = "osztaly_id";
    public static final String EMPLOYEE_POSITION_ID = "beosztas_id";
    public static final String EMPLOYEE_USER_ID = "felh_id";

    //jogosultsagok tábla és oszlopok definiálása
    public static final String PERMISSION_TABLE = "jogosultsagok";

    public static final String PERMISSION_ID = "jogosultsag_id";
    public static final String PERMISSION_NAME = "jogosultsagNeve";

    //beosztások tábla és oszlopok definiálása
    public static final String POSITION_TABLE = "beosztasok";

    public static final String POSITION_ID = "beosztas_id";
    public static final String POSITION_NAME = "beosztasNeve";
    public static final String POSITION_GRADE_ID = "grade_id";

    //osztályok tábla és oszlopok definiálása
    public static final String DEPARTMENT_TABLE = "osztalyok";

    public static final String DEPARTMENT_ID = "osztaly_id";
    public static final String DEPARTMENT_NAME = "osztalyNeve";

    //autók tábla és oszlopok definiálása
    public static final String CAR_TABLE = "autok";

    public static final String CAR_ID = "auto_id";
    public static final String CAR_LICENSENUMBER = "autoRendszam";
    public static final String CAR_VINNUMBER = "autoAlvazszam";
    public static final String CAR_MOTDATE = "autoMuszakierveny";
    public static final String CAR_MODEL_ID = "autoGyartmany_id";
    public static final String CARTYPE = "autoTipus";
    public static final String MOTDATE = "muszakiDatum";

    //autó gyártámányok tábla és oszlopok definiálása
    public static final String MODELOFCAR_TABLE = "auto_gyartmanyok";

    public static final String MODELOFCAR_ID = "autoGyartmany_id";
    public static final String MODELOFCAR_BRAND = "autoMarka";
    public static final String MODELOFCAR_TYPE = "autoTipus";
    public static final String MODELOFCAR_GRADE_ID = "grade_id";


    //mobilok tábla és oszlopok definiálása
    public static final String MOBILE_TABLE = "mobilok";

    public static final String MOBIL_ID = "mobil_id";
    public static final String MOBIL_IMEINUMBER = "mobilImeiSzam";
    public static final String MOBIL_MODEL_ID = "mobilGyartmany_id";
    public static final String MOBILTYPE = "mobilTipus";

    //mobil gyártámányok tábla és oszlopok definiálása
    public static final String MODELOFMOBIL_TABLE = "mobil_gyartmanyok";

    public static final String MODELOFMOBIL_ID = "mobilGyartmany_id";
    public static final String MODELOFMOBIL_BRAND = "mobilMarka";
    public static final String MODELOFMOBIL_TYPE = "mobilTipus";
    public static final String MODELOFMOBIL_GRADE_ID = "grade_id";

    //laptopok tábla és oszlopok definiálása
    public static final String LAPTOP_TABLE = "laptopok";

    public static final String LAPTOP_ID = "laptop_id";
    public static final String LAPTOP_IMEINUMBER = "laptopImeiSzam";
    public static final String LAPTOP_MODEL_ID = "laptopGyartmany_id";
    public static final String LAPTOPTYPE = "laptopTupus";

    //laptop gyártámányok tábla és oszlopok definiálása
    public static final String MODELOFLAPTOP_TABLE = "laptop_gyartmanyok";

    public static final String MODELOFLAPTOP_ID = "laptopGyartmany_id";
    public static final String MODELOFLAPTOP_BRAND = "laptopMarka";
    public static final String MODELOFLAPTOP_TYPE = "laptopTipus";
    public static final String MODELOFLAPTOP_GRADE_ID = "grade_id";

    //gradek tábla és oszlopok definiálása
    public static final String GRADE_TABLE = "gradek";

    public static final String GRADE_ID = "grade_id";
    public static final String GRADE_NAME = "grade_name";
    public static final String SALARY_MIN_VALUE = "fizetesAlsoErtek";
    public static final String SALARY_MAX_VALUE = "fizetesFelsoErtek";

    //utak tábla és oszlopok definiálása
    public static final String TRIP_TABLE = "utak";

    public static final String TRIP_ID = "ut_id";
    public static final String TRIP_GPS_START = "gpsIndulas";
    public static final String TRIP_GPS_ARRIVAL = "gpsErkezes";
    public static final String TRIP_KM_START = "kmIndulas";
    public static final String TRIP_KM_ARRIVAL = "kmErkezes";
    public static final String TRIP_TIMESTAMP = "rogzitesIdo";
    public static final String TRIP_EMPLOYEE_ID = "dolgozo_id";
    public static final String TRIP_CAR_ID = "auto_id";

    //juttatások tábla és oszlopok definiálása
    public static final String BENEFIT_TABLE = "juttatasok";

    public static final String BENEFIT_ID = "juttatas_id";
    public static final String BENEFIT_EMPLOYEE_ID = "dolgozo_id";
    public static final String BENEFIT_ITEM = "eszkoz";
    public static final String BENEFIT_ITEM_ID = "eszkoz_id";
    public static final String BENEFIT_STATUS = "status";
    public static final String BENEFIT_USER_ID = "user_id";
    public static final String BENEFIT_FAC_USER = "fac_user_name";

    //juttatások_log tábla és oszlopok definiálása
    public static final String BENEFIT_LOG_TABLE = "juttatasok_log";

    public static final String BENEFIT_LOG_ID = "log_id";
    public static final String BENEFIT_LOG_BENEFIT_ID = "juttatas_id";
    public static final String BENEFIT_LOG_USER_ID = "user_id";
    public static final String BENEFIT_LOG_ITEM = "eszkoz";
    public static final String BENEFIT_LOG_ITEM_ID = "eszkoz_id";
    public static final String BENEFIT_LOG_STATUS = "status";
    public static final String BENEFIT_LOG_DATETIME = "datetime";



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Táblák létrehozása
        sqLiteDatabase.execSQL("CREATE TABLE "
                + USER_TABLE + " ("
                + USER_ID + " integer primary key autoincrement, "
                + USER_NAME + " text not null, "
                + USER_PASSWORD + " text not null, "
                + USER_STATUS + " bool not null,"
                + USER_PERMISSION_ID + " integer not null,"
                + " FOREIGN KEY ("+USER_PERMISSION_ID+") REFERENCES "+PERMISSION_TABLE+"("+PERMISSION_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + PERMISSION_TABLE + " ("
                + PERMISSION_ID + " integer primary key autoincrement, "
                + PERMISSION_NAME + " text not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + EMPLOYEE_TABLE + " ("
                + EMPLOYEE_ID + " integer primary key autoincrement, "
                + EMPLOYEE_NAME + " text not null, "
                + EMPLOYEE_GENDER + " boolean not null, "
                + EMPLOYEE_BIRTH + " date not null, "
                + EMPLOYEE_MOTHERS_NAME + " text not null, "
                + EMPLOYEE_STATUS + " boolean not null, "
                + EMPLOYEE_SALARY + " integer not null, "
                + EMPLOYEE_DEPARTMANET_ID + " integer not null, "
                + EMPLOYEE_POSITION_ID + " integer not null, "
                + EMPLOYEE_USER_ID + " integer, "
                + " FOREIGN KEY ("+EMPLOYEE_DEPARTMANET_ID+") REFERENCES "+DEPARTMENT_TABLE+"("+DEPARTMENT_ID+"),"
                + " FOREIGN KEY ("+EMPLOYEE_POSITION_ID+") REFERENCES "+POSITION_TABLE+"("+POSITION_ID+"),"
                + " FOREIGN KEY ("+EMPLOYEE_USER_ID+") REFERENCES "+USER_TABLE+"("+USER_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + POSITION_TABLE + " ("
                + POSITION_ID + " integer primary key autoincrement, "
                + POSITION_NAME + " text not null, "
                + POSITION_GRADE_ID + " integer not null, "
                + " FOREIGN KEY ("+POSITION_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + DEPARTMENT_TABLE + " ("
                + DEPARTMENT_ID + " integer primary key autoincrement, "
                + DEPARTMENT_NAME + " text not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + CAR_TABLE + " ("
                + CAR_ID + " integer primary key autoincrement, "
                + CAR_LICENSENUMBER + " text not null, "
                + CAR_VINNUMBER + " text not null, "
                + CAR_MOTDATE + " date not null,"
                + CAR_MODEL_ID + " integer not null,"
                + " FOREIGN KEY ("+CAR_MODEL_ID+") REFERENCES "+MODELOFCAR_TABLE+"("+MODELOFCAR_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MODELOFCAR_TABLE + " ("
                + MODELOFCAR_ID + " integer primary key autoincrement, "
                + MODELOFCAR_BRAND + " text not null,"
                + MODELOFCAR_TYPE + " text not null,"
                + MODELOFCAR_GRADE_ID + " integer not null,"
                + " FOREIGN KEY ("+MODELOFCAR_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MOBILE_TABLE + " ("
                + MOBIL_ID + " integer primary key autoincrement, "
                + MOBIL_IMEINUMBER + " text not null, "
                + MOBIL_MODEL_ID + " integer not null, "
                + " FOREIGN KEY ("+MOBIL_MODEL_ID+") REFERENCES "+MODELOFMOBIL_TABLE+"("+MODELOFMOBIL_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MODELOFMOBIL_TABLE + " ("
                + MODELOFMOBIL_ID + " integer primary key autoincrement, "
                + MODELOFMOBIL_BRAND + " text not null,"
                + MODELOFMOBIL_TYPE + " text not null,"
                + MODELOFMOBIL_GRADE_ID + " integer not null,"
                + " FOREIGN KEY ("+MODELOFMOBIL_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + LAPTOP_TABLE + " ("
                + LAPTOP_ID + " integer primary key autoincrement, "
                + LAPTOP_IMEINUMBER + " text not null, "
                + LAPTOP_MODEL_ID + " integer not null, "
                + " FOREIGN KEY ("+LAPTOP_MODEL_ID+") REFERENCES "+MODELOFLAPTOP_TABLE+"("+MODELOFLAPTOP_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MODELOFLAPTOP_TABLE + " ("
                + MODELOFLAPTOP_ID + " integer primary key autoincrement, "
                + MODELOFLAPTOP_BRAND + " text not null,"
                + MODELOFLAPTOP_TYPE + " text not null,"
                + MODELOFLAPTOP_GRADE_ID + " integer not null,"
                + " FOREIGN KEY ("+MODELOFLAPTOP_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + GRADE_TABLE + " ("
                + GRADE_ID + " integer primary key autoincrement, "
                + GRADE_NAME + " text not null, "
                + SALARY_MIN_VALUE + " integer not null,"
                + SALARY_MAX_VALUE + " integer not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + TRIP_TABLE + " ("
                + TRIP_ID + " integer primary key autoincrement, "
                + TRIP_EMPLOYEE_ID + " integer not null,"
                + TRIP_GPS_START + " text not null,"
                + TRIP_GPS_ARRIVAL + " text not null,"
                + TRIP_KM_START + " integer not null,"
                + TRIP_KM_ARRIVAL + " integer not null,"
                + TRIP_TIMESTAMP + " timestamp not null,"
                + " FOREIGN KEY ("+TRIP_EMPLOYEE_ID+") REFERENCES "+EMPLOYEE_TABLE+"("+EMPLOYEE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + BENEFIT_TABLE + " ("
                + BENEFIT_ID + " integer primary key autoincrement, "
                + BENEFIT_EMPLOYEE_ID + " integer not null, "
                + BENEFIT_ITEM + " char not null, "
                + BENEFIT_ITEM_ID + " integer not null, "
                + BENEFIT_STATUS + " boolean not null,"
                + BENEFIT_USER_ID + " integer not null,"
                + BENEFIT_FAC_USER + " text not null)");


        sqLiteDatabase.execSQL("CREATE TABLE "
                + BENEFIT_LOG_TABLE + " ("
                + BENEFIT_LOG_ID + " integer primary key autoincrement, "
                + BENEFIT_LOG_BENEFIT_ID + " integer not null, "
                + BENEFIT_LOG_USER_ID + " integer not null, "
                + BENEFIT_LOG_ITEM + " char not null, "
                + BENEFIT_LOG_ITEM_ID + " integer not null, "
                + BENEFIT_LOG_STATUS + " boolean not null,"
                + BENEFIT_LOG_DATETIME + " timestamp not null)");

        sqLiteDatabase.execSQL("CREATE TRIGGER juttatasInsert AFTER INSERT " +
                "ON juttatasok " +
                "BEGIN " +
                "   INSERT INTO juttatas_log(juttatas_id, user_id, eszkoz, eszkoz_id, status, fac_user_name) VALUES (new.juttatas_id, new.user_id, new.eszkoz, new.eszkoz_id, new.status, new.fac_user_name); " +
                "END");

        sqLiteDatabase.execSQL("CREATE TRIGGER juttatasUpdate AFTER UPDATE " +
                "ON juttatasok " +
                "BEGIN " +
                "   INSERT INTO juttatas_log(juttatas_id, user_id, eszkoz, eszkoz_id, status, fac_user_name) VALUES (new.juttatas_id, new.user_id, new.eszkoz, new.eszkoz_id, new.status, new.fac_user_name); " +
                "END");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PERMISSION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EMPLOYEE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + POSITION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DEPARTMENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CAR_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MODELOFCAR_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOBILE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MODELOFMOBIL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LAPTOP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MODELOFLAPTOP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GRADE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BENEFIT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BENEFIT_LOG_TABLE);
    }

    //Felhasználó felvétel
    public boolean insertUser(String felhNeve, String felhJelszo, Boolean felhStatusz, int jogosultsag_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, felhNeve);
        contentValues.put(USER_PASSWORD, felhJelszo);
        contentValues.put(USER_STATUS, felhStatusz);
        contentValues.put(USER_PERMISSION_ID, jogosultsag_id);

        long eredmeny = db.insert(USER_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Dolgozó felvétel
    public boolean insertEmployee(String dolgNev, boolean dolgNeme, String dolgSzuletesiDatum, String dolgAnyjaNeve,
                                  boolean dolgStatusz, String dolgFizetes, int osztaly_id, int beosztas_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_NAME, dolgNev);
        contentValues.put(EMPLOYEE_GENDER, dolgNeme);
        contentValues.put(EMPLOYEE_BIRTH, dolgSzuletesiDatum);
        contentValues.put(EMPLOYEE_MOTHERS_NAME, dolgAnyjaNeve);
        contentValues.put(EMPLOYEE_STATUS, dolgStatusz);
        contentValues.put(EMPLOYEE_SALARY, dolgFizetes);
        contentValues.put(EMPLOYEE_DEPARTMANET_ID, osztaly_id);
        contentValues.put(EMPLOYEE_POSITION_ID, beosztas_id);

        long eredmeny = db.insert(EMPLOYEE_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Jogosultság felvétel
    public boolean insertPermission(String jogosultsagNeve){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERMISSION_NAME, jogosultsagNeve);

        long eredmeny = db.insert(PERMISSION_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }


    //Beosztás felvétel
    public boolean insertPosition(String beosztasNeve, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_NAME, beosztasNeve);
        contentValues.put(POSITION_GRADE_ID, grade_id);

        long eredmeny = db.insert(POSITION_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Osztály felvétel
    public boolean insertDepartment(String osztalyNeve){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEPARTMENT_NAME, osztalyNeve);

        long eredmeny = db.insert(DEPARTMENT_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Autó felvétel
    public boolean insertCar(String autoRendszam, String autoAlvazszam, Date autoMuszakierveny, int autoGyartmany_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAR_LICENSENUMBER, autoRendszam);
        contentValues.put(CAR_VINNUMBER, autoAlvazszam);
        contentValues.put(CAR_MOTDATE, String.valueOf(autoMuszakierveny));
        contentValues.put(CAR_MODEL_ID, autoGyartmany_id);

        long eredmeny = db.insert(CAR_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Autó gyártmány felvétel
    public boolean insertModelOfCar(String autoMarka, String autoTipus, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODELOFCAR_BRAND, autoMarka);
        contentValues.put(MODELOFCAR_TYPE, autoTipus);
        contentValues.put(MODELOFCAR_GRADE_ID, grade_id);

        long eredmeny = db.insert(MODELOFCAR_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Mobil felvétel
    public boolean insertMobile(String mobilImeiSzam, int mobilGyartmany_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOBIL_IMEINUMBER, mobilImeiSzam);
        contentValues.put(MOBIL_MODEL_ID, mobilGyartmany_id);

        long eredmeny = db.insert(MOBILE_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Mobil gyártmány felvétel
    public boolean insertModelOfMobile(String mobilMarka, String mobilTipus, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODELOFMOBIL_BRAND, mobilMarka);
        contentValues.put(MODELOFMOBIL_TYPE, mobilTipus);
        contentValues.put(MODELOFMOBIL_GRADE_ID, grade_id);

        long eredmeny = db.insert(MODELOFMOBIL_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Laptop felvétel
    public boolean insertLaptop(String laptopImeiSzam, int laptopGyartmany_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LAPTOP_IMEINUMBER, laptopImeiSzam);
        contentValues.put(LAPTOP_MODEL_ID, laptopGyartmany_id);

        long eredmeny = db.insert(LAPTOP_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Laptop gyártmány felvétel
    public boolean insertModelOfLaptop(String laptopMarka, String laptopTipus, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODELOFLAPTOP_BRAND, laptopMarka);
        contentValues.put(MODELOFLAPTOP_TYPE, laptopTipus);
        contentValues.put(MODELOFLAPTOP_GRADE_ID, grade_id);

        long eredmeny = db.insert(MODELOFLAPTOP_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }


    //Út felvétel
    public boolean insertTrip(int dolgozo_id,String gpsIndulas, String gpsErkezes, int kmIndulas, int kmErkezes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIP_EMPLOYEE_ID, dolgozo_id);
        contentValues.put(TRIP_GPS_START, gpsIndulas);
        contentValues.put(TRIP_GPS_ARRIVAL, gpsErkezes);
        contentValues.put(TRIP_KM_START, kmIndulas);
        contentValues.put(TRIP_KM_ARRIVAL, kmErkezes);

        long eredmeny = db.insert(TRIP_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Juttatás felvétel
    public boolean insertBenefit(int dolgId, String eszkoz,
                                     int eszkoz_id, boolean status,int dolgozo_id, String facUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BENEFIT_EMPLOYEE_ID, dolgId);
        contentValues.put(BENEFIT_ITEM, eszkoz);
        contentValues.put(BENEFIT_ITEM_ID, eszkoz_id);
        contentValues.put(BENEFIT_STATUS, status);
        contentValues.put(BENEFIT_USER_ID, dolgozo_id);
        contentValues.put(BENEFIT_USER_ID, facUser);

        long eredmeny = db.insert(BENEFIT_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }


    /*
    * FELHASZNÁLÓVAL KAPCSOLATOS ADATBÁZIS PARANCSOK
    * */

        //Felhasználó létezésének ellenőzése belépéshez
        public Boolean userCheck(String userName, String userPassword){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE felhNeve=? AND felhJelszo=?", new String[]{userName,userPassword});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Felhasználó nevének ellenőzése belépéshez
        public Boolean userNameCheck(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE felhNeve=?", new String[]{userName});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Felhasználó jogsosultságánnak ellenőzése belépéshez
        public Integer userPermissionCheck(String userName, String userPassword){
            SQLiteDatabase db = this.getReadableDatabase();
            int jogosultsagID = 0;
            Cursor cursor = db.rawQuery("SELECT jogosultsag_id FROM " + USER_TABLE + " WHERE felhNeve=? AND felhJelszo=?", new String[]{userName,userPassword});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                jogosultsagID = cursor.getInt(cursor.getColumnIndex("jogosultsag_id"));
            }
            return jogosultsagID;
        }

        //Felhasználó státuszának ellenőzése belépéshez
        public Boolean userStatusCheck(String userName, String userPassword){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT felhStatusz FROM " + USER_TABLE + " WHERE felhNeve=? AND felhJelszo=?", new String[]{userName,userPassword});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }


        //Felhasználók adatainak feltöltése listába
        public ArrayList<HashMap<String,String>> viewUsers(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> userList = new ArrayList<>();

            String query = "SELECT u."+ USER_NAME + ", p."+ PERMISSION_NAME +", u." + USER_STATUS +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + PERMISSION_TABLE + " AS p ON u." + USER_PERMISSION_ID + " = p."+ PERMISSION_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> user = new HashMap<>();
                user.put("USER_NAME",cursor.getString(cursor.getColumnIndex(USER_NAME)));
                user.put("PERMISSION_NAME",cursor.getString(cursor.getColumnIndex(PERMISSION_NAME)));

                    switch (cursor.getInt(cursor.getColumnIndex(USER_STATUS))){
                        case 0:
                            user.put("USER_STATUS","Inaktív");
                            break;

                        case 1:
                            user.put("USER_STATUS","Aktív");
                            break;
                        default: user.put("USER_STATUS","Nincs");
                        break;
                }

                userList.add(user);
            }
            return userList;
        }


        //Felhasználók nevei feltöltése listába
        public ArrayList<String> viewUsersByName(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> userList = new ArrayList<>();

            String query = "SELECT "+ USER_NAME + " AS felhNeve" +
                    " FROM " + USER_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                userList.add(cursor.getString(cursor.getColumnIndex("felhNeve")));
            }
            return userList;
        }

        //Felhasználók státuszai feltöltése listába
        public ArrayList<String> viewUsersByStatus(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> userList = new ArrayList<>();

            String query = "SELECT "+ USER_STATUS + " AS felhStatusz" +
                    " FROM " + USER_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                userList.add(cursor.getString(cursor.getColumnIndex("felhStatusz")));
            }
            return userList;
        }

        //Felhasználók jogosultságai feltöltése listába
        public ArrayList<String> viewUsersByPerm(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> userList = new ArrayList<>();

            String query = "SELECT "+ PERMISSION_NAME + " AS jogosultsagNeve" +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + PERMISSION_TABLE + " AS p ON u." + USER_PERMISSION_ID + " = p."+ PERMISSION_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                userList.add(cursor.getString(cursor.getColumnIndex("jogosultsagNeve")));
            }
            return userList;
        }


        //Felhasználó jogosultságának kiírása
        public Boolean getUserPermission(String jogosultsagID){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery
                    ("SELECT jogosultsagNeve " +
                            "FROM " + USER_TABLE + " as u " +
                            "LEFT JOIN "+ PERMISSION_TABLE + " as p ON u.jogosultsag_id = p.jogosultsag_id " +
                            "WHERE jogosultsag_id=?", new String[]{jogosultsagID});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Felhasználó törlése
        public Boolean UserDelete(String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(USER_TABLE,USER_NAME + "="+'"'+userName+'"',null) > 0;
        }

        //Felhasználó módosítása
        public Boolean userModify(String oldUserName,String userName, String userPassword, Boolean userStatus, int userPermission){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(USER_NAME, userName);
            contentValues.put(USER_PASSWORD, userPassword);
            contentValues.put(USER_STATUS, userStatus);
            contentValues.put(USER_PERMISSION_ID, userPermission);

            int i = db.update(USER_TABLE, contentValues, USER_NAME + "=" + '"'+oldUserName+'"',null);
            return i > 0;
        }

        //Felhasználó módosítása jelszó nélkül
        public Boolean userModifyWithoutPassword(String oldUserName, String userName, Boolean userStatus, int userPermission){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(USER_NAME, userName);
            contentValues.put(USER_STATUS, userStatus);
            contentValues.put(USER_PERMISSION_ID, userPermission);

            int i = db.update(USER_TABLE, contentValues, USER_NAME + "=" + '"'+oldUserName+'"',null);
            return i > 0;
        }

        //Felhasználónév létezésének ellenőrzése -- darabszám
        public Integer userNameCheckForModify(String username){
            int userCount=0;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor eredmeny = db.rawQuery("SELECT COUNT("+USER_NAME+") AS eredmeny FROM " + USER_TABLE + " WHERE felhNeve='" + username +"'", null);
            if (eredmeny!=null && eredmeny.getCount()>0) {
                eredmeny.moveToFirst();
                userCount = eredmeny.getInt(eredmeny.getColumnIndex("eredmeny"));
            }
            return userCount;
        }

    /*
    * POZÍCIÓVAL KAPCSOLATOS ADATBÁZIS PARANCSOK
    * */

        //Pozíciók feltöltése listába
        public ArrayList<String> positionList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> positionList = new ArrayList<>();

            String query = "SELECT "+ POSITION_NAME + " AS posName" +
                    " FROM " + POSITION_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                positionList.add(cursor.getString(cursor.getColumnIndex("posName")));
            }
            return positionList;
        }

        public Boolean positionCheck(String positionName){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + POSITION_TABLE + " WHERE beosztasNeve=?", new String[]{positionName});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Pozició tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewPositions(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> positionList = new ArrayList<>();

            String query = "SELECT u."+ POSITION_NAME + ", g."+ GRADE_NAME + ", g."+ SALARY_MIN_VALUE +" , g."+ SALARY_MAX_VALUE +
                    " FROM " + POSITION_TABLE + " AS u" +
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON u." + POSITION_GRADE_ID + " = g."+ GRADE_ID +
                    " ORDER BY " + GRADE_NAME;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> position = new HashMap<>();
                position.put("POSITION_NAME",cursor.getString(cursor.getColumnIndex(POSITION_NAME)));
                position.put("GRADE_NAME",cursor.getString(cursor.getColumnIndex(GRADE_NAME)));
                position.put("SALARY_MIN_VALUE",cursor.getString(cursor.getColumnIndex(SALARY_MIN_VALUE)));
                position.put("SALARY_MAX_VALUE",cursor.getString(cursor.getColumnIndex(SALARY_MAX_VALUE)));

                positionList.add(position);
            }
            return positionList;
        }

        //Beosztás módosítása
        public Boolean positionModify(String oldPositioName, String newPositioName, int gradeId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(POSITION_NAME, newPositioName);
            contentValues.put(POSITION_GRADE_ID, gradeId);

            int i = db.update(POSITION_TABLE, contentValues, POSITION_NAME + "=" + '"'+oldPositioName+'"',null);
            return i > 0;
        }


        //Pozició törlése
        public Boolean positionDelete(String position){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(POSITION_TABLE,POSITION_NAME + "="+'"'+position+'"',null) > 0;
        }

     /*
     * Osztállyal kapcsolatos adatbázis utasítások
     * */

        //Osztályok feltöltése listába
        public ArrayList<String> departmentList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> departmentList = new ArrayList<>();

            String query = "SELECT "+ DEPARTMENT_NAME +
                    " FROM " + DEPARTMENT_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                departmentList.add(cursor.getString(cursor.getColumnIndex("osztalyNeve")));
            }
            return departmentList;
        }

        //Department tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewDepartments(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> departmentList = new ArrayList<>();

            String query = "SELECT "+ DEPARTMENT_NAME +
                    " FROM " + DEPARTMENT_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> department = new HashMap<>();
                department.put("DEPARTMENT_NAME",cursor.getString(cursor.getColumnIndex(DEPARTMENT_NAME)));
                departmentList.add(department);
            }
            return departmentList;
        }

        //Osztály ellenőrzése
        public Boolean departmentCheck(String department){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DEPARTMENT_TABLE + " WHERE osztalyNeve=?", new String[]{department});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Osztály módosítása
        public Boolean departmentModify(String oldDepartmentName, String newDepartmentName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DEPARTMENT_NAME, newDepartmentName);

            int i = db.update(DEPARTMENT_TABLE, contentValues, DEPARTMENT_NAME + "=" + '"'+oldDepartmentName+'"',null);
            return i > 0;
        }

        //Osztály törlése
        public Boolean departmentDelete(String department){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(DEPARTMENT_TABLE,DEPARTMENT_NAME + "="+'"'+department+'"',null) > 0;
        }



    /*
    * Autóval kapcsolatos adatbázis utasítások
    * */


        //Autó gyártmányok feltöltése listába
        public ArrayList<String> modelOfCarList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> modelOfCarList = new ArrayList<>();

            String query = "SELECT ("+ MODELOFCAR_BRAND + " || " + "' '" + " || " + MODELOFCAR_TYPE + ") AS carType" +
                    " FROM " + MODELOFCAR_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                modelOfCarList.add(cursor.getString(cursor.getColumnIndex("carType")));
            }
            return modelOfCarList;
        }

        //Autó létezésének ellenőzése
        public Boolean carCheck(String licenseeNumber, String vinNumber){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CAR_TABLE + " WHERE autoRendszam=? AND autoAlvazszam=?", new String[]{licenseeNumber,vinNumber});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Autó tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewCars(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> carList = new ArrayList<>();

            String query = "SELECT c."+ CAR_LICENSENUMBER + ", c."+ CAR_VINNUMBER + ", date(c."+ CAR_MOTDATE +") AS "+MOTDATE+", (m."+ MODELOFCAR_BRAND + " || " + "' '" + " || m." + MODELOFCAR_TYPE+ ") AS " + CARTYPE +
                    " FROM " + CAR_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFCAR_TABLE + " AS m ON m." + MODELOFCAR_ID + " = c." + CAR_MODEL_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> car = new HashMap<>();
                car.put("CAR_LICENSENUMBER",cursor.getString(cursor.getColumnIndex(CAR_LICENSENUMBER)));
                car.put("CAR_VINNUMBER",cursor.getString(cursor.getColumnIndex(CAR_VINNUMBER)));
                car.put("MOTDATE",cursor.getString(cursor.getColumnIndex(MOTDATE)));
                car.put("CARTYPE",cursor.getString(cursor.getColumnIndex(CARTYPE)));

                carList.add(car);
            }
            return carList;
        }

        //Autó módosítása
        public Boolean carModify(String oldLicenseeNumber, String newLicenseeNumber,String vinNumber, String motDate, int carModelId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(CAR_LICENSENUMBER, newLicenseeNumber);
            contentValues.put(CAR_VINNUMBER, vinNumber);
            contentValues.put(CAR_MOTDATE, motDate);
            contentValues.put(CAR_MODEL_ID, carModelId);

            int i = db.update(CAR_TABLE, contentValues, CAR_LICENSENUMBER + "=" + '"'+oldLicenseeNumber+'"',null);
            return i > 0;
        }


        //Autó törlése
        public Boolean carDelete(String licenseeNumber){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(CAR_TABLE,CAR_LICENSENUMBER + "="+'"'+licenseeNumber+'"',null) > 0;
        }


     /*
     * Autó gyártmánnyal kapcsolatos lekérdezések
     * */

        //Grade-ek feltöltése listába
        public ArrayList<String> gradeList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> gradeNameList = new ArrayList<>();

            String query = "SELECT "+ GRADE_NAME +" AS gradeName"+
                    " FROM " + GRADE_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                gradeNameList.add(cursor.getString(cursor.getColumnIndex("gradeName")));
            }
            return gradeNameList;
        }

        //Autó gyártmány létezésének ellenőzése
        public Boolean carBrandCheck(String brand, String type){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + MODELOFCAR_TABLE + " WHERE autoMarka=? AND autoTipus=?", new String[]{brand,type});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Autó gyártmány tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewCarBrands(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> carBrandList = new ArrayList<>();

        String query = "SELECT m."+ MODELOFCAR_BRAND + ", m."+ MODELOFCAR_TYPE + ", g."+ GRADE_NAME +
                " FROM " + MODELOFCAR_TABLE + " AS m" +
                " LEFT JOIN " + GRADE_TABLE + " AS g ON g." + GRADE_ID + " = m." + MODELOFCAR_GRADE_ID;

        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){
            HashMap<String,String> brand = new HashMap<>();
            brand.put("MODELOFCAR_BRAND",cursor.getString(cursor.getColumnIndex(MODELOFCAR_BRAND)));
            brand.put("MODELOFCAR_TYPE",cursor.getString(cursor.getColumnIndex(MODELOFCAR_TYPE)));
            brand.put("GRADE_NAME",cursor.getString(cursor.getColumnIndex(GRADE_NAME)));

            carBrandList.add(brand);
        }
        return carBrandList;
    }

        //Autó gyártmány módosítása
        public Boolean modifyCarBrand(String oldCarBrand, String oldCarType, String carBrand,String carType, int gradeId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(MODELOFCAR_BRAND, carBrand);
            contentValues.put(MODELOFCAR_TYPE, carType);
            contentValues.put(MODELOFCAR_GRADE_ID, gradeId);

            int i = db.update(MODELOFCAR_TABLE, contentValues, MODELOFCAR_BRAND + "=" + '"'+oldCarBrand+'"' +" AND "+ MODELOFCAR_TYPE + "=" + '"'+oldCarType+'"',null);
            return i > 0;
        }

        //Autó gyártmány törlése
        public Boolean carBrandDelete(String brand, String type){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(MODELOFCAR_TABLE,MODELOFCAR_BRAND + "="+'"'+brand+'"' +" AND "+ MODELOFCAR_TYPE + "="+'"'+type+'"',null) > 0;
        }


    /*
     * Mobilokkal kapcsolatos adatbázis utasítások
     * */


        //Mobil gyártmányok feltöltése listába
        public ArrayList<String> modelOfMobileList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> modelOfMobileList = new ArrayList<>();

            String query = "SELECT ("+ MODELOFMOBIL_BRAND + " || " + "' '" + " || " + MODELOFMOBIL_TYPE + ") AS mobilType" +
                    " FROM " + MODELOFMOBIL_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                modelOfMobileList.add(cursor.getString(cursor.getColumnIndex("mobilType")));
            }
            return modelOfMobileList;
        }

        //Mobil létezésének ellenőzése
        public Boolean mobileCheck(String imeiNumber){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + MOBILE_TABLE + " WHERE mobilImeiSzam=?", new String[]{imeiNumber});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Mobil tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewMobiles(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> mobileBrandList = new ArrayList<>();

            String query = "SELECT m."+ MOBIL_IMEINUMBER + ", (g."+ MODELOFMOBIL_BRAND + " || " + "' '" + " || g." + MODELOFMOBIL_TYPE+ ") AS " + MOBILTYPE +
                    " FROM " + MOBILE_TABLE + " AS m" +
                    " LEFT JOIN " + MODELOFMOBIL_TABLE + " AS g ON g." + MODELOFMOBIL_ID + " = m." + MOBIL_MODEL_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> mobile = new HashMap<>();
                mobile.put("MOBIL_IMEINUMBER",cursor.getString(cursor.getColumnIndex(MOBIL_IMEINUMBER)));
                mobile.put("MOBILTYPE",cursor.getString(cursor.getColumnIndex(MOBILTYPE)));

                mobileBrandList.add(mobile);
            }
            return mobileBrandList;
        }

        //Mobil módosítása
        public Boolean modifyMobile(String oldMobileImei,String mobileImeiNumber, int mobileBrandId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(MOBIL_IMEINUMBER, mobileImeiNumber);
            contentValues.put(MOBIL_MODEL_ID, mobileBrandId);

            int i = db.update(MOBILE_TABLE, contentValues, MOBIL_IMEINUMBER + "=" + '"'+oldMobileImei+'"',null);
            return i > 0;
        }

        //Mobil törlése
        public Boolean mobileDelete(String mobileImeiNumber){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(MOBILE_TABLE,MOBIL_IMEINUMBER + "="+'"'+mobileImeiNumber+'"',null) > 0;
        }



    /*
     * Mobil gyártmányokkal kapcsolatos adatbázis utasítások
     * */



        //Mobil gyártmány létezésének ellenőzése
        public Boolean mobileBrandCheck(String brand, String type){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + MODELOFMOBIL_TABLE + " WHERE mobilMarka=? AND mobilTipus=?", new String[]{brand,type});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Mobil gyártmány tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewMobileBrands(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> mobileBrandList = new ArrayList<>();

            String query = "SELECT m."+ MODELOFMOBIL_BRAND + ", m."+ MODELOFMOBIL_TYPE + ", g."+ GRADE_NAME +
                    " FROM " + MODELOFMOBIL_TABLE + " AS m" +
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON g." + GRADE_ID + " = m." + MODELOFMOBIL_GRADE_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> brand = new HashMap<>();
                brand.put("MODELOFMOBIL_BRAND",cursor.getString(cursor.getColumnIndex(MODELOFMOBIL_BRAND)));
                brand.put("MODELOFMOBIL_TYPE",cursor.getString(cursor.getColumnIndex(MODELOFMOBIL_TYPE)));
                brand.put("GRADE_NAME",cursor.getString(cursor.getColumnIndex(GRADE_NAME)));

                mobileBrandList.add(brand);
            }
            return mobileBrandList;
        }

        //Mobil gyártmány módosítása
        public Boolean modifyMobileBrand(String oldMobileBrand, String oldMobileType, String mobileBrand,String mobileType, int gradeId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(MODELOFMOBIL_BRAND, mobileBrand);
            contentValues.put(MODELOFMOBIL_TYPE, mobileType);
            contentValues.put(MODELOFMOBIL_GRADE_ID, gradeId);

            int i = db.update(MODELOFMOBIL_TABLE, contentValues, MODELOFMOBIL_BRAND + "=" + '"'+oldMobileBrand+'"' +" AND "+ MODELOFMOBIL_TYPE + "=" + '"'+oldMobileType+'"',null);
            return i > 0;
        }

        //Mobil gyártmány törlése
        public Boolean mobileBrandDelete(String brand, String type){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(MODELOFMOBIL_TABLE,MODELOFMOBIL_BRAND + "="+'"'+brand+'"' +" AND "+ MODELOFMOBIL_TYPE + "="+'"'+type+'"',null) > 0;
        }



    /*
     * Laptop gyártmányokkal kapcsolatos adatbázis utasítások
     * */




        //Laptop gyártmány létezésének ellenőzése
        public Boolean laptopBrandCheck(String brand, String type){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + MODELOFLAPTOP_TABLE + " WHERE laptopMarka=? AND laptopTipus=?", new String[]{brand,type});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Laptop gyártmány tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewLaptopBrands(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> laptopBrandList = new ArrayList<>();

            String query = "SELECT m."+ MODELOFLAPTOP_BRAND + ", m."+ MODELOFLAPTOP_TYPE + ", g."+ GRADE_NAME +
                    " FROM " + MODELOFLAPTOP_TABLE + " AS m" +
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON g." + GRADE_ID + " = m." + MODELOFLAPTOP_GRADE_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> brand = new HashMap<>();
                brand.put("MODELOFLAPTOP_BRAND",cursor.getString(cursor.getColumnIndex(MODELOFLAPTOP_BRAND)));
                brand.put("MODELOFLAPTOP_TYPE",cursor.getString(cursor.getColumnIndex(MODELOFLAPTOP_TYPE)));
                brand.put("GRADE_NAME",cursor.getString(cursor.getColumnIndex(GRADE_NAME)));

                laptopBrandList.add(brand);
            }
            return laptopBrandList;
        }

        //Laptop gyártmány módosítása
        public Boolean modifyLaptopBrand(String oldLaptopBrand, String oldLaptopType, String laptopBrand,String laptopType, int gradeId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(MODELOFLAPTOP_BRAND, laptopBrand);
            contentValues.put(MODELOFLAPTOP_TYPE, laptopType);
            contentValues.put(MODELOFLAPTOP_GRADE_ID, gradeId);

            int i = db.update(MODELOFLAPTOP_TABLE, contentValues, MODELOFLAPTOP_BRAND + "=" + '"'+oldLaptopBrand+'"' +" AND "+ MODELOFLAPTOP_TYPE + "=" + '"'+oldLaptopType+'"',null);
            return i > 0;
        }

        //Laptop gyártmány törlése
        public Boolean laptopBrandDelete(String brand, String type){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(MODELOFLAPTOP_TABLE,MODELOFLAPTOP_BRAND + "="+'"'+brand+'"' +" AND "+ MODELOFLAPTOP_TYPE + "="+'"'+type+'"',null) > 0;
        }


        /*
         * Laptopokkal kapcsolatos adatbázis utasítások
         * */

        //Laptop gyártmányok feltöltése listába
        public ArrayList<String> modelOfLaptopList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> modelOfLaptopList = new ArrayList<>();

            String query = "SELECT ("+ MODELOFLAPTOP_BRAND + " || " + "' '" + " || " + MODELOFLAPTOP_TYPE + ") AS laptopType" +
                    " FROM " + MODELOFLAPTOP_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                modelOfLaptopList.add(cursor.getString(cursor.getColumnIndex("laptopType")));
            }
            return modelOfLaptopList;
        }

        //Laptop létezésének ellenőzése
        public Boolean laptopCheck(String imeiNumber){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + LAPTOP_TABLE + " WHERE laptopImeiSzam=?", new String[]{imeiNumber});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Laptop tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewLaptops(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> laptopBrandList = new ArrayList<>();

            String query = "SELECT m."+ LAPTOP_IMEINUMBER + ", (l."+ MODELOFLAPTOP_BRAND + " || " + "' '" + " || l." + MODELOFLAPTOP_TYPE+ ") AS " + LAPTOPTYPE +
                    " FROM " + LAPTOP_TABLE + " AS m" +
                    " LEFT JOIN " + MODELOFLAPTOP_TABLE + " AS l ON l." + MODELOFLAPTOP_ID + " = m." + LAPTOP_MODEL_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> mobile = new HashMap<>();
                mobile.put("LAPTOP_IMEINUMBER",cursor.getString(cursor.getColumnIndex(LAPTOP_IMEINUMBER)));
                mobile.put("LAPTOPTYPE",cursor.getString(cursor.getColumnIndex(LAPTOPTYPE)));

                laptopBrandList.add(mobile);
            }
            return laptopBrandList;
        }

        //Laptop módosítása
        public Boolean modifyLaptop(String oldLaptopImei,String laptopImeiNumber, int laptopBrandId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(LAPTOP_IMEINUMBER, laptopImeiNumber);
            contentValues.put(LAPTOP_MODEL_ID, laptopBrandId);

            int i = db.update(LAPTOP_TABLE, contentValues, LAPTOP_IMEINUMBER + "=" + '"'+oldLaptopImei+'"',null);
            return i > 0;
        }

        //Laptop törlése
        public Boolean laptopDelete(String laptopImeiNumber){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(LAPTOP_TABLE,LAPTOP_IMEINUMBER + "="+'"'+laptopImeiNumber+'"',null) > 0;
        }


    /*
     * Dolgozókkal kapcsolatos adatbázis utasítások
     * */



        //Dolgozó létezésének ellenőzése
        public Boolean employeeCheck(String empName, String empMoName){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE + " WHERE dolgNev=? AND dolgAnyjaNeve=?", new String[]{empName,empMoName});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Dolgozó tábla feltöltése listába
        public ArrayList<HashMap<String,String>> viewEmployees(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> employeeList = new ArrayList<>();

            String query = "SELECT e."+ EMPLOYEE_ID +", e."+ EMPLOYEE_NAME +", e." + EMPLOYEE_GENDER +", e." + EMPLOYEE_BIRTH +", e." + EMPLOYEE_MOTHERS_NAME +", e." + EMPLOYEE_STATUS +", e." + EMPLOYEE_SALARY +
                    ", d." + DEPARTMENT_NAME +", p." + POSITION_NAME +
                    " FROM " + EMPLOYEE_TABLE + " AS e" +
                    " LEFT JOIN " + DEPARTMENT_TABLE + " AS d ON e." + EMPLOYEE_DEPARTMANET_ID + " = d." + DEPARTMENT_ID +
                    " LEFT JOIN " + POSITION_TABLE + " AS p ON e." + EMPLOYEE_POSITION_ID + " = p." + POSITION_ID;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> employee = new HashMap<>();
                employee.put("EMPLOYEE_ID",cursor.getString(cursor.getColumnIndex(EMPLOYEE_ID)));
                employee.put("EMPLOYEE_NAME",cursor.getString(cursor.getColumnIndex(EMPLOYEE_NAME)));

                switch (cursor.getInt(cursor.getColumnIndex(EMPLOYEE_GENDER))){
                    case 0:
                        employee.put("EMPLOYEE_GENDER","Nő");
                        break;

                    case 1:
                        employee.put("EMPLOYEE_GENDER","Férfi");
                        break;
                    default: employee.put("EMPLOYEE_GENDER","Nincs adat");
                        break;
                }

                employee.put("EMPLOYEE_BIRTH",cursor.getString(cursor.getColumnIndex(EMPLOYEE_BIRTH)));
                employee.put("EMPLOYEE_MOTHERS_NAME",cursor.getString(cursor.getColumnIndex(EMPLOYEE_MOTHERS_NAME)));

                switch (cursor.getInt(cursor.getColumnIndex(EMPLOYEE_STATUS))){
                    case 0:
                        employee.put("EMPLOYEE_STATUS","Inaktív");
                        break;

                    case 1:
                        employee.put("EMPLOYEE_STATUS","Aktív");
                        break;
                    default: employee.put("EMPLOYEE_GENDER","Nincs adat");
                        break;
                }

                employee.put("EMPLOYEE_SALARY",cursor.getString(cursor.getColumnIndex(EMPLOYEE_SALARY)));
                employee.put("DEPARTMENT_NAME",cursor.getString(cursor.getColumnIndex(DEPARTMENT_NAME)));
                employee.put("POSITION_NAME",cursor.getString(cursor.getColumnIndex(POSITION_NAME)));

                employeeList.add(employee);
            }
            return employeeList;
        }

        //Dolgozó módosítása
        public Boolean modifyEmployee(int employeeID,String empName,boolean empGender,String empBirth, String empMoName,boolean empStatus,String empSalary,
                                    int empDep, int empPos){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(EMPLOYEE_NAME, empName);
            contentValues.put(EMPLOYEE_GENDER, empGender);
            contentValues.put(EMPLOYEE_BIRTH, empBirth);
            contentValues.put(EMPLOYEE_MOTHERS_NAME, empMoName);
            contentValues.put(EMPLOYEE_STATUS, empStatus);
            contentValues.put(EMPLOYEE_SALARY, empSalary);
            contentValues.put(EMPLOYEE_DEPARTMANET_ID, empDep);
            contentValues.put(EMPLOYEE_POSITION_ID, empPos);

            int i = db.update(EMPLOYEE_TABLE, contentValues, EMPLOYEE_ID + "=" + '"'+employeeID+'"',null);
            return i > 0;
        }

        //Dolgozó törlése
        public Boolean employeeDelete(int empId){
            SQLiteDatabase db = this.getWritableDatabase();
            return  db.delete(EMPLOYEE_TABLE,EMPLOYEE_ID + "="+'"'+empId+'"',null) > 0;
        }

        //Dolgozó név kikeresése
        public String empName(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            String empName = "";
            Cursor cursor = db.rawQuery("SELECT e." + EMPLOYEE_NAME + " AS empName" +
                                            " FROM " + USER_TABLE + " AS u" +
                                            " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_USER_ID + " = u." + USER_ID +
                                            " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex("empName"));
            }
            return empName;
        }

        //Osztály kikeresése
        public String empDep(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            String depName = "";
            Cursor cursor = db.rawQuery("SELECT d." + DEPARTMENT_NAME + " AS depName" +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_USER_ID + " = u." + USER_ID +
                    " LEFT JOIN " + DEPARTMENT_TABLE + " AS d ON d." + DEPARTMENT_ID + " = e." + EMPLOYEE_DEPARTMANET_ID +
                    " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                depName = cursor.getString(cursor.getColumnIndex("depName"));
            }
            return depName;
        }

        //Beosztás kikeresése
        public String empPos(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            String posName = "";
            Cursor cursor = db.rawQuery("SELECT p." + POSITION_NAME + " AS posName" +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_USER_ID + " = u." + USER_ID +
                    " LEFT JOIN " + POSITION_TABLE + " AS p ON p." + POSITION_ID + " = e." + EMPLOYEE_POSITION_ID +
                    " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                posName = cursor.getString(cursor.getColumnIndex("posName"));
            }
            return posName;
        }

    /*
    * Juttatásokkal kapcsolatos adatbázis utasítások
    * */

        //Autó juttatás feltöltése listába
        public ArrayList<HashMap<String,String>> viewActiveCarBenefit(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> carBenefitList = new ArrayList<>();

            String query = "SELECT b."+ BENEFIT_ID +", e."+ EMPLOYEE_NAME +", (m."+ MODELOFCAR_BRAND + " || " + "' '" + " || m." + MODELOFCAR_TYPE+ ") AS " + CARTYPE +
                    ", c."+ CAR_LICENSENUMBER + ", b." + BENEFIT_STATUS + ", u." + USER_NAME +
                    " FROM " + BENEFIT_TABLE + " AS b" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_ID + " = b." + BENEFIT_EMPLOYEE_ID +
                    " LEFT JOIN " + CAR_TABLE + " AS c ON c." + CAR_ID + " = b." + BENEFIT_ITEM_ID +
                    " LEFT JOIN " + MODELOFCAR_TABLE + " AS m ON m." + MODELOFCAR_ID + " = c." + CAR_MODEL_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = b." + BENEFIT_USER_ID +
                    " WHERE " + BENEFIT_STATUS + "= 1 AND " + BENEFIT_ITEM + " = 'a'";

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> carBenefit = new HashMap<>();
                carBenefit.put("BENEFIT_ID",cursor.getString(cursor.getColumnIndex(BENEFIT_ID)));
                carBenefit.put("EMPLOYEE_NAME",cursor.getString(cursor.getColumnIndex(EMPLOYEE_NAME)));
                carBenefit.put("CARTYPE",cursor.getString(cursor.getColumnIndex(CARTYPE)));
                carBenefit.put("CAR_LICENSENUMBER",cursor.getString(cursor.getColumnIndex(CAR_LICENSENUMBER)));


                switch (cursor.getInt(cursor.getColumnIndex(BENEFIT_STATUS))){
                    case 0:
                        carBenefit.put("BENEFIT_STATUS","Inaktív");
                        break;

                    case 1:
                        carBenefit.put("BENEFIT_STATUS","Aktív");
                        break;
                    default: carBenefit.put("BENEFIT_STATUS","Nincs adat");
                        break;
                }

                carBenefit.put("USER_NAME",cursor.getString(cursor.getColumnIndex(USER_NAME)));

                carBenefitList.add(carBenefit);
            }
            return carBenefitList;
        }


        //Autó gyártmányok feltöltése listába
        public ArrayList<String> carList(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> modelOfCarList = new ArrayList<>();

            String query = "SELECT (m."+ MODELOFCAR_BRAND + " || " + "' '" + " || m." + MODELOFCAR_TYPE + " || " + "' '" + " || c." + CAR_LICENSENUMBER + ") AS CAR" +
                    " FROM " + CAR_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFCAR_TABLE + " AS m ON m." + MODELOFCAR_ID + " = c." + CAR_MODEL_ID +
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON g." + GRADE_ID + " = m." + MODELOFCAR_GRADE_ID +
                    " LEFT JOIN " + POSITION_TABLE + " AS p ON p." + POSITION_GRADE_ID + " = g." + GRADE_ID +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_POSITION_ID + " = p." + POSITION_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE "+ USER_NAME +"=?" +
                    " GROUP BY CAR";

            Cursor cursor = db.rawQuery(query,new String[]{userName});

            while(cursor.moveToNext()){
                modelOfCarList.add(cursor.getString(cursor.getColumnIndex("CAR")));
            }
            return modelOfCarList;
        }

        //Autó juttatás ellenőzése
        public Boolean carBenefitCheck(int itemId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                                        " FROM " + BENEFIT_TABLE +
                                        " WHERE eszkoz='a' AND eszkoz_id=? AND status=1", new String[]{String.valueOf(itemId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Juttatás módosítása
        public Boolean updateBenefit(int benefitId,int itemId,boolean status, String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(BENEFIT_ITEM_ID, itemId);
            contentValues.put(BENEFIT_STATUS, status);
            contentValues.put(BENEFIT_FAC_USER, userName);

            int i = db.update(BENEFIT_TABLE, contentValues, BENEFIT_ID + "=" + '"'+benefitId+'"',null);
            return i > 0;
        }

        //Userek feltöltése listába
        public ArrayList<String> employeeUserList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> employeeUserList = new ArrayList<>();

            String query = "SELECT u."+ USER_NAME + " AS emp" +
                    " FROM " + EMPLOYEE_TABLE + " AS e" +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE e."+ EMPLOYEE_USER_ID +" IS NOT NULL";

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                employeeUserList.add(cursor.getString(cursor.getColumnIndex("emp")));
            }
            return employeeUserList;
        }

        //Dolgozó kikeresése
        public String employeeNameSearch(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            String empName = "";
            Cursor cursor = db.rawQuery("SELECT e." + EMPLOYEE_NAME + " AS empName" +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_USER_ID + " = u." + USER_ID +
                    " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex("empName"));
            }else empName = "Dolgozó neve";
            return empName;
        }

        //Dolgozó ID kikeresése
        public String empIdSearch(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            String empId = "";
            Cursor cursor = db.rawQuery("SELECT e." + EMPLOYEE_ID + " AS empId" +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_USER_ID + " = u." + USER_ID +
                    " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                empId = cursor.getString(cursor.getColumnIndex("empId"));
            }else empId = "0";
            return empId;
        }

        //Felhasználó ID kikeresése
        public String userIdSearch(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            String userId = "";
            Cursor cursor = db.rawQuery("SELECT " + USER_ID + " AS userId" +
                    " FROM " + USER_TABLE +
                    " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                userId = cursor.getString(cursor.getColumnIndex("userId"));
            }else userId = "0";
            return userId;
        }

        //Autó rendszám feltöltése listába
        public ArrayList<String> carLicList(String brand, String type){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> licOfCarList = new ArrayList<>();

            String query = "SELECT c."+CAR_LICENSENUMBER+" AS licenseeNumber" +
                    " FROM " + CAR_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFCAR_TABLE + " AS m ON m." + MODELOFCAR_ID + " = c." + CAR_MODEL_ID +
                    " WHERE " + MODELOFCAR_BRAND + "=? AND " + MODELOFCAR_TYPE + " =?";

            Cursor cursor = db.rawQuery(query,new String[] {brand, type});

            while(cursor.moveToNext()){
                licOfCarList.add(cursor.getString(cursor.getColumnIndex("licenseeNumber")));
            }
            return licOfCarList;
        }


        //Autó ID kikeresése
        public Integer carIdSearch(String licNumber){
            SQLiteDatabase db = this.getReadableDatabase();
            int id = 0;
            Cursor cursor = db.rawQuery("SELECT " + CAR_ID + " AS carId" +
                    " FROM " + CAR_TABLE +
                    " WHERE autoRendszam=?", new String[]{licNumber});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndex("carId"));
            }
            return id;
        }

        //Autó juttatás ellenőzése
        public Boolean empCarBenefitCheck(int empId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='a' AND dolgozo_id=? AND status=1", new String[]{String.valueOf(empId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Autó juttatás ellenőzése
        public Boolean empCarBenefitCheckForInactive(int empId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='a' AND dolgozo_id=? AND status=0", new String[]{String.valueOf(empId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Autó juttatás módosítása
        public Boolean updateCarBenefitForInactive(int itemId,boolean status, String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(BENEFIT_ITEM_ID, itemId);
            contentValues.put(BENEFIT_STATUS, status);
            contentValues.put(BENEFIT_FAC_USER, userName);

            int i = db.update(BENEFIT_TABLE, contentValues, BENEFIT_ITEM + "=" +"'"+ "a"+"'",null);
            return i > 0;
        }


        //Mobil juttatás feltöltése listába
        public ArrayList<HashMap<String,String>> viewActiveMobileBenefit(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> mobileBenefitList = new ArrayList<>();

            String query = "SELECT b."+ BENEFIT_ID +", e."+ EMPLOYEE_NAME +", (m."+ MODELOFMOBIL_BRAND + " || " + "' '" + " || m." + MODELOFMOBIL_TYPE + ") AS " + MOBILTYPE +
                    ", c."+ MOBIL_IMEINUMBER + ", b." + BENEFIT_STATUS +", u." + USER_NAME +
                    " FROM " + BENEFIT_TABLE + " AS b" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_ID + " = b." + BENEFIT_EMPLOYEE_ID +
                    " LEFT JOIN " + MOBILE_TABLE + " AS c ON c." + MOBIL_ID + " = b." + BENEFIT_ITEM_ID +
                    " LEFT JOIN " + MODELOFMOBIL_TABLE + " AS m ON m." + MODELOFMOBIL_ID + " = c." + MOBIL_MODEL_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = b." + BENEFIT_USER_ID +
                    " WHERE " + BENEFIT_STATUS + "= 1 AND " + BENEFIT_ITEM + " = 'm'";


            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> mobileBenefit = new HashMap<>();
                mobileBenefit.put("BENEFIT_ID",cursor.getString(cursor.getColumnIndex(BENEFIT_ID)));
                mobileBenefit.put("EMPLOYEE_NAME",cursor.getString(cursor.getColumnIndex(EMPLOYEE_NAME)));
                mobileBenefit.put("MOBILTYPE",cursor.getString(cursor.getColumnIndex(MOBILTYPE)));
                mobileBenefit.put("MOBIL_IMEINUMBER",cursor.getString(cursor.getColumnIndex(MOBIL_IMEINUMBER)));


                switch (cursor.getInt(cursor.getColumnIndex(BENEFIT_STATUS))){
                    case 0:
                        mobileBenefit.put("BENEFIT_STATUS","Inaktív");
                        break;

                    case 1:
                        mobileBenefit.put("BENEFIT_STATUS","Aktív");
                        break;
                    default: mobileBenefit.put("BENEFIT_STATUS","Nincs adat");
                        break;
                }

                mobileBenefit.put("USER_NAME",cursor.getString(cursor.getColumnIndex(USER_NAME)));

                mobileBenefitList.add(mobileBenefit);
            }
            return mobileBenefitList;
        }

        //Mobil gyártmányok feltöltése listába
        public ArrayList<String> mobileList(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> modelOfMobileList = new ArrayList<>();

            String query = "SELECT (m."+ MODELOFMOBIL_BRAND + " || " + "' '" + " || m." + MODELOFMOBIL_TYPE + " || " + "' '" + " || c." + MOBIL_IMEINUMBER + ") AS mobil" +
                    " FROM " + MOBILE_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFMOBIL_TABLE + " AS m ON m." + MODELOFMOBIL_ID + " = c." + MOBIL_MODEL_ID +
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON g." + GRADE_ID + " = m." + MODELOFMOBIL_GRADE_ID +
                    " LEFT JOIN " + POSITION_TABLE + " AS p ON p." + POSITION_GRADE_ID + " = g." + GRADE_ID +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_POSITION_ID + " = p." + POSITION_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE "+ USER_NAME +"=?" +
                    " GROUP BY mobil";


            Cursor cursor = db.rawQuery(query,new String[]{userName});

            while(cursor.moveToNext()){
                modelOfMobileList.add(cursor.getString(cursor.getColumnIndex("mobil")));
            }
            return modelOfMobileList;
        }

        //Mobil juttatás ellenőzése
        public Boolean mobileBenefitCheck(int itemId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='m' AND eszkoz_id=? AND status=1", new String[]{String.valueOf(itemId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Mobil márka feltöltése listába
        public ArrayList<String> mobileBrandList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> brandOfMobilList = new ArrayList<>();

            String query = "SELECT "+MODELOFMOBIL_BRAND+" AS brand" +
                    " FROM " + MODELOFMOBIL_TABLE +
                    " GROUP BY " + MODELOFMOBIL_BRAND;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                brandOfMobilList.add(cursor.getString(cursor.getColumnIndex("brand")));
            }
            return brandOfMobilList;
        }

        //Mobil típus feltöltése listába
        public ArrayList<String> mobileTypeList(String brand){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> typeOfMobileList = new ArrayList<>();

            String query = "SELECT "+MODELOFMOBIL_TYPE+" AS type" +
                    " FROM " + MODELOFMOBIL_TABLE +
                    " WHERE " + MODELOFMOBIL_BRAND + "=?" +
                    " GROUP BY " + MODELOFMOBIL_TYPE;

            Cursor cursor = db.rawQuery(query,new String[]{brand});

            while(cursor.moveToNext()){
                typeOfMobileList.add(cursor.getString(cursor.getColumnIndex("type")));
            }
            return typeOfMobileList;
        }

        //Mobil IMEI feltöltése listába
        public ArrayList<String> mobileImeiList(String brand, String type){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> imeiOfMobileList = new ArrayList<>();

            String query = "SELECT c."+MOBIL_IMEINUMBER+" AS imeiNumber" +
                    " FROM " + MOBILE_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFMOBIL_TABLE + " AS m ON m." + MODELOFMOBIL_ID + " = c." + MOBIL_MODEL_ID +
                    " WHERE " + MODELOFMOBIL_BRAND + "=? AND " + MODELOFMOBIL_TYPE + " =?";

            Cursor cursor = db.rawQuery(query,new String[] {brand, type});

            while(cursor.moveToNext()){
                imeiOfMobileList.add(cursor.getString(cursor.getColumnIndex("imeiNumber")));
            }
            return imeiOfMobileList;
        }

        //Mobil ID kikeresése
        public Integer mobileIdSearch(String imeiNum){
            SQLiteDatabase db = this.getReadableDatabase();
            int id = 0;
            Cursor cursor = db.rawQuery("SELECT " + MOBIL_ID + " AS mobilId" +
                    " FROM " + MOBILE_TABLE +
                    " WHERE mobilImeiSzam=?", new String[]{imeiNum});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndex("mobilId"));
            }
            return id;
        }

        //Mobil juttatás ellenőzése
        public Boolean empMobileBenefitCheck(int empId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='m' AND dolgozo_id=? AND status=1", new String[]{String.valueOf(empId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Mobil juttatás ellenőzése
        public Boolean empMobileBenefitCheckForInactive(int empId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='m' AND dolgozo_id=? AND status=0", new String[]{String.valueOf(empId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Mobil juttatás módosítása
        public Boolean updateMobileBenefitForInactive(int itemId,boolean status, String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(BENEFIT_ITEM_ID, itemId);
            contentValues.put(BENEFIT_STATUS, status);
            contentValues.put(BENEFIT_FAC_USER, userName);

            int i = db.update(BENEFIT_TABLE, contentValues, BENEFIT_ITEM + "=" +"'"+"m"+"'",null);
            return i > 0;
        }

        //Laptop juttatás feltöltése listába
        public ArrayList<HashMap<String,String>> viewActiveLaptopBenefit(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> laptopBenefitList = new ArrayList<>();

            String query = "SELECT b."+ BENEFIT_ID +", e."+ EMPLOYEE_NAME +", (m."+ MODELOFLAPTOP_BRAND + " || " + "' '" + " || m." + MODELOFLAPTOP_TYPE + ") AS " + LAPTOPTYPE +
                    ", c."+ LAPTOP_IMEINUMBER + ", b." + BENEFIT_STATUS + ", u." + USER_NAME +
                    " FROM " + BENEFIT_TABLE + " AS b" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_ID + " = b." + BENEFIT_EMPLOYEE_ID +
                    " LEFT JOIN " + LAPTOP_TABLE + " AS c ON c." + LAPTOP_ID + " = b." + BENEFIT_ITEM_ID +
                    " LEFT JOIN " + MODELOFLAPTOP_TABLE + " AS m ON m." + MODELOFLAPTOP_ID + " = c." + LAPTOP_MODEL_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = b." + BENEFIT_USER_ID +
                    " WHERE " + BENEFIT_STATUS + "= 1 AND " + BENEFIT_ITEM + " = 'l'";


            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> laptopBenefit = new HashMap<>();
                laptopBenefit.put("BENEFIT_ID",cursor.getString(cursor.getColumnIndex(BENEFIT_ID)));
                laptopBenefit.put("EMPLOYEE_NAME",cursor.getString(cursor.getColumnIndex(EMPLOYEE_NAME)));
                laptopBenefit.put("LAPTOPTYPE",cursor.getString(cursor.getColumnIndex(LAPTOPTYPE)));
                laptopBenefit.put("LAPTOP_IMEINUMBER",cursor.getString(cursor.getColumnIndex(LAPTOP_IMEINUMBER)));

                switch (cursor.getInt(cursor.getColumnIndex(BENEFIT_STATUS))){
                    case 0:
                        laptopBenefit.put("BENEFIT_STATUS","Inaktív");
                        break;

                    case 1:
                        laptopBenefit.put("BENEFIT_STATUS","Aktív");
                        break;
                    default: laptopBenefit.put("BENEFIT_STATUS","Nincs adat");
                        break;
                }

                laptopBenefit.put("USER_NAME",cursor.getString(cursor.getColumnIndex(USER_NAME)));

                laptopBenefitList.add(laptopBenefit);
            }
            return laptopBenefitList;
        }

        //Laptop gyártmányok feltöltése listába
        public ArrayList<String> laptopList(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> modelOfLaptopList = new ArrayList<>();

            String query = "SELECT (m."+ MODELOFLAPTOP_BRAND + " || " + "' '" + " || m." + MODELOFLAPTOP_TYPE + " || " + "' '" + " || c." + LAPTOP_IMEINUMBER + ") AS laptop" +
                    " FROM " + LAPTOP_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFLAPTOP_TABLE + " AS m ON m." + MODELOFLAPTOP_ID + " = c." + LAPTOP_MODEL_ID +
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON g." + GRADE_ID + " = m." + MODELOFLAPTOP_GRADE_ID +
                    " LEFT JOIN " + POSITION_TABLE + " AS p ON p." + POSITION_GRADE_ID + " = g." + GRADE_ID +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_POSITION_ID + " = p." + POSITION_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE "+ USER_NAME +"=?" +
                    " GROUP BY laptop";

            Cursor cursor = db.rawQuery(query,new String[]{userName});

            while(cursor.moveToNext()){
                modelOfLaptopList.add(cursor.getString(cursor.getColumnIndex("laptop")));
            }
            return modelOfLaptopList;
        }

        //Laptop juttatás ellenőzése
        public Boolean laptopBenefitCheck(int itemId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='l' AND eszkoz_id=? AND status=1", new String[]{String.valueOf(itemId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Laptop márka feltöltése listába
        public ArrayList<String> laptopBrandList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> brandOfMobilList = new ArrayList<>();

            String query = "SELECT "+MODELOFLAPTOP_BRAND+" AS brand" +
                    " FROM " + MODELOFLAPTOP_TABLE +
                    " GROUP BY " + MODELOFLAPTOP_BRAND;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                brandOfMobilList.add(cursor.getString(cursor.getColumnIndex("brand")));
            }
            return brandOfMobilList;
        }

        //Laptop típus feltöltése listába
        public ArrayList<String> laptopTypeList(String brand){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> typeOfLaptopList = new ArrayList<>();

            String query = "SELECT "+MODELOFLAPTOP_TYPE+" AS type" +
                    " FROM " + MODELOFLAPTOP_TABLE +
                    " WHERE " + MODELOFLAPTOP_BRAND + "=?" +
                    " GROUP BY " + MODELOFLAPTOP_TYPE;

            Cursor cursor = db.rawQuery(query,new String[]{brand});

            while(cursor.moveToNext()){
                typeOfLaptopList.add(cursor.getString(cursor.getColumnIndex("type")));
            }
            return typeOfLaptopList;
        }

        //Laptop IMEI feltöltése listába
        public ArrayList<String> laptopImeiList(String brand, String type){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> imeiOfLaptopList = new ArrayList<>();

            String query = "SELECT c."+LAPTOP_IMEINUMBER+" AS imeiNumber" +
                    " FROM " + LAPTOP_TABLE + " AS c" +
                    " LEFT JOIN " + MODELOFLAPTOP_TABLE + " AS m ON m." + MODELOFLAPTOP_ID + " = c." + LAPTOP_MODEL_ID +
                    " WHERE " + MODELOFLAPTOP_BRAND + "=? AND " + MODELOFLAPTOP_TYPE + " =?";

            Cursor cursor = db.rawQuery(query,new String[] {brand, type});

            while(cursor.moveToNext()){
                imeiOfLaptopList.add(cursor.getString(cursor.getColumnIndex("imeiNumber")));
            }
            return imeiOfLaptopList;
        }

        //Laptop ID kikeresése
        public Integer laptopIdSearch(String imeiNum){
            SQLiteDatabase db = this.getReadableDatabase();
            int id = 0;
            Cursor cursor = db.rawQuery("SELECT " + LAPTOP_ID + " AS laptopId" +
                    " FROM " + LAPTOP_TABLE +
                    " WHERE laptopImeiSzam=?", new String[]{imeiNum});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndex("laptopId"));
            }
            return id;
        }

        //Laptop juttatás ellenőzése
        public Boolean empLaptopBenefitCheck(int empId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='l' AND dolgozo_id=? AND status=1", new String[]{String.valueOf(empId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Laptop juttatás ellenőzése
        public Boolean empLaptopBenefitCheckForInactive(int empId){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz='l' AND dolgozo_id=? AND status=0", new String[]{String.valueOf(empId)});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        //Laptop juttatás módosítása
        public Boolean updateLaptopBenefitForInactive(int itemId,boolean status, String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(BENEFIT_ITEM_ID, itemId);
            contentValues.put(BENEFIT_STATUS, status);
            contentValues.put(BENEFIT_FAC_USER, userName);

            int i = db.update(BENEFIT_TABLE, contentValues, BENEFIT_ITEM + "=" +"'"+"l"+"'",null);
            return i > 0;
        }


    /*
     * QR kód kezelést segítő adatbázis utasítások
     * */

        //Márka feltöltése listába
        public ArrayList<String> BrandHelper(String query){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> brandList = new ArrayList<>();

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                brandList.add(cursor.getString(cursor.getColumnIndex("brand")));
            }
            return brandList;
        }

        //Típus feltöltése listába
        public ArrayList<String> TypeHelper(String query){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> typeList = new ArrayList<>();

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                typeList.add(cursor.getString(cursor.getColumnIndex("type")));
            }
            return typeList;
        }

        //Típus feltöltése listába
        public ArrayList<String> IdentityHelper(String query){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> indentityList = new ArrayList<>();

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                indentityList.add(cursor.getString(cursor.getColumnIndex("identity")));
            }
            return indentityList;
        }

        public Boolean itemCheck(String query){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

    /*
    * Employee Menu - adatbázis utasítások
    * */


        //Autó juttatás ellenőzése
        public Boolean itemBenefitCheckByUserName(String item, String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * " +
                    " FROM " + BENEFIT_TABLE +
                    " WHERE eszkoz="+"'"+item+"'"+" AND dolgozo_id=" +
                    "(SELECT "+ EMPLOYEE_ID +
                    " FROM "+ EMPLOYEE_TABLE + " AS e" +
                    " LEFT JOIN "+ USER_TABLE +" AS u ON u."+ USER_ID +" = e."+ EMPLOYEE_USER_ID +
                    " WHERE "+ USER_NAME +"=?) " +
                    "AND status=1", new String[]{userName});

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }

        public ArrayList<String> queryFillList(String query, String column){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> list = new ArrayList<>();
            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex(column)));
            }
            return list;
        }


        //Autó juttatás feltöltése listába user szerint
        public ArrayList<String> viewCarBenefitByUser(String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> carBenefitList = new ArrayList<>();

            String query = "SELECT (m."+ MODELOFCAR_BRAND + " || " + "' '" + " || m." + MODELOFCAR_TYPE+ ") AS " + CARTYPE +", c."+ CAR_LICENSENUMBER +
                    ", c." + CAR_VINNUMBER + ", c." + CAR_MOTDATE +
                    " FROM " + BENEFIT_TABLE + " AS b" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_ID + " = b." + BENEFIT_EMPLOYEE_ID +
                    " LEFT JOIN " + CAR_TABLE + " AS c ON c." + CAR_ID + " = b." + BENEFIT_ITEM_ID +
                    " LEFT JOIN " + MODELOFCAR_TABLE + " AS m ON m." + MODELOFCAR_ID + " = c." + CAR_MODEL_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE " + USER_NAME + "=?";

            Cursor cursor = db.rawQuery(query,new String[]{userName});

            while(cursor.moveToNext()){
                carBenefitList.add(cursor.getString(cursor.getColumnIndex(CARTYPE)));
                carBenefitList.add(cursor.getString(cursor.getColumnIndex(CAR_LICENSENUMBER)));
                carBenefitList.add(cursor.getString(cursor.getColumnIndex(CAR_VINNUMBER)));
                carBenefitList.add(cursor.getString(cursor.getColumnIndex(CAR_MOTDATE)));
            }
            return carBenefitList;
        }

        //Dolgozó ID kikeresése
        public Integer empIdByUserName(String userName){
            SQLiteDatabase db = this.getReadableDatabase();
            int empId;
            Cursor cursor = db.rawQuery("SELECT e." + EMPLOYEE_ID + " AS empId" +
                    " FROM " + USER_TABLE + " AS u" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_USER_ID + " = u." + USER_ID +
                    " WHERE felhNeve=?", new String[]{userName});

            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                empId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("empId")));
                return empId;
            }else return null;
        }

        public ArrayList<String> tripDetails(String query, String col1, String col2, String col3, String col4){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> list = new ArrayList<>();
            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex(col1)));
                list.add(cursor.getString(cursor.getColumnIndex(col2)));
                list.add(cursor.getString(cursor.getColumnIndex(col3)));
                list.add(cursor.getString(cursor.getColumnIndex(col4)));
            }
            return list;
        }

        //Autó juttatás feltöltése listába user szerint
        public ArrayList<String> viewMobileBenefitByUser(String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> mobileBenefitList = new ArrayList<>();

            String query = "SELECT (m."+ MODELOFMOBIL_BRAND + " || " + "' '" + " || m." + MODELOFMOBIL_TYPE+ ") AS " + MOBILTYPE +", c."+ MOBIL_IMEINUMBER +
                    " FROM " + BENEFIT_TABLE + " AS b" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_ID + " = b." + BENEFIT_EMPLOYEE_ID +
                    " LEFT JOIN " + MOBILE_TABLE + " AS c ON c." + MOBIL_ID + " = b." + BENEFIT_ITEM_ID +
                    " LEFT JOIN " + MODELOFMOBIL_TABLE + " AS m ON m." + MODELOFMOBIL_ID + " = c." + MOBIL_MODEL_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE " + USER_NAME + "=?";

            Cursor cursor = db.rawQuery(query,new String[]{userName});

            while(cursor.moveToNext()){
                mobileBenefitList.add(cursor.getString(cursor.getColumnIndex(MOBILTYPE)));
                mobileBenefitList.add(cursor.getString(cursor.getColumnIndex(MOBIL_IMEINUMBER)));
            }
            return mobileBenefitList;
        }

        //Autó juttatás feltöltése listába user szerint
        public ArrayList<String> viewLaptopBenefitByUser(String userName){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> laptopBenefitList = new ArrayList<>();

            String query = "SELECT (m."+ MODELOFLAPTOP_BRAND + " || " + "' '" + " || m." + MODELOFLAPTOP_TYPE+ ") AS " + LAPTOPTYPE +", c."+ LAPTOP_IMEINUMBER +
                    " FROM " + BENEFIT_TABLE + " AS b" +
                    " LEFT JOIN " + EMPLOYEE_TABLE + " AS e ON e." + EMPLOYEE_ID + " = b." + BENEFIT_EMPLOYEE_ID +
                    " LEFT JOIN " + LAPTOP_TABLE + " AS c ON c." + LAPTOP_ID + " = b." + BENEFIT_ITEM_ID +
                    " LEFT JOIN " + MODELOFLAPTOP_TABLE + " AS m ON m." + MODELOFLAPTOP_ID + " = c." + LAPTOP_MODEL_ID +
                    " LEFT JOIN " + USER_TABLE + " AS u ON u." + USER_ID + " = e." + EMPLOYEE_USER_ID +
                    " WHERE " + USER_NAME + "=?";

            Cursor cursor = db.rawQuery(query,new String[]{userName});

            while(cursor.moveToNext()){
                laptopBenefitList.add(cursor.getString(cursor.getColumnIndex(LAPTOPTYPE)));
                laptopBenefitList.add(cursor.getString(cursor.getColumnIndex(LAPTOP_IMEINUMBER)));
            }
            return laptopBenefitList;
        }


            //Juttatások feltöltése listába
        public ArrayList<HashMap<String,String>> viewBenefits(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> benefitList = new ArrayList<>();

            String query = "SELECT " +
                    "CASE j.eszkoz " +
                    "   WHEN 'a' " +
                    "   THEN (SELECT g.autoMarka || \" \" || g.autoTipus || \" \" || a.autoRendszam " +
                    "           FROM autok as a " +
                    "           LEFT JOIN auto_gyartmanyok as g on g.autoGyartmany_id = a.autoGyartmany_id " +
                    "           LEFT JOIN felhasznalok as f on f.felh_id = user_id " +
                    "           WHERE a.auto_id = eszkoz_id)" +
                    "   WHEN 'm' " +
                    "   THEN (SELECT g.mobilMarka || \" \" || g.mobilTipus || \" \" || a.mobilImeiSzam " +
                    "           FROM mobilok as a " +
                    "           LEFT JOIN mobil_gyartmanyok as g on g.mobilGyartmany_id = a.mobilGyartmany_id " +
                    "           WHERE a.mobil_id = eszkoz_id)" +
                    "   WHEN 'l' " +
                    "       THEN (SELECT g.laptopMarka || \" \" || g.laptopTipus || \" \" || a.LaptopImeiSzam " +
                    "           FROM laptopok as a " +
                    "           LEFT JOIN laptop_gyartmanyok as g on g.laptopGyartmany_id = a.laptopGyartmany_id " +
                    "           WHERE a.laptop_id = eszkoz_id)" +
                    "END tipus, d.dolgNev, " +
                    "CASE status " +
                    "   WHEN 1 THEN 'Kiadva' " +
                    "   ELSE 'Átvéve' " +
                    "END status, datetime, fac_user_name " +
                    "FROM juttatas_log as j " +
                    "LEFT JOIN felhasznalok as f on f.felh_id = j.user_id " +
                    "LEFT JOIN dolgozok as d on d.felh_id = f.felh_id";


            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> benefit = new HashMap<>();
                benefit.put("tipus",cursor.getString(cursor.getColumnIndex("tipus")));
                benefit.put("dolgNev",cursor.getString(cursor.getColumnIndex("dolgNev")));
                benefit.put("status",cursor.getString(cursor.getColumnIndex("status")));
                benefit.put("datetime",cursor.getString(cursor.getColumnIndex("datetime")));
                benefit.put("fac_user_name",cursor.getString(cursor.getColumnIndex("fac_user_name")));

                benefitList.add(benefit);
            }
            return benefitList;
        }


        //Utak feltöltése listába
        public ArrayList<HashMap<String,String>> viewTrips(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<HashMap<String,String>> tripList = new ArrayList<>();

            String query = "SELECT d.dolgNev, u.kmIndulas , u.gpsIndulas, u.kmErkezes, u.gpsErkezes, u.rogzitesIdo" +
                    " FROM utak AS u" +
                    " LEFT JOIN dolgozok AS d ON d.dolg_id = u.dolgozo_id";


            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                HashMap<String,String> trip = new HashMap<>();
                trip.put("dolgNev",cursor.getString(cursor.getColumnIndex("dolgNev")));
                trip.put("kmIndulas",cursor.getString(cursor.getColumnIndex("kmIndulas")));
                trip.put("gpsIndulas",cursor.getString(cursor.getColumnIndex("gpsIndulas")));
                trip.put("kmErkezes",cursor.getString(cursor.getColumnIndex("kmErkezes")));
                trip.put("gpsErkezes",cursor.getString(cursor.getColumnIndex("gpsErkezes")));
                trip.put("rogzitesIdo",cursor.getString(cursor.getColumnIndex("rogzitesIdo")));

                tripList.add(trip);
            }
            return tripList;
        }

        //Dolgozónév felhasználó nélküli lista
        public ArrayList<String> empNameList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> empNameList = new ArrayList<>();
            String query = "SELECT dolgNev " +
                             "FROM dolgozok " +
                                "WHERE felh_id IS NULL";
            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                empNameList.add(cursor.getString(cursor.getColumnIndex("dolgNev")));
            }
            return empNameList;
        }

        //Dolgozónév felhasználóval lista
        public ArrayList<String> empNameListWithUser(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> empNameList = new ArrayList<>();
            String query = "SELECT dolgNev " +
                    "FROM dolgozok " +
                    "WHERE felh_id IS NOT NULL";
            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                empNameList.add(cursor.getString(cursor.getColumnIndex("dolgNev")));
            }
            return empNameList;
        }

        //Felhasználónév lista
        public ArrayList<String> userNameList(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> empNameList = new ArrayList<>();
            String query = "SELECT felhNeve " +
                    "FROM felhasznalok f " +
                    "LEFT JOIN dolgozok d ON f.felh_id = d.felh_id " +
                    "WHERE " +
                    "   f.felh_id IS NOT NULL " +
                    "   AND d.felh_id IS NULL " +
                    "   AND felhNeve NOT LIKE '%admin%' " +
                    "   AND felhNeve NOT LIKE '%fac%' " +
                    "   AND felhNeve NOT LIKE '%hr%'";

            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                empNameList.add(cursor.getString(cursor.getColumnIndex("felhNeve")));
            }
            return empNameList;
        }

        //Felhasználó törlése dolgozói profilból
        public Boolean deleteUserFromEmp(String empName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(EMPLOYEE_USER_ID, (byte[]) null);

            int i = db.update(EMPLOYEE_TABLE, contentValues, EMPLOYEE_NAME + "=" +"'"+empName+"'",null);
            return i > 0;
        }

        //Felhasználó hozzáadása dolgozói profilhoz
        public Boolean addUserToEmp(String empName, int userId){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(EMPLOYEE_USER_ID, userId);

            int i = db.update(EMPLOYEE_TABLE, contentValues, EMPLOYEE_NAME + "=" +"'"+empName+"'",null);
            return i > 0;
        }

}
