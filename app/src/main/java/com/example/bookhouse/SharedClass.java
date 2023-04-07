package com.example.bookhouse;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedClass {


    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public static final String ISLOGIN = "islogin";
    public static final String NAME = "fullname";
    public static final String DEPARTMENT = "department";
    public static final String REG_NUM = "regnum";
    public static final String PHONE_NUM = "phone";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ROLL_NUM = "roll";
    public static final String BATCH = "batch";
    public static final String FIRST = "first";

    SharedClass(Context context) {
        sharedPreferences = context.getSharedPreferences("userloginsession", Context.MODE_PRIVATE);
        this.context = context;
        editor = sharedPreferences.edit();
    }

    public void createloginsession(String name, String department, String regnum, String phone, String email, String password, String batch, String roll) {
        editor.putBoolean(ISLOGIN, true);
        editor.putString(NAME, name);
        editor.putString(DEPARTMENT, department);
        editor.putString(REG_NUM, regnum);
        editor.putString(PHONE_NUM, phone);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putString(ROLL_NUM, roll);
        editor.putString(BATCH, batch);
        editor.commit();
    }

    public HashMap<String, String> getuserdetails() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(NAME, sharedPreferences.getString(NAME, null));
        map.put(DEPARTMENT, sharedPreferences.getString(DEPARTMENT, null));
        map.put(REG_NUM, sharedPreferences.getString(REG_NUM, null));
        map.put(PHONE_NUM, sharedPreferences.getString(PHONE_NUM, null));
        map.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        map.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        map.put(ROLL_NUM, sharedPreferences.getString(ROLL_NUM, null));
        map.put(BATCH, sharedPreferences.getString(BATCH, null));
        return map;
    }
    public boolean getuserauth() {
        if (sharedPreferences.getBoolean(ISLOGIN, false)) {
            return true;
        } else return false;
    }
    public void logout()
    {
        editor.putBoolean(ISLOGIN, false);
        editor.putString(FIRST,"first");
        editor.commit();
    }
}
