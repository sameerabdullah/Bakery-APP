package com.sameer.bitekitchenette.utills;

import android.content.Context;
import android.content.SharedPreferences;

import com.sameer.bitekitchenette.models.MenuItemJ;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Preferences {

    //-----------------------------------------------------------------------------------------------------------
    // Add, Get and Remove Menu from Shared Preferences

    public static void addMenuItemsToSharedPreferences(Context context, ArrayList<MenuItemJ> cart){
        Gson gson = new Gson();
        String jsonString = gson.toJson(cart);
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MENU_TAG_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.MENU_TAG_MODEL, jsonString);
        editor.apply();
    }

    public static ArrayList<MenuItemJ> getMenuItemsFromSharedPreferences(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MENU_TAG_SP, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MenuItemJ>>() {}.getType();
        return gson.fromJson(sharedPref.getString(Constants.MENU_TAG_MODEL, null), type);
    }

    public static void removeMenuItemsFromSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.MENU_TAG_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Constants.MENU_TAG_MODEL).apply();
    }

    //-----------------------------------------------------------------------------------------------------------
    // Add, Get and Remove Deals from Shared Preferences

    public static void addDealsToSharedPreferences(Context context, ArrayList<MenuItemJ> cart){
        Gson gson = new Gson();
        String jsonString = gson.toJson(cart);
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.DEALS_TAG_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.DEALS_TAG_MODEL, jsonString);
        editor.apply();
    }

    public static ArrayList<MenuItemJ> getDealsFromSharedPreferences(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.DEALS_TAG_SP, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MenuItemJ>>() {}.getType();
        return gson.fromJson(sharedPref.getString(Constants.DEALS_TAG_MODEL, null), type);
    }

    public static void removeDealsFromSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.DEALS_TAG_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Constants.DEALS_TAG_MODEL).apply();
    }

    //-----------------------------------------------------------------------------------------------------------
    // Add, Get and Remove Cart from Shared Preferences

    public static void addCartItemsToSharedPreferences(Context context, ArrayList<MenuItemJ> cart){
        Gson gson = new Gson();
        String jsonString = gson.toJson(cart);
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.CART_TAG_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.CART_TAG_MODEL, jsonString);
        editor.apply();
    }

    public static ArrayList<MenuItemJ> getCartItemsFromSharedPreferences(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.CART_TAG_SP, Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<MenuItemJ>>() {}.getType();
        return gson.fromJson(sharedPref.getString(Constants.CART_TAG_MODEL, null), type);
    }

    public static void removeCartItemsFromSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.CART_TAG_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Constants.CART_TAG_MODEL).apply();
    }
}
