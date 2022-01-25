package com.sameer.bitekitchenette.customWidgets;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.sameer.bitekitchenette.utills.Constants;

public class CustomFonts {

    public static void setRegularFontOnTextView(Context context, TextView view){
        if(context!=null && view!=null){
            try{
                Typeface tf= Typeface.createFromAsset(context.getAssets(), Constants.BERKSHIRE_FONT_PATH);
                if(tf!=null){
                    view.setTypeface(tf);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
