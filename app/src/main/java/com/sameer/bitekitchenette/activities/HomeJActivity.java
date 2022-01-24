package com.sameer.bitekitchenette.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sameer.bitekitchenette.customWidgets.CustomFonts;
import com.sameer.bitekitchenette.models.MenuItemJ;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.adapters.MenuListJAdapter;
import com.sameer.bitekitchenette.models.PackSizeJ;
import com.sameer.bitekitchenette.utills.Preferences;
import com.sameer.bitekitchenette.utills.Utills;

import java.util.ArrayList;

public class HomeJActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout homeScreen;
    private TextView title;
    private ImageView back;
    private ImageView logoToolbar;
    private ImageView cartToolbar;
    private TextView cartCount;
    private RecyclerView menuRecyclerView;
    private double mLastClickTime;
    private ArrayList<MenuItemJ> menu;
    private Long lastPress;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_j);

        setViews();
    }

    private void setViews() {
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText(getResources().getString(R.string.title_home_screen));
        logoToolbar = findViewById(R.id.logo_toolbar);
        cartToolbar = findViewById(R.id.cart);
        cartToolbar.setOnClickListener(this);
        cartCount = findViewById(R.id.cart_count);
        logoToolbar.setVisibility(View.GONE);
        cartToolbar.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);

        menuRecyclerView = findViewById(R.id.menu_recycler);

        Utills.setCartCount(this, cartCount);

        CustomFonts.setRegularFontOnTextView(this, title);

        populateMenu();
    }

    private void populateMenu() {

        title.setText(getResources().getString(R.string.title_home_screen));

        menu = new ArrayList();

        if(Preferences.getMenuItemsFromSharedPreferences(this) == null) {

            // Menu Item 1

            MenuItemJ menuItem1 = new MenuItemJ();
            menuItem1.id = 1;
            menuItem1.itemName = "Chicken Patties";
            menuItem1.packSizes = new ArrayList();

            PackSizeJ packSize11 = new PackSizeJ();
            packSize11.id = 11;
            packSize11.name = "Chicken Patties";
            packSize11.packSize = 6;
            packSize11.price = 270;
            packSize11.free = -1;

            PackSizeJ packSize12 = new PackSizeJ();
            packSize12.id = 12;
            packSize12.name = "Chicken Patties";
            packSize12.packSize = 12;
            packSize12.price = 540;
            packSize12.free = -1;

            PackSizeJ packSize13 = new PackSizeJ();
            packSize13.id = 13;
            packSize13.name = "Chicken Patties";
            packSize13.packSize = 18;
            packSize13.price = 810;
            packSize13.free = 1;

            menuItem1.packSizes.add(packSize11);
            menuItem1.packSizes.add(packSize12);
            menuItem1.packSizes.add(packSize13);


            // Menu Item 2

            MenuItemJ menuItem2 = new MenuItemJ();
            menuItem2.id = 2;
            menuItem2.itemName = "Chicken Bread";
            menuItem2.packSizes = new ArrayList();

            PackSizeJ packSize21 = new PackSizeJ();
            packSize21.id = 21;
            packSize21.name = "Chicken Bread";
            packSize21.packSize = 12;
            packSize21.price = 350;
            packSize21.free = -1;

            PackSizeJ packSize22 = new PackSizeJ();
            packSize22.id = 22;
            packSize22.name = "Chicken Bread";
            packSize22.packSize = 24;;;
            packSize22.price = 700;
            packSize22.free = 1;

            PackSizeJ packSize23 = new PackSizeJ();
            packSize23.id = 23;
            packSize23.name = "Chicken Bread";
            packSize23.packSize = 36;
            packSize23.price = 1050;
            packSize23.free = 3;;

            menuItem2.packSizes.add(packSize21);
            menuItem2.packSizes.add(packSize22);
            menuItem2.packSizes.add(packSize23);


            // Menu Item 3

            MenuItemJ menuItem3 = new MenuItemJ();
            menuItem3.id = 3;
            menuItem3.itemName = "Pastry";
            menuItem3.packSizes = new ArrayList();

            PackSizeJ packSize31 = new PackSizeJ();
            packSize31.id = 31;
            packSize31.name = "Pastry";
            packSize31.packSize = 3;
            packSize31.price = 275;
            packSize31.free = -1;

            PackSizeJ packSize32 = new PackSizeJ();
            packSize32.id = 32;
            packSize32.name = "Pastry";
            packSize32.packSize = 6;
            packSize32.price = 550;
            packSize32.free = -1;

            PackSizeJ packSize33 = new PackSizeJ();
            packSize33.id = 33;
            packSize33.name = "Pastry";
            packSize33.packSize = 9;
            packSize33.price = 825;
            packSize33.free = 1;

            menuItem3.packSizes.add(packSize31);
            menuItem3.packSizes.add(packSize32);
            menuItem3.packSizes.add(packSize33);


            // Menu Item 4

            MenuItemJ menuItem4 = new MenuItemJ();
            menuItem4.id = 4;
            menuItem4.itemName = "Donut";
            menuItem4.packSizes = new ArrayList();

            PackSizeJ packSize41 = new PackSizeJ();
            packSize41.id = 41;
            packSize41.name = "Donut";
            packSize41.packSize = 6;
            packSize41.price = 375;
            packSize41.free = -1;

            PackSizeJ packSize42 = new PackSizeJ();
            packSize42.id = 42;
            packSize42.name = "Donut";
            packSize42.packSize = 12;
            packSize42.price = 750;
            packSize42.free = -1;

            PackSizeJ packSize43 = new PackSizeJ();
            packSize43.id = 43;
            packSize43.name = "Donut";
            packSize43.packSize = 18;
            packSize43.price = 1125;
            packSize43.free = 1;

            menuItem4.packSizes.add(packSize41);
            menuItem4.packSizes.add(packSize42);
            menuItem4.packSizes.add(packSize43);


            // Menu Item 5

            MenuItemJ menuItem5 = new MenuItemJ();
            menuItem5.id = 5;
            menuItem5.itemName = "Doner";
            menuItem5.packSizes = new ArrayList();

            PackSizeJ packSize51 = new PackSizeJ();
            packSize51.id = 51;
            packSize51.name = "Doner";
            packSize51.packSize = 12;
            packSize51.price = 280;
            packSize51.free = -1;

            PackSizeJ packSize52 = new PackSizeJ();
            packSize52.id = 52;
            packSize52.name = "Doner";
            packSize52.packSize = 24;
            packSize52.price = 560;
            packSize52.free = -1;

            PackSizeJ packSize53 = new PackSizeJ();
            packSize53.id = 53;
            packSize53.name = "Doner";
            packSize53.packSize = 36;
            packSize53.price = 840;
            packSize53.free = 2;

            menuItem5.packSizes.add(packSize51);
            menuItem5.packSizes.add(packSize52);
            menuItem5.packSizes.add(packSize53);


            // Menu Item 6

            MenuItemJ menuItem6 = new MenuItemJ();
            menuItem6.id = 6;
            menuItem6.itemName = "Chicken Pizza";
            menuItem6.packSizes = new ArrayList();

            PackSizeJ packSize61 = new PackSizeJ();
            packSize61.id = 61;
            packSize61.name = "Chicken Pizza";
            packSize61.packSize = 12;
            packSize61.price = 300;
            packSize61.free = -1;

            PackSizeJ packSize62 = new PackSizeJ();
            packSize62.id = 62;
            packSize62.name = "Chicken Pizza";
            packSize62.packSize = 24;
            packSize62.price = 600;
            packSize62.free = -1;

            PackSizeJ packSize63 = new PackSizeJ();
            packSize63.id = 63;
            packSize63.name = "Chicken Pizza";
            packSize63.packSize = 36;
            packSize63.price = 900;
            packSize63.free = 3;

            menuItem6.packSizes.add(packSize61);
            menuItem6.packSizes.add(packSize62);
            menuItem6.packSizes.add(packSize63);


            // Menu Item 7

            MenuItemJ menuItem7 = new MenuItemJ();
            menuItem7.id = 7;
            menuItem7.itemName = "Chicken Roll";
            menuItem7.packSizes = new ArrayList();

            PackSizeJ packSize71 = new PackSizeJ();
            packSize71.id = 71;
            packSize71.name = "Chicken Roll";
            packSize71.packSize = 12;
            packSize71.price = 375;
            packSize71.free = -1;

            PackSizeJ packSize72 = new PackSizeJ();
            packSize72.id = 72;
            packSize72.name = "Chicken Roll";
            packSize72.packSize = 24;
            packSize72.price = 750;
            packSize72.free = -1;

            PackSizeJ packSize73 = new PackSizeJ();
            packSize73.id = 73;
            packSize73.name = "Chicken Roll";
            packSize73.packSize = 36;
            packSize73.price = 1125;
            packSize73.free = 3;

            menuItem7.packSizes.add(packSize71);
            menuItem7.packSizes.add(packSize72);
            menuItem7.packSizes.add(packSize73);


            // Menu Item 8

            MenuItemJ menuItem8 = new MenuItemJ();
            menuItem8.id = 8;
            menuItem8.itemName = "Chicken Sticks";
            menuItem8.packSizes = new ArrayList();

            PackSizeJ packSize81 = new PackSizeJ();
            packSize81.id = 81;
            packSize81.name = "Chicken Sticks";
            packSize81.packSize = 6;
            packSize81.price = 300;
            packSize81.free = -1;

            PackSizeJ packSize82 = new PackSizeJ();
            packSize82.id = 82;
            packSize82.name = "Chicken Sticks";
            packSize82.packSize = 12;
            packSize82.price = 600;
            packSize82.free = -1;

            PackSizeJ packSize83 = new PackSizeJ();
            packSize83.id = 83;
            packSize83.name = "Chicken Sticks";
            packSize83.packSize = 18;
            packSize83.price = 900;
            packSize83.free = 2;

            menuItem8.packSizes.add(packSize81);
            menuItem8.packSizes.add(packSize82);
            menuItem8.packSizes.add(packSize83);


            // Menu Item 9

            MenuItemJ menuItem9 = new MenuItemJ();
            menuItem9.id = 9;
            menuItem9.itemName = "Cupcakes";
            menuItem9.packSizes = new ArrayList();

            PackSizeJ packSize91 = new PackSizeJ();
            packSize91.id = 91;
            packSize91.name = "Cupcakess";
            packSize91.packSize = 6;
            packSize91.price = 320;
            packSize91.free = -1;

            PackSizeJ packSize92 = new PackSizeJ();
            packSize92.id = 92;
            packSize92.name = "Cupcakes";
            packSize92.packSize = 12;
            packSize92.price = 640;
            packSize92.free = -1;

            PackSizeJ packSize93 = new PackSizeJ();
            packSize93.id = 93;
            packSize93.name = "Cupcakes";
            packSize93.packSize = 18;
            packSize93.price = 960;
            packSize93.free = 2;

            menuItem9.packSizes.add(packSize91);
            menuItem9.packSizes.add(packSize92);
            menuItem9.packSizes.add(packSize93);


            // Menu Item 10

            MenuItemJ menuItem10 = new MenuItemJ();
            menuItem10.id = 10;
            menuItem10.itemName = "Bread";
            menuItem10.packSizes = new ArrayList();

            PackSizeJ packSize101 = new PackSizeJ();
            packSize101.id = 101;
            packSize101.name = "Bread";
            packSize101.packSize = 3;
            packSize101.price = 300;
            packSize101.free = -1;

            PackSizeJ packSize102 = new PackSizeJ();
            packSize102.id = 102;
            packSize102.name = "Bread";
            packSize102.packSize = 6;
            packSize102.price = 600;
            packSize102.free = -1;

            PackSizeJ packSize103 = new PackSizeJ();
            packSize103.id = 103;
            packSize103.name = "Bread";
            packSize103.packSize = 9;
            packSize103.price = 900;
            packSize103.free = 1;

            menuItem10.packSizes.add(packSize101);
            menuItem10.packSizes.add(packSize102);
            menuItem10.packSizes.add(packSize103);


            menu.add(menuItem1);
            menu.add(menuItem2);
            menu.add(menuItem3);
            menu.add(menuItem4);
            menu.add(menuItem5);
            menu.add(menuItem6);
            menu.add(menuItem7);
            menu.add(menuItem8);
            menu.add(menuItem9);
            menu.add(menuItem10);

            Preferences.addMenuItemsToSharedPreferences(this, menu);
        } else {
            menu = Preferences.getMenuItemsFromSharedPreferences(this);
        }

        populateItems();
    }

    private void populateItems() {

        if(menu != null) {
            menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            MenuListJAdapter adapter = new MenuListJAdapter(this, menu, true);
            menuRecyclerView.setAdapter(adapter);
            menuRecyclerView.invalidate();
        }
    }

    public void setCartCount(){
        Utills.setCartCount(this, cartCount);
    }

    private void showToast(String text) {

        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onResume() {
        Utills.setCartCount(this, cartCount);
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                performClick(v);
            }
        }
    }

    private void performClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                finish();
            break;

            case R.id.cart:
                startActivity(new Intent(this, CartJActivity.class));
                break;
        }
    }
}