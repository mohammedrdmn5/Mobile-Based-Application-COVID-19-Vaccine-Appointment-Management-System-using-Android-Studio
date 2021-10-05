package com.example.groupassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class DatabaseManager extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "MyVaccine.db";
    private static final int DATABASE_VERSION = 1;
    /////// all the tables
    private static final String TBL_User = "User";
    private static final String TBL_Vaccine = "Vaccine";
    private static final String TBL_Appointment = "Appointment";
    private static final String TBL_UserVaccineRecord = "UserVaccineRecord";
    private static final String TBL_Hospital = "Hospital";
    private static final String TBL_FAQ = "FAQ";
    //private static final String COLUMN_ID="ID";


    //////////////columns for user table
    private static final String User_id = "id";
    private static final String User_Fname = "Fname";
    private static final String User_Lname = "Lname";
    private static final String User_Username = "Username";
    private static final String User_Password = "Password";
    private static final String User_IsTakenVaccine = "IsTakenVaccine";
    private static final String User_IsHascovid = "IsHascovid";
    private static final String User_Address = "Address";
    private static final String User_dob = "dob";
    private static final String User_IsAdmin = "IsAdmin";
    private static final String User_IsDoneQuiz = "IsDoneQuiz";
    private static final String User_Img = "Img";
    private static final String User_Phone = "Phone";
    private static final String User_Email = "Email";
    private static final String User_Gender = "Gender";
    private static final String User_PostCode = "PostCode";
    private static final String User_IsIntrestTakenVaccine = "IsIntrestTakenVaccine";
    private static final String User_Passport = "Passport";

    private static final String User_Comorbidites = "Comorbidites";


    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

// create USER TABLE
        String TBL_CREATE_USER = "CREATE TABLE " + TBL_User +
                "(" + User_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                User_Username + " TEXT UNIQUE," +
                User_Fname + " TEXT, " +
                User_Lname + " TEXT, " +
                User_Password + " TEXT, " +
                User_IsTakenVaccine + " INTEGER, " +
                User_IsHascovid + " INTEGER, " +
                User_Address + " TEXT, " +
                User_dob + " TEXT, " +
                User_IsAdmin + " INTEGER, " +
                User_IsDoneQuiz + " INTEGER, " +
                User_Img + " TEXT, " +
                User_Phone + " TEXT, " +
                User_Email + " TEXT, " +
                User_Gender + " TEXT, " +
                User_PostCode + " INT, " +
                User_IsIntrestTakenVaccine + " INT, " +
                User_Passport + " TEXT, " +
                User_Comorbidites + " TEXT );";
        db.execSQL(TBL_CREATE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TBL_User + " ;";
        db.execSQL(sql);
        onCreate(db);
    }


    public boolean addNewUser(String Fname, String Lname, String Username, String Password, String dob, String phone, String email, String gender, int isAdmin,String passport,String comorbidites) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User_Username, Username);
        cv.put(User_Fname, Fname);
        cv.put(User_Lname, Lname);
        cv.put(User_Password, encryptSha1(Password));
        cv.put(User_IsTakenVaccine, 0);
        cv.put(User_IsHascovid, 0);
        cv.put(User_Address, "None");
        cv.put(User_dob, dob);
        cv.put(User_IsAdmin, isAdmin);
        cv.put(User_IsDoneQuiz, 0);
        cv.put(User_Img, "None");
        cv.put(User_Phone, phone);
        cv.put(User_Email, email);
        cv.put(User_Gender, gender);
        cv.put(User_PostCode, 0);
        cv.put(User_IsIntrestTakenVaccine, 0);
        cv.put(User_Passport, passport);
        cv.put(User_Comorbidites, comorbidites);


        //Todo i need to write the others

/*
        private static final String User_Phone="Phone";
        private static final String User_Email="Email";
        private static final String User_Gender="Gender";
        private static final String User_Age="Age";
        private static final String User_PostCode="PostCode";
        private static final String User_IsIntrestTakenVaccine="IsIntrestTakenVaccine";
        User_Passport
        private static final String User_Comorbidites="Comorbidites";
        */


        long result = db.insert(TBL_User, null, cv);
        if (result == -1) {
            db.close();
            return false;// insertion failed
        } else {
            db.close();
            return true;
        }


    }

    public boolean addNewUserByAdmin(String Fname, String Lname, String Username, String Password, String dob, String phone, String email, String gender, int isAdmin, int IsTakenVaccine, int IsHascovid, int IsDoneQuiz, int PostCode, int IsIntrestTakenVaccine, String address, String passport, String Comorbidites) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User_Username, Username);
        cv.put(User_Fname, Fname);
        cv.put(User_Lname, Lname);
        cv.put(User_Password, encryptSha1(Password));
        cv.put(User_IsTakenVaccine, IsTakenVaccine);
        cv.put(User_IsHascovid, IsHascovid);
        cv.put(User_Address, address);
        cv.put(User_dob, dob);
        cv.put(User_IsAdmin, isAdmin);
        cv.put(User_IsDoneQuiz, IsDoneQuiz);
        cv.put(User_Img, "None");
        cv.put(User_Phone, phone);
        cv.put(User_Email, email);
        cv.put(User_Gender, gender);
        cv.put(User_PostCode, PostCode);
        cv.put(User_IsIntrestTakenVaccine, IsIntrestTakenVaccine);
        cv.put(User_Passport, passport);
        cv.put(User_Comorbidites, Comorbidites);


        //Todo i need to write the others

/*
        private static final String User_Phone="Phone";
        private static final String User_Email="Email";
        private static final String User_Gender="Gender";
        private static final String User_Age="Age";
        private static final String User_PostCode="PostCode";
        private static final String User_IsIntrestTakenVaccine="IsIntrestTakenVaccine";
        User_Passport
        private static final String User_Comorbidites="Comorbidites";
        */


        long result = db.insert(TBL_User, null, cv);
        if (result == -1) {
            db.close();
            return false;// insertion failed
        } else {
            db.close();
            return true;
        }


    }

    Cursor getUsersInfo() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_User, null);


    }

    public boolean checkUserExist(String by, String data) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        switch (by) {
            case "username":
                cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Username + " = '" + data + "' ", null);
                break;
            case "phone":
                cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Phone + " = '" + data + "' ", null);
                break;
            case "email":
                cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Email + " = '" + data + "' ", null);
                break;
            case "passport":
                cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Passport + " = '" + data + "' ", null);
                break;
        }
        if (cursor.getCount() > 0) {
            db.close();
            return true;// user exist
        } else {
            db.close();
            return false; // user is not exist
        }

    }

    public void CreateMainAdmin() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // check if the admin user is exist
        SQLiteDatabase db = getReadableDatabase();
        //check if the admin user is exist in the system by searching for the first user added, as the first user will be added to this database is the admin.
        Cursor cursor = db.rawQuery("select * from user where user.Username='admin'", null);
        if (cursor.getCount() <= 0) {
            // the admin user is not exist in the system
            addNewUser("The", "Admin", "admin", "admin", "None", "None", "none@gnone.com", "Male", 1,"None","None");
        }
        db.close();

    }


    public boolean deleteUser(int id) {

        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_User, User_id + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    // TODO need to modify
    public boolean updateUserInfo(String id, String Fname, String Lname, String Username, String Password, String IsTakenVaccine,
                                  String IsHascovid, String Address, String dob,  String IsDoneQuiz,  String Phone, String Email, String Gender, String PostCode, String IsIntrestTakenVaccine, String Passport) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User_Fname, Fname);
        cv.put(User_Lname, Lname);
        cv.put(User_Username, Username);
        cv.put(User_Password, Password);
        cv.put(User_IsTakenVaccine, IsTakenVaccine);
        cv.put(User_IsHascovid, IsHascovid);
        cv.put(User_Address, Address);
        cv.put(User_dob, dob);

        cv.put(User_IsDoneQuiz, IsDoneQuiz);

        cv.put(User_Phone, Phone);
        cv.put(User_Email, Email);
        cv.put(User_Gender, Gender);
        cv.put(User_PostCode, PostCode);
        cv.put(User_IsIntrestTakenVaccine, IsIntrestTakenVaccine);
        cv.put(User_Passport, Passport);

        return db.update(TBL_User, cv, User_id + "=?", new String[]{id.toString()}) == 1;
    }

    Cursor databassedonfiltering(String filterBy) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        switch (filterBy) {
            case "IsAdmin": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_IsAdmin + " = 1", null);
            }
            case "IsTakenVaccine": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_IsTakenVaccine + " = 1", null);
            }
            case "IsHascovid": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_IsHascovid + " = 1", null);
            }
            case "IsIntrestTakenVaccine": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_IsIntrestTakenVaccine + " = 1", null);
            }
            case "Male": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Gender + " = 'Male'", null);
            }
            case "Female": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Gender + " = 'Female'", null);
            }
            case "IsDoneQuiz": {
                return cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_IsDoneQuiz + " = 1", null);
            }
        }
        return null;
    }


    public boolean changeUserPassword(String userID, String oldPassword, String newpassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_id + " = '" + userID + "' and  " + User_Password + " = " + encryptSha1(oldPassword), null);
        if (cursor.getCount() > 0) {
            // correct old password
            ContentValues cv = new ContentValues();
            cv.put(User_Password, encryptSha1(newpassword));
            return db.update(TBL_User, cv, User_id + "=?", new String[]{userID.toString()}) == 1;
        } else
            return false;
        // wrong old password;

    }

    public String encryptSha1(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // encrypt the password;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(data.getBytes("utf8"));
        return String.format("%040x", new BigInteger(1, digest.digest()));
    }


    public String loginUser(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor checkUsername = sqLiteDatabase.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Username + " = '" + username + "' ", null);
        if (checkUsername.getCount() > 0) { // username exist
            Cursor checkUsernameAndPass = sqLiteDatabase.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Username + " = '" + username + "' and " + User_Password + " = '" + encryptSha1(password) + "'", null);
            if (checkUsernameAndPass.getCount() > 0) { // check the password for the exist username
                // the username and password correct.
                sqLiteDatabase.close();
                return "Valid";
            } else {
                //password incorrect
                sqLiteDatabase.close();
                return "InValidPass";
            }
        } else {
            // username is not exist
            sqLiteDatabase.close();
            return "InValidUsername";
        }



        /*

        int userid =-1;
        username="";
        while (cursor.moveToNext()) {
            userid = cursor.getInt(0);
            username+= cursor.getString(1);
            username+= cursor.getString(2);
            username+= cursor.getString(3);
            username+= cursor.getString(4);

        }
        String all= new String(String.valueOf(userid))+username;
        return all ;

        */
/*
        while (cursor.moveToNext()) {
            return cursor.getString(0); // return the user id
        }
        return null;*/


    }

    public Cursor getWholeOneUserInfo(String by, String data) {
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        switch (by) {
            case "id":
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_id + " = '" + data + "'", null);
                break;
            case "username":
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Username + " = '" + data + "'", null);
                break;
        }
        if (cursor.moveToNext()) {
            return cursor;
        }
        return cursor;

    }


    public String getOneUserInfo(String by, String data, String requiredInfo) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        switch (by) {
            case "id":
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_id + " = '" + data + "'", null);
                break;
            case "username":
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Username + " = '" + data + "'", null);
                break;
        }

        if (cursor.moveToNext()) {
            switch (requiredInfo) {
                case "id":
                    return new String(String.valueOf(cursor.getInt(0)));
                case "Username":
                    return new String(String.valueOf(cursor.getString(1)));
                case "Fname":
                    return new String(String.valueOf(cursor.getString(2)));
                case "Lname":
                    return new String(String.valueOf(cursor.getString(3)));
                case "Password":
                    return new String(String.valueOf(cursor.getString(4)));
                case "IsTakenVaccine":
                    return new String(String.valueOf(cursor.getInt(5)));
                case "IsHascovid":
                    return new String(String.valueOf(cursor.getInt(6)));
                case "Address":
                    return new String(String.valueOf(cursor.getString(7)));
                case "dob":
                    return new String(String.valueOf(cursor.getString(8)));
                case "IsAdmin":
                    return new String(String.valueOf(cursor.getInt(9)));
                case "IsDoneQuiz":
                    return new String(String.valueOf(cursor.getInt(10)));
                case "Img":
                    return new String(String.valueOf(cursor.getString(11)));
                case "Phone":
                    return new String(String.valueOf(cursor.getString(12)));
                case "Email":
                    return new String(String.valueOf(cursor.getString(13)));
                case "Gender":
                    return new String(String.valueOf(cursor.getString(14)));
                case "PostCode":
                    return new String(String.valueOf(cursor.getInt(15)));
                case "IsIntrestTakenVaccine":
                    return new String(String.valueOf(cursor.getInt(16)));
                case "Passport":
                    return new String(String.valueOf(cursor.getString(17)));
                case "User_Comorbidites":
                    return new String(String.valueOf(cursor.getString(18)));
            }



/*
            return cursor.getInt(0);// get the userID

        } else
            return 0;*/

        }
        return "No data found"; // if there is no data
    }


    public boolean updateTakenQuiz(String Username, String takenQuiz) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User_IsDoneQuiz, takenQuiz);
        return db.update(TBL_User, cv, User_Username + "=?", new String[]{Username.toString()}) == 1;
    }

    public boolean updateTakenVaccine(String Username, String takenVaccine) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User_IsIntrestTakenVaccine, takenVaccine);
        return db.update(TBL_User, cv, User_Username + "=?", new String[]{Username.toString()}) == 1;
    }


    Cursor searchByName(String fname) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_User + " WHERE " + User_Fname + " LIKE    '%" + fname + "%';", null);
    }


}
