package com.sameer.bitekitchenette.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sameer.bitekitchenette.customWidgets.CustomDialogs;
import com.sameer.bitekitchenette.customWidgets.CustomFonts;
import com.sameer.bitekitchenette.models.MenuItemJ;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.adapters.MenuListJAdapter;
import com.sameer.bitekitchenette.models.PackSizeJ;
import com.sameer.bitekitchenette.utills.Constants;
import com.sameer.bitekitchenette.utills.Preferences;
import com.sameer.bitekitchenette.utills.Utills;

import java.util.ArrayList;

public class CartJActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout cartScreen;
    private TextView title;
    private ImageView back;
    private ImageView logoToolbar;
    private ImageView cartToolbar;
    private TextView cartCount;
    private RecyclerView cartRecyclerView;
    private MenuListJAdapter cartAdapter;
    private ConstraintLayout noCartItemView;
    private TextView addItem;
    private CardView totalCard;
    private TextView amount;
    private ImageView deliveryCharges;
    private TextView totalAmount;
    private View bottom;
    private Button placeOrder;
    private ArrayList<MenuItemJ> cartItems;
    private double mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_j);

        setViews();
    }

    private void setViews() {
        cartScreen = findViewById(R.id.cart_screen);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title = findViewById(R.id.title);
        title.setText(getResources().getString(R.string.title_cart_screen));
        logoToolbar = findViewById(R.id.logo_toolbar);
        cartToolbar = findViewById(R.id.cart);
        cartToolbar.setOnClickListener(this);
        cartCount = findViewById(R.id.cart_count);
        logoToolbar.setVisibility(View.GONE);
        cartToolbar.setVisibility(View.VISIBLE);
        CustomFonts.setRegularFontOnTextView(this, title);

        cartRecyclerView = findViewById(R.id.cart_recycler);
        noCartItemView = findViewById(R.id.no_cart);
        addItem = findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
        totalCard = findViewById(R.id.total_card);
        amount = findViewById(R.id.amount);
        deliveryCharges = findViewById(R.id.delivery_charges);
        deliveryCharges.setOnClickListener(this);
        totalAmount = findViewById(R.id.total_amount);
        bottom = findViewById(R.id.bottom);
        placeOrder = findViewById(R.id.place_order);
        placeOrder.setOnClickListener(this);

        cartItems = Preferences.getCartItemsFromSharedPreferences(this);

        populateCart();

        Utills.setCartCount(this, cartCount);
    }

    private void populateCart() {

        if(cartItems != null && cartItems.size() > 0) {
            noCartItemView.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            placeOrder.setVisibility(View.VISIBLE);
            totalCard.setVisibility(View.VISIBLE);
            Utills.changeNavigationBarColor(this, Constants.COLOR_GREY);
            showTotal();

            cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            cartAdapter = new MenuListJAdapter(this, cartItems, true);
            cartRecyclerView.setAdapter(cartAdapter);
            cartRecyclerView.invalidate();
        } else {
            noCartItemView.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
            placeOrder.setVisibility(View.GONE);
            totalCard.setVisibility(View.GONE);
        }
    }

    private void showTotal() {

        int orderAmount = Utills.getOrderTotalAmount(this);

        amount.setText("Rs. " + orderAmount);

        totalAmount.setText("Rs. " + orderAmount);
    }

    private void placeOrder() {

        startActivity(new Intent(this, PlaceOrderJActivity.class));
    }

    public void setCartCount(){
        Utills.setCartCount(this, cartCount);
    }

    public void addToCart(PackSizeJ item, Boolean deal){
        Utills.addToCart(this, item, deal);
        cartItems = Preferences.getCartItemsFromSharedPreferences(this);
        populateCart();
    }

    public void deleteItemFromCart(PackSizeJ item, Boolean deal){
        Utills.removeFromCart(this, item, deal);
        cartItems = Preferences.getCartItemsFromSharedPreferences(this);
        populateCart();
    }

    public void decreaseQuantity(PackSizeJ item, Boolean deal){
        Utills.decreaseFromCart(this, item, deal);
        cartItems = Preferences.getCartItemsFromSharedPreferences(this);
        populateCart();
    }

    @Override
    protected void onResume() {
        Utills.setCartCount(this, cartCount);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

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

            case R.id.delivery_charges: {
                CustomDialogs customDialog = new CustomDialogs(this);
                customDialog.showGeneralDialog(false, "", "", "Ok", "", "", true);
            }
            break;

            case R.id.place_order: {
                placeOrder();
            }
            break;

            case R.id.add_item: {
                finish();
            }
            break;
        }
    }
}