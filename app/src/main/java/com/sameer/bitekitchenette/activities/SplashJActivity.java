package com.sameer.bitekitchenette.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.sameer.bitekitchenette.customWidgets.CustomFonts;
import com.sameer.bitekitchenette.R;
import com.sameer.bitekitchenette.utills.Utills;

public class SplashJActivity extends AppCompatActivity {

    private double STARTUP_DELAY = 0.00;
    private double ANIM_ITEM_DURATION = 1000.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);

        Utills.transparentToolbar(this, false);
        Utills.transparentNavigation(this, true, false);
        setContentView(R.layout.activity_splash_j);

        TextView title = findViewById(R.id.label);
        CustomFonts.setRegularFontOnTextView(this, title);
        animate();
    }

    private void animate() {
        ConstraintLayout logoView = findViewById(R.id.logo_section);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewCompat.animate(logoView)
                    .translationX(Float.valueOf(-250))
                    .setStartDelay((long) STARTUP_DELAY)
                    .setDuration((long) ANIM_ITEM_DURATION).setInterpolator(new DecelerateInterpolator(1.2f)).withEndAction(new Runnable() {
                @Override
                public void run() {
                    afterAnimation();
                }
            }).start();
        } else {
            ViewCompat.animate(logoView)
                    .translationY(Float.valueOf(-200))
                    .setStartDelay((long)STARTUP_DELAY)
                    .setDuration((long)ANIM_ITEM_DURATION).setInterpolator(new DecelerateInterpolator(1.2f)
            ).withEndAction(new Runnable() {
                @Override
                public void run() {
                    afterAnimation();
                }
            }).start();
        }
    }

    private void afterAnimation(){
        Intent i = new Intent(this, HomeJActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}