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
                + EMPLOYEE_GENDER + " integer not null, "
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
    public boolean insertEmployee(String dolgNev, int dolgNeme, String dolgSzuletesiDatum, String dolgAnyjaNeve,
                                  boolean dolgStatusz, int dolgFizetes, int osztaly_id, int beosztas_id){
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
        Cursor cursor = db.rawQuery("SELECT * FROM " + LAPTOP_TABLE + " WHERE laptopMarka=? AND laptopTipus=?", new String[]{brand,type});

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
            brand.put("MODELOFMOBIL_BRAND",cursor.getString(cursor.getColumnIndex(MODELOFMOBIL_BRAND)));
            brand.put("MODELOFMOBIL_TYPE",cursor.getString(cursor.getColumnIndex(MODELOFMOBIL_TYPE)));
            brand.put("GRADE_NAME",cursor.getString(cursor.getColumnIndex(GRADE_NAME)));

            laptopBrandList.add(brand);
        }
        return laptopBrandList;
    }

    //Laptop gyártmány módosítása
    public Boolean modifyLaptopBrand(String oldLaptopBrand, String oldLaptopType, String laptopBrand,String laptopType, int gradeId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MODELOFMOBIL_BRAND, laptopBrand);
        contentValues.put(MODELOFMOBIL_TYPE, laptopType);
        contentValues.put(MODELOFMOBIL_GRADE_ID, gradeId);

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



    //Laptop gyártmány létezésének ellenőzése
    public Boolean employeeCheck(String empName, String empMoName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EMPLOYEE_TABLE + " WHERE dolgNev=? AND dolgAnyjaNeve=?", new String[]{empName,empMoName});

        if(cursor.getCount()>0){
            return true;
        }else return false;
    }


}
