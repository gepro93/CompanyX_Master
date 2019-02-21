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
    public static final String CAR_GRADE_ID = "grade_id";

    //autó gyártámányok tábla és oszlopok definiálása
    public static final String MODELOFCAR_TABLE = "auto_gyartmanyok";

    public static final String MODELOFCAR_ID = "autoGyartmany_id";
    public static final String MODELOFCAR_BRAND = "autoMarka";
    public static final String MODELOFCAR_TYPE = "autoTipus";


    //mobilok tábla és oszlopok definiálása
    public static final String MOBILE_TABLE = "mobilok";

    public static final String MOBIL_ID = "mobil_id";
    public static final String MOBIL_IMEINUMBER = "mobilImeiSzam";
    public static final String MOBIL_MODEL_ID = "mobilGyartmany_id";
    public static final String MOBIL_GRADE_ID = "grade_id";

    //mobil gyártámányok tábla és oszlopok definiálása
    public static final String MODELOFMOBIL_TABLE = "mobil_gyartmanyok";

    public static final String MODELOFMOBIL_ID = "mobilGyartmany_id";
    public static final String MODELOFMOBIL_BRAND = "mobilMarka";
    public static final String MODELOFMOBIL_TYPE = "mobilTipus";

    //laptopok tábla és oszlopok definiálása
    public static final String LAPTOP_TABLE = "laptopok";

    public static final String LAPTOP_ID = "laptop_id";
    public static final String LAPTOP_IMEINUMBER = "laptopImeiSzam";
    public static final String LAPTOP_MODEL_ID = "laptopGyartmany_id";
    public static final String LAPTOP_GRADE_ID = "grade_id";

    //laptop gyártámányok tábla és oszlopok definiálása
    public static final String MODELOFLAPTOP_TABLE = "laptop_gyartmanyok";

    public static final String MODELOFLAPTOP_ID = "laptopGyartmany_id";
    public static final String MODELOFLAPTOP_BRAND = "laptopMarka";
    public static final String MODELOFLAPTOP_TYPE = "laptopTipus";

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

    //tranzakciók tábla és oszlopok definiálása
    public static final String TRANSACTION_TABLE = "tranzakciok";

    public static final String TRANSACTION_ID = "tranzakcio_id";
    public static final String TRANSACTION_TYPE = "tranzakcioTipus";
    public static final String TRANSACTION_ITEM_NAME = "eszkozNeve";
    public static final String TRANSACTION_ITEM_ID = "eszkoz_id";
    public static final String TRANSACTION_EMPLOYEE_ID = "dolgozo_id";



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
                + EMPLOYEE_GENDER + " char not null, "
                + EMPLOYEE_BIRTH + " date not null, "
                + EMPLOYEE_MOTHERS_NAME + " text not null, "
                + EMPLOYEE_STATUS + " bool not null, "
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
                + CAR_GRADE_ID + " integer not null,"
                + " FOREIGN KEY ("+CAR_MODEL_ID+") REFERENCES "+MODELOFCAR_TABLE+"("+MODELOFCAR_ID+"),"
                + " FOREIGN KEY ("+CAR_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MODELOFCAR_TABLE + " ("
                + MODELOFCAR_ID + " integer primary key autoincrement, "
                + MODELOFCAR_BRAND + " text not null,"
                + MODELOFCAR_TYPE + " text not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MOBILE_TABLE + " ("
                + MOBIL_ID + " integer primary key autoincrement, "
                + MOBIL_IMEINUMBER + " text not null, "
                + MOBIL_MODEL_ID + " integer not null, "
                + MOBIL_GRADE_ID + " integer not null,"
                + " FOREIGN KEY ("+MOBIL_MODEL_ID+") REFERENCES "+MODELOFMOBIL_TABLE+"("+MODELOFMOBIL_ID+"),"
                + " FOREIGN KEY ("+MOBIL_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MODELOFMOBIL_TABLE + " ("
                + MODELOFMOBIL_ID + " integer primary key autoincrement, "
                + MODELOFMOBIL_BRAND + " text not null,"
                + MODELOFMOBIL_TYPE + " text not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + LAPTOP_TABLE + " ("
                + LAPTOP_ID + " integer primary key autoincrement, "
                + LAPTOP_IMEINUMBER + " text not null, "
                + LAPTOP_MODEL_ID + " integer not null, "
                + LAPTOP_GRADE_ID + " integer not null,"
                + " FOREIGN KEY ("+LAPTOP_MODEL_ID+") REFERENCES "+MODELOFLAPTOP_TABLE+"("+MODELOFLAPTOP_ID+"),"
                + " FOREIGN KEY ("+LAPTOP_GRADE_ID+") REFERENCES "+GRADE_TABLE+"("+GRADE_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + MODELOFLAPTOP_TABLE + " ("
                + MODELOFLAPTOP_ID + " integer primary key autoincrement, "
                + MODELOFLAPTOP_BRAND + " text not null,"
                + MODELOFLAPTOP_TYPE + " text not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + GRADE_TABLE + " ("
                + GRADE_ID + " integer primary key autoincrement, "
                + GRADE_NAME + " text not null, "
                + SALARY_MIN_VALUE + " integer not null,"
                + SALARY_MAX_VALUE + " integer not null)");

        sqLiteDatabase.execSQL("CREATE TABLE "
                + TRIP_TABLE + " ("
                + TRIP_ID + " integer primary key autoincrement, "
                + TRIP_GPS_START + " text,"
                + TRIP_GPS_ARRIVAL + " text,"
                + TRIP_KM_START + " integer,"
                + TRIP_KM_ARRIVAL + " integer,"
                + TRIP_TIMESTAMP + " date,"
                + TRIP_EMPLOYEE_ID + " integer,"
                + TRIP_CAR_ID + " integer,"
                + " FOREIGN KEY ("+TRIP_EMPLOYEE_ID+") REFERENCES "+EMPLOYEE_TABLE+"("+EMPLOYEE_ID+"),"
                + " FOREIGN KEY ("+TRIP_CAR_ID+") REFERENCES "+CAR_TABLE+"("+CAR_ID+"))");

        /*sqLiteDatabase.execSQL("CREATE TABLE "
                + TRANSACTION_TABLE + " ("
                + TRANSACTION_ID + " integer primary key autoincrement, "
                + TRANSACTION_TYPE + " bool not null, "
                + TRANSACTION_ITEM_NAME + " char not null, "
                + TRANSACTION_ITEM_ID + " integer not null, "
                + TRANSACTION_EMPLOYEE_ID + " integer not null,"
                + " FOREIGN KEY ("+TRANSACTION_ITEM_ID+") REFERENCES "+item_table+"("+item_id+"),"
                + " FOREIGN KEY ("+TRANSACTION_EMPLOYEE_ID+") REFERENCES "+EMPLOYEE_TABLE+"("+EMPLOYEE_ID+"))");*/

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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
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
    public boolean insertEmployee(String dolgNev, String dolgNeme, Date dolgSzuletesiDatum, String dolgAnyjaNeve,
                                  int dolgStatusz, Boolean dolgFizetes, int osztaly_id, int beosztas_id, int felh_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_NAME, dolgNev);
        contentValues.put(EMPLOYEE_GENDER, dolgNeme);
        contentValues.put(EMPLOYEE_BIRTH, String.valueOf(dolgSzuletesiDatum));
        contentValues.put(EMPLOYEE_MOTHERS_NAME, dolgAnyjaNeve);
        contentValues.put(EMPLOYEE_STATUS, dolgStatusz);
        contentValues.put(EMPLOYEE_SALARY, dolgFizetes);
        contentValues.put(EMPLOYEE_DEPARTMANET_ID, osztaly_id);
        contentValues.put(EMPLOYEE_POSITION_ID, beosztas_id);
        contentValues.put(EMPLOYEE_USER_ID, felh_id);

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
    public boolean insertCar(String autoRendszam, String autoAlvazszam, Date autoMuszakierveny, String autoGyartmany_id, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAR_LICENSENUMBER, autoRendszam);
        contentValues.put(CAR_VINNUMBER, autoAlvazszam);
        contentValues.put(CAR_MOTDATE, String.valueOf(autoMuszakierveny));
        contentValues.put(CAR_MODEL_ID, autoGyartmany_id);
        contentValues.put(CAR_GRADE_ID, grade_id);

        long eredmeny = db.insert(CAR_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Autó gyártmány felvétel
    public boolean insertModelOfCar(String autoMarka, String autoTipus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODELOFCAR_BRAND, autoMarka);
        contentValues.put(MODELOFCAR_TYPE, autoTipus);

        long eredmeny = db.insert(MODELOFCAR_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Mobil felvétel
    public boolean insertMobile(String mobilImeiSzam, int mobilGyartmany_id, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOBIL_IMEINUMBER, mobilImeiSzam);
        contentValues.put(MOBIL_MODEL_ID, mobilGyartmany_id);
        contentValues.put(MOBIL_GRADE_ID, grade_id);

        long eredmeny = db.insert(MOBILE_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Mobil gyártmány felvétel
    public boolean insertModelOfMobile(String mobilMarka, String mobilTipus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODELOFMOBIL_BRAND, mobilMarka);
        contentValues.put(MODELOFMOBIL_TYPE, mobilTipus);

        long eredmeny = db.insert(MODELOFMOBIL_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Laptop felvétel
    public boolean insertLaptop(String laptopImeiSzam, int laptopGyartmany_id, int grade_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LAPTOP_IMEINUMBER, laptopImeiSzam);
        contentValues.put(LAPTOP_MODEL_ID, laptopGyartmany_id);
        contentValues.put(LAPTOP_GRADE_ID, grade_id);

        long eredmeny = db.insert(LAPTOP_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Laptop gyártmány felvétel
    public boolean insertModelOfLaptop(String laptopMarka, String laptopTipus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MODELOFLAPTOP_BRAND, laptopMarka);
        contentValues.put(MODELOFLAPTOP_TYPE, laptopTipus);

        long eredmeny = db.insert(MODELOFLAPTOP_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Grade felvétel
    public boolean insertGrade(int fizetesAlsoErtek, int fizetesFelsoErtek){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SALARY_MIN_VALUE, fizetesAlsoErtek);
        contentValues.put(SALARY_MAX_VALUE, fizetesFelsoErtek);

        long eredmeny = db.insert(GRADE_TABLE,  null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Út felvétel
    public boolean insertTrip(String gpsIndulas, String gpsErkezes, int kmIndulas, int kmErkezes,
                                  Date rogzitesIdo, int dolgozo_id, int auto_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIP_GPS_START, gpsIndulas);
        contentValues.put(TRIP_GPS_ARRIVAL, gpsErkezes);
        contentValues.put(TRIP_KM_START, kmIndulas);
        contentValues.put(TRIP_KM_ARRIVAL, kmErkezes);
        contentValues.put(TRIP_TIMESTAMP, String.valueOf(rogzitesIdo));
        contentValues.put(TRIP_EMPLOYEE_ID, dolgozo_id);
        contentValues.put(TRIP_CAR_ID, auto_id);

        long eredmeny = db.insert(TRIP_TABLE, null, contentValues);

        if(eredmeny == -1){
            return false;
        }else{
            return true;
        }
    }

    //Tranzakció felvétel
    public boolean insertTransaction(Boolean tranzakcioTipus, String eszkozNeve,
                                     int eszkoz_id, int dolgozo_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTION_TYPE, tranzakcioTipus);
        contentValues.put(TRANSACTION_ITEM_NAME, eszkozNeve);
        contentValues.put(TRANSACTION_ITEM_ID, eszkoz_id);
        contentValues.put(TRANSACTION_EMPLOYEE_ID, dolgozo_id);

        long eredmeny = db.insert(TRIP_TABLE, null, contentValues);

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

            /*if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                status = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("felhStatusz")));
            }
            return status;*/
        }

        /*public Cursor viewUsers(){
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT u."+ USER_NAME + ", p."+ PERMISSION_NAME +", u." + USER_STATUS +
                            " FROM " + USER_TABLE + " AS u" +
                            " LEFT JOIN " + PERMISSION_TABLE + " AS p ON u." + USER_PERMISSION_ID + " = p."+ PERMISSION_ID;
            Cursor cursor = db.rawQuery(query,null);

            return cursor;
        }*/


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

                user.put("USER_STATUS", cursor.getString(cursor.getColumnIndex(USER_STATUS)));
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

        //Admin létrehozása
        protected boolean insertAdmin(){
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                String adminUserName = "admin";
                String adminPassword = "admin1234";
                Boolean adminStatus = true;
                int adminPermission = 1;

                contentValues.put(USER_NAME, adminUserName);
                contentValues.put(USER_PASSWORD, adminPassword);
                contentValues.put(USER_STATUS, adminStatus);
                contentValues.put(USER_PERMISSION_ID, adminPermission);

                long eredmeny = db.insert(USER_TABLE, null, contentValues);

                if(eredmeny == -1){
                    return false;
                }else{
                    return true;
                }
        }


    /*
    * JOGOSULTSÁGGAL KAPCSOLATOS ADATBÁZIS PARANCSOK
    * */

        //Jogosultságok létrehozása
        protected boolean createPermissions(){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentAdmin = new ContentValues();
            ContentValues contentHr = new ContentValues();
            ContentValues contentFacilities = new ContentValues();
            ContentValues contentEmployee = new ContentValues();
            ContentValues contentSuperUser = new ContentValues();

            String adminPerm = "Admin";
            String hrPerm = "HR";
            String facilitiesPerm = "Beszerzés";
            String employeePerm = "Dolgozó";
            String superUser = "Super User";

            contentAdmin.put(PERMISSION_NAME, adminPerm);
            contentHr.put(PERMISSION_NAME, hrPerm);
            contentFacilities.put(PERMISSION_NAME, facilitiesPerm);
            contentEmployee.put(PERMISSION_NAME, employeePerm);
            contentSuperUser.put(PERMISSION_NAME, superUser);

            long resultAdmin = db.insert(PERMISSION_TABLE, null, contentAdmin);
            long resultHr = db.insert(PERMISSION_TABLE, null, contentHr);
            long resultFacilities = db.insert(PERMISSION_TABLE, null, contentFacilities);
            long resultEmployee = db.insert(PERMISSION_TABLE, null, contentEmployee);
            long resultSuperUser = db.insert(PERMISSION_TABLE, null, contentSuperUser);

            if(resultAdmin == -1 || resultHr == -1 || resultFacilities == -1 || resultEmployee == -1 || resultSuperUser == -1){
                return false;
            }else{
                return true;
            }
        }

        //Jogosultságok ellenőrzése
        public Boolean permissionCheck(){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + PERMISSION_TABLE + " WHERE jogosultsag_id=1 AND jogosultsag_id=2 AND jogosultsag_id=3 AND jogosultsag_id=4 AND jogosultsag_id=5", null);

            if(cursor.getCount()>0){
                return true;
            }else return false;
        }


    /*
    * POZÍCIÓVAL KAPCSOLATOS ADATBÁZIS PARANCSOK
    * */

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
                    " LEFT JOIN " + GRADE_TABLE + " AS g ON u." + POSITION_GRADE_ID + " = g."+ GRADE_ID;

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

        //Poziciók feltöltése listába
        public ArrayList<String> viewPositionsByName(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<String> positionList = new ArrayList<>();

            String query = "SELECT "+ POSITION_NAME + " AS beosztasNeve" +
                    " FROM " + POSITION_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                positionList.add(cursor.getString(cursor.getColumnIndex("beosztasNeve")));
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

        //Poziciók feltöltése listába
        public ArrayList<Integer> viewPositionsByGrade(){
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<Integer> gradeList = new ArrayList<>();

            String query = "SELECT "+ POSITION_GRADE_ID +
                    " FROM " + POSITION_TABLE;

            Cursor cursor = db.rawQuery(query,null);

            while(cursor.moveToNext()){
                gradeList.add(cursor.getInt(cursor.getColumnIndex("gradeNeve")));
            }
            return gradeList;
        }

        //Grade létrehozása
        protected boolean insertGrade(){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            String gradeName = "Manager";
            int salaryMin = 500;
            int salaryMax = 1000;

            contentValues.put(GRADE_NAME, gradeName);
            contentValues.put(SALARY_MIN_VALUE, salaryMin);
            contentValues.put(SALARY_MAX_VALUE, salaryMax);


            long eredmeny = db.insert(GRADE_TABLE, null, contentValues);

            if(eredmeny == -1){
                return false;
            }else{
                return true;
            }
        }

}
