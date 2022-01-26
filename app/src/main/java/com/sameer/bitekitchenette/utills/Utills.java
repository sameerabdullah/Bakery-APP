package com.sameer.bitekitchenette.utills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sameer.bitekitchenette.models.MenuItemJ;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.models.PackSizeJ;

import java.util.ArrayList;

public class Utills {

    public static void transparentToolbar(Activity activity, boolean showShade) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);

            if (showShade) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.blackLight));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public static void transparentNavigation(Activity activity, boolean transparent, boolean showShadow) {

        if (transparent) {
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
            }
            if (Build.VERSION.SDK_INT >= 19) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
                if (showShadow) {
                    activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.blackLight));
                } else {
                    activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.mapSimilarColor));
            }
        }
    }

    public static void changeNavigationBarColor(Activity activity, String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (color.equals(Constants.COLOR_BLACK)) {
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.black));
            } else if(color.equals(Constants.COLOR_WHITE)){
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.white));
            } else if(color.equals(Constants.COLOR_ORANGE)){
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.themeCenterColor));
            } else if(color.equals(Constants.COLOR_GREY)){
                activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.grey));
            } else {
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
//            activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.green));
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void setCartCount(Activity activity, TextView cartCount){

        if(cartCount != null) {
            ArrayList<MenuItemJ> cart = Preferences.getCartItemsFromSharedPreferences(activity);
            if ((cart != null && cart.size() > 0)) {

                int count = 0;

                for(int i = 0; i < cart.size(); i++){
                    if(cart.get(i).getPackSizes() != null) {
                        if(cart.get(i).getDeal()){
                            count += cart.get(i).getPackSizes().get(0).getQuantity();
                        } else {
                            for (int j = 0; j < cart.get(i).getPackSizes().size(); j++) {
                                if (cart.get(i).getPackSizes().get(j).getQuantity() > 0) {
                                    count += cart.get(i).getPackSizes().get(j).getQuantity();
                                }
                            }
                        }
                    }
                }

                if(count >  99){
                    count = 99;
                }

                cartCount.setText(count + "");
                cartCount.setVisibility(View.VISIBLE);
            } else {
                cartCount.setVisibility(View.GONE);
            }
        }
    }

    public static void addToCart(Activity activity, PackSizeJ item, Boolean deal){
        ArrayList<MenuItemJ> cart = Preferences.getCartItemsFromSharedPreferences(activity);

        if(cart == null)
            cart = new ArrayList();

        if(item != null) {

            String mId = String.valueOf(item.id);

            int menuId = Integer.parseInt(mId.substring(0,mId.length()-1));

            Boolean foundMenuItem = false;
            Boolean foundPackSize = false;

            for(int i = 0; i < cart.size(); i++){
                if(cart.get(i).getId() == menuId){
                    foundMenuItem = true;

                    if (cart.get(i).getPackSizes() != null && cart.get(i).getPackSizes().size() > 0) {
                        for (int j = 0; j < cart.get(i).getPackSizes().size(); j++) {
                            if(deal){
                                cart.get(i).getPackSizes().get(j).setQuantity(cart.get(i).getPackSizes().get(j).getQuantity() + 1);
                            } else {
                                if (cart.get(i).getPackSizes().get(j).getId() == item.id) {
                                    foundPackSize = true;
                                    cart.get(i).getPackSizes().get(j).setQuantity(cart.get(i).getPackSizes().get(j).getQuantity() + 1);
                                }
                            }
                        }

                        if (!foundPackSize && !deal) {
                            item.quantity = 1;
                            cart.get(i).getPackSizes().add(item);
                        }
                    }
                }
            }

            if(!foundMenuItem){

                ArrayList<MenuItemJ> menuItems = Preferences.getMenuItemsFromSharedPreferences(activity);

                if(deal) {
                    menuItems = new ArrayList<MenuItemJ>();
                    menuItems = Preferences.getDealsFromSharedPreferences(activity);
                }

                for(int i = 0; i < menuItems.size(); i++){
                    if(menuItems.get(i).getId() == menuId){

                        MenuItemJ menu = menuItems.get(i);

                        MenuItemJ men = new MenuItemJ();

                        men.setId(menu.getId());
                        men.setItemName(menu.getItemName());
                        men.setDeal(menu.getDeal());
                        men.setPackSizes(new ArrayList());
                        if(deal){
                            for(int k = 0; k < menu.getPackSizes().size(); k++){
                                menu.getPackSizes().get(k).setQuantity(1);
                                men.getPackSizes().add(menu.getPackSizes().get(k));
                            }
                        } else {
                            item.setQuantity(1);
                            men.getPackSizes().add(item);
                        }
                        cart.add(men);
                        break;
                    }
                }
            }

            Preferences.addCartItemsToSharedPreferences(activity, cart);
        }
    }

    public static void decreaseFromCart(Activity activity, PackSizeJ item, Boolean deal){
        ArrayList<MenuItemJ> cart = Preferences.getCartItemsFromSharedPreferences(activity);

        if(cart == null)
            cart = new ArrayList();

        if(item != null) {

            String mId = String.valueOf(item.id);

            int menuId = Integer.parseInt(mId.substring(0,mId.length()-1));

            Boolean foundMenuItem = false;
            Boolean foundPackSize = false;

            for(int i = 0; i < cart.size(); i++){
                if(cart.get(i).id == menuId){
                    foundMenuItem = true;
                    if(cart.get(i).packSizes != null && cart.get(i).packSizes.size() > 0) {
                        for (int j = 0; j < cart.get(i).packSizes.size(); j++) {

                            if(deal){
                                if (cart.get(i).packSizes.get(j).getQuantity() > 1) {
                                    cart.get(i).packSizes.get(j).setQuantity(cart.get(i).packSizes.get(j).getQuantity() - 1);
                                }
                            } else {
                                if (cart.get(i).packSizes.get(j).getId() == item.id) {
                                    foundPackSize = true;
                                    if (cart.get(i).packSizes.get(j).getQuantity() > 1) {
                                        cart.get(i).packSizes.get(j).setQuantity(cart.get(i).packSizes.get(j).getQuantity() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Preferences.addCartItemsToSharedPreferences(activity, cart);
        }
    }

    public static void removeFromCart(Activity activity, PackSizeJ item, Boolean deal){
        ArrayList<MenuItemJ> cart = Preferences.getCartItemsFromSharedPreferences(activity);

        if(cart == null)
            cart = new ArrayList();

        if(item != null) {

            String mId = String.valueOf(item.id);

            int menuId = Integer.parseInt(mId.substring(0,mId.length()-1));

            Boolean foundMenuItem = false;
            Boolean foundPackSize = false;

            for(int i = 0; i < cart.size(); i++){
                if(cart.get(i).id == menuId){
                    foundMenuItem = true;
                    if(cart.get(i).packSizes != null && cart.get(i).packSizes.size() > 0) {
                        for (int j = cart.get(i).packSizes.size()-1; j >= 0; j--) {
                            if(deal){
                                cart.get(i).packSizes.remove(j);
                                foundPackSize = true;
                            } else {
                                if (cart.get(i).packSizes.get(j).getId() == item.id) {
                                    foundPackSize = true;
                                    cart.get(i).packSizes.remove(j);
                                    break;
                                }
                            }
                        }

                        if(foundPackSize){
                            if(cart.get(i).packSizes == null || cart.get(i).packSizes.size() == 0){
                                cart.remove(i);
                            }
                        }
                    }
                }
            }

//            if(!foundMenuItem){
//                for(int i = 0; i < Preferences.getMenuItemsFromSharedPreferences(activity).size(); i++){
//                    if(Preferences.getMenuItemsFromSharedPreferences(activity).get(i).getId() == menuId){
//
//                        MenuItem menu = Preferences.getMenuItemsFromSharedPreferences(activity).get(i);
//
//                        MenuItem men = new MenuItem();
//
//                        men.setId(menu.getId());
//                        men.setItemName(menu.getItemName());
//                        men.setPackSizes(new ArrayList());
//                        item.setQuantity(1);
//                        men.getPackSizes().add(item);
//                        cart.add(men);
//                        break;
//                    }
//                }
//            }

            Preferences.addCartItemsToSharedPreferences(activity, cart);
        }
    }

    public static int getOrderTotalAmount(Activity activity){
        int amount = 0;

            ArrayList<MenuItemJ> cart = Preferences.getCartItemsFromSharedPreferences(activity);
            if ((cart != null && cart.size() > 0)) {

                int count = 0;

                for(int i = 0; i < cart.size(); i++){
                    if(cart.get(i).packSizes != null) {
                        if(cart.get(i).deal){
                            amount += cart.get(i).packSizes.get(0).getPrice() * cart.get(i).packSizes.get(0).getQuantity();
                        } else {
                            for (int j = 0; j < cart.get(i).packSizes.size(); j++) {
                                if (cart.get(i).packSizes.get(j).getQuantity() > 0) {
                                    amount += (cart.get(i).packSizes.get(j).getPrice() * cart.get(i).packSizes.get(j).getQuantity());
                                }
                            }
                        }
                    }
                }
            }

            return amount;
    }

    public static String getOrderMessage(Activity activity){
        StringBuilder message = new StringBuilder();
        ArrayList<MenuItemJ> cart = Preferences.getCartItemsFromSharedPreferences(activity);

        message = new StringBuilder("#BiteKitchenette\n\nOrder:\n\n");

        if(cart != null){
            for(int i = 0; i < cart.size(); i++){
                if(cart.get(i).getDeal()){
                    message.append(cart.get(i).getItemName()).append(" x ").append(cart.get(i).getPackSizes().get(0).getQuantity()).append("\n");

                    for (int j = 0; j < cart.get(i).getPackSizes().size(); j++) {
                        message.append(cart.get(i).getPackSizes().get(j).getName()).append("  ").append(cart.get(i).getPackSizes().get(j).getPackSize()).append("-PCS").append("\n");
                    }
                } else {
                    message.append(cart.get(i).getItemName()).append("\n");

                    for (int j = 0; j < cart.get(i).getPackSizes().size(); j++) {
                        message.append(cart.get(i).getPackSizes().get(j).getPackSize()).append("-PCS x ").append(cart.get(i).getPackSizes().get(j).getQuantity()).append("\n");
                    }
                }

                message.append("---------------------------------\n");

            }
        }

        return message.toString();
    }

    public static boolean sendSms(Context context, String phone, String message){

        if(phone != null && !phone.equals("")) {

//            String s = phone.substring(0, 5);
//            if (isLandlineNumber(phone)) {
//                return false;
//            } else {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent smsIntent = new Intent(Intent.ACTION_VIEW, uri);
//        smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", phone);
            smsIntent.putExtra("sms_body", message);
            context.startActivity(smsIntent);
            return true;
//            }
        } else {
            return false;
        }
    }

    public static void shareApp(Activity activity){
        Intent shareApp = new Intent();
        shareApp.setAction(Intent.ACTION_SEND);
        shareApp.setType("text/plain");
        shareApp.putExtra(Intent.EXTRA_TEXT,
                "Enjoy home made frozen food by Bite Kitchenette delivered to your doorstep in few clicks, Download Now!.\n\nhttps://play.google.com/store/apps/details?id=com.ahtesham.bitekitchenette");
        activity.startActivity(Intent.createChooser(shareApp, "Share via"));
    }
}