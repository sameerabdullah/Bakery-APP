package com.sameer.bitekitchenette.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sameer.bitekitchenette.customWidgets.CustomDialogs;
import com.sameer.bitekitchenette.customWidgets.CustomFonts;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.adapters.MenuListJAdapter;
import com.sameer.bitekitchenette.models.MenuItemJ;
import com.sameer.bitekitchenette.utills.Constants;
import com.sameer.bitekitchenette.utills.Preferences;
import com.sameer.bitekitchenette.utills.Utills;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceOrderJActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout placeOrderScreen;
    private TextView title;
    private ImageView back;
    private ImageView logoToolbar;
    private EditText etName;
    private EditText etNumber;
    private EditText etAddress;
    private RecyclerView orderRecycler;
    private TextView amount;
    private ImageView deliveryCharges;
    private TextView totalAmount;
    private View bottom;
    private Button placeOrder;
    private double mLastClickTime = 0;
    private Toast toast;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private int REQUEST_CHECK_SETTINGS = 80;
    private int REQUEST_LOCATION_PERMISSION = 15;
    private boolean showAddress = true;
    private boolean addressUpdated = false;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_j);

        setViews();
    }

    private void setViews() {
        placeOrderScreen = findViewById(R.id.place_order_screen);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title = findViewById(R.id.title);
        title.setText(getResources().getString(R.string.title_place_order_screen));
        CustomFonts.setRegularFontOnTextView(this, title);

        etName = findViewById(R.id.et_full_name);
        etNumber = findViewById(R.id.et_number);
        etAddress = findViewById(R.id.et_address);
        orderRecycler = findViewById(R.id.order_recycler);
        amount = findViewById(R.id.amount);
        deliveryCharges = findViewById(R.id.delivery_charges);
        deliveryCharges.setOnClickListener(this);
        totalAmount = findViewById(R.id.total_amount);
        bottom = findViewById(R.id.bottom);
        placeOrder = findViewById(R.id.place_order);
        placeOrder.setOnClickListener(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        showCurrentLocation();
        populateCart();
    }

    private void populateCart() {

        ArrayList<MenuItemJ> cartItems = Preferences.getCartItemsFromSharedPreferences(this);

        if(cartItems != null){
            bottom.setVisibility(View.VISIBLE);
            placeOrder.setVisibility(View.VISIBLE);
            Utills.changeNavigationBarColor(this, Constants.COLOR_GREY);
            showTotal();

            orderRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            MenuListJAdapter adapter = new MenuListJAdapter(this, cartItems, false);
            orderRecycler.setAdapter(adapter);
            orderRecycler.invalidate();
        } else {
            bottom.setVisibility(View.GONE);
            placeOrder.setVisibility(View.GONE);
        }
    }

    private void showTotal() {

        int orderAmount = Utills.getOrderTotalAmount(this);

        amount.setText("Rs. " + orderAmount);

        totalAmount.setText("Rs. " + orderAmount);
    }

    private boolean isValid() {
        return !etName.getText().toString().equals("") && !etAddress.getText().toString().equals("");
    }

    private void placeOrder() {

        String message = Utills.getOrderMessage(this);

        message += "Name: " + etName.getText().toString() + "\n";
        message += "Address: " + etAddress.getText().toString() + "\n";

//        if (currentLocation.getLatitude() != null && currentLocation.getLongitude() != null)
        message += "Direction:\nhttps://www.google.com/maps/search/?api=1&query=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude();

        PackageManager packageManager = null;
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url =
                    null;
            try {
                url = "https://api.whatsapp.com/send?phone=+923326573249&text=" + URLEncoder.encode(
                        message,
                        "UTF-8"
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            } else {
                Utills.sendSms(this, "+923326573249", message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utills.sendSms(this, "+923326573249", message);
        }
    }

    private void showToast(String text) {

        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void getAddress(Location location) {

        if(Geocoder.isPresent()) {

            final String[] address = {""};

            Thread thread = new Thread() {
                public void run() {
                    try {
                        if (location != null) {
                            boolean geo = true;
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());;
                            ArrayList<Address> addresses = new ArrayList<>();

                            try {
                                    addresses = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                            } catch (IOException e) {
                                if(e.toString().equals("java.io.IOException: Service not Available")) {
                                    String loc = location.getLatitude() + "," + location.getLongitude();
                                }
                                e.printStackTrace();
                            }

                            if (addresses != null && addresses.size() > 0) {
                                if (addresses.get(0).getAddressLine(0) != null) {
                                    address[0] = addresses.get(0).getAddressLine(0);
                                    etAddress.setText(address[0]);
                                }
                            }
                        }
                    } catch (Exception e) {

                    } finally {
                        if(address[0] == null || address[0] == ""){

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(address[0] != null && etAddress != null)
                                        try {
                                            etAddress.setText(address[0]);
                                        } catch (Exception e){ }
                                }
                            });
                        }
                    }
                }
            };
            thread.start();

        } else {

        }
    }

//    private void startHome(){
//        Intent home = new Intent(this, HomeActivity.class);
//        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(home);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean result = false;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            result = true;

        if(requestCode == REQUEST_LOCATION_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCurrentLocation();
            } else { }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

            case R.id.delivery_charges: {
                CustomDialogs customDialog = new CustomDialogs(this);

                customDialog.showGeneralDialog(false, "", "", "Ok", "", "", true);
            }
            break;

            case R.id.place_order: {
                if(isValid()) {
                    placeOrder();
                } else {
                    showToast("Enter Delivery Information");
                }
            }
            break;
        }
    }

    @Override
    protected void onResume() {
        if (mFusedLocationClient != null) {
            setFusedLocationClient();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        super.onDestroy();
    }

    private void askLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    private void showCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            askLocationPermission();
            return;
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); // 5 seconds interval
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        createLocationRequest();
    }

    private void setFusedLocationClient() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

                showAddress = true;
            } else {
                showAddress = false;
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            showAddress = true;
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest

                Location newLocation = locationList.get(locationList.size() - 1);

                if(!addressUpdated){
                    currentLocation = newLocation;
                    getAddress(newLocation);
                    addressUpdated = true;
                }

            }
        }
    };

    private void createLocationRequest() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);


        builder.setAlwaysShow(true);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                showAddress = true;
                setFusedLocationClient();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ((ResolvableApiException) e).startResolutionForResult(PlaceOrderJActivity.this, REQUEST_CHECK_SETTINGS);
                        showAddress = false;
//                    locationPopupShown = true
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }

                }
            }
        });
    }
}