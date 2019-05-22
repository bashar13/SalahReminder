/**
 * @file SharedPreferencesManager.java
 * @brief Manages shared preferences storing and retriving
 * @author Md Khairul Bashar
 * @date 08/01/2019
 * @Modified 08/01/2019
 */

package com.bashar.salatreminder;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static SharedPreferences stringType, doubleType, intType, booleanType;

    /**
     * @brief stores double type preferences
     * @param: string, double
     * @param: none
     */
    public static void storeDoublePref(Context context, String title, double value) {
        doubleType = context.getSharedPreferences("doubleType", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = doubleType.edit();

        edit.putFloat(title, (float)value);
        edit.commit();
    }

    /**
     * @brief stores boolean type preferences
     * @param: String boolean
     * @param: none
     */
    public static void storeBooleanPref(Context context, String title, boolean value){
        booleanType = context.getSharedPreferences("booleanType", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = booleanType.edit();

        edit.putBoolean(title, value);
        edit.commit();
    }

    /**
     * @brief stores String type preferences
     * @param: String, String
     * @param: none
     */

    public static void storeStringPref(Context context, String title, String value) {
        stringType = context.getSharedPreferences("stringType", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = stringType.edit();

        edit.putString(title, value);
        edit.commit();

    }

    /**
     * @brief stores int type preferences
     * @param: String, int
     * @param: none
     */

    public static void storeIntPref(Context context, String title, int value) {
        intType = context.getSharedPreferences("intType", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = intType.edit();

        edit.putInt(title, value);
        edit.commit();

    }

    /**
     * @brief returns boolean type preferences
     * @param: String
     * @return boolean
     */
    public static boolean getBooleanPref(Context context, String title, boolean defaultValue) {
        booleanType = context.getSharedPreferences("booleanType", Context.MODE_PRIVATE);
        return booleanType.getBoolean(title, defaultValue);

    }
    /**
     * @brief returns int type preferences
     * @param: String
     * @return int
     */
    public static int getIntPref(Context context, String title, int defaultValue){
        intType = context.getSharedPreferences("intType", Context.MODE_PRIVATE);
        return intType.getInt(title, defaultValue);
    }

    public static float getDoublePref(Context context, String title, double defaultValue) {
        doubleType = context.getSharedPreferences("doubleType", Context.MODE_PRIVATE);
        return doubleType.getFloat(title, (float)defaultValue);
    }

    public static String getStringPref(Context context, String title, String defaultValue){
        stringType = context.getSharedPreferences("stringType", Context.MODE_PRIVATE);
        return stringType.getString(title, defaultValue);
    }
}
