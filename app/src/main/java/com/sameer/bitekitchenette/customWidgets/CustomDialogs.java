package com.sameer.bitekitchenette.customWidgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sameer.bitekitchenette.R;

public class CustomDialogs {

    private Context context;

    // General Dialog
    private ConstraintLayout generalDialogView;
    private Dialog generalDialog;
    private TextView dTitle, dMessage;
    private Button no;
    private Button yes;

    public CustomDialogs(Context context){
        this.context = context;
    }

    //----------------------------------------------------------------------------------------------
    // General Dialog

    public void setGeneralDialog(boolean nightMode, final String title, String message, String b1Text, String b2Text, String b3Text, Boolean cancellable){
        if (context != null && generalDialog == null) {
            generalDialog = new Dialog(context, R.style.CustomDialog);
            generalDialog.setContentView(R.layout.custom_dialog_general);
            generalDialog.setCancelable(cancellable);
            generalDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            generalDialogView = generalDialog.findViewById(R.id.general_dialog);
            dTitle = generalDialog.findViewById(R.id.dialog_title);
            dMessage = generalDialog.findViewById(R.id.dialog_message);
            no = generalDialog.findViewById(R.id.no);
            yes = generalDialog.findViewById(R.id.yes);

//            if (nightMode){
//                generalDialogView.setBackgroundResource(R.drawable.background_et_round_night_mode);
//                dTitle.setTextColor(context.getResources().getColor(R.color.white));
//                dMessage.setTextColor(context.getResources().getColor(R.color.textColorNightMode));
//            }

            if(title != null && !title.equals("")){
                dTitle.setText(title);

            }
//            else {
//                dTitle.setVisibility(View.GONE);
//            }

            if(message != null && !message.equals("")){
                dMessage.setText(message);
            }

            if(b1Text != null && !b1Text.equals("")){
                no.setText(b1Text);
            } else {
                no.setVisibility(View.GONE);
            }

            if(b2Text != null && !b2Text.equals("")){
                yes.setText(b2Text);
            } else {
                yes.setVisibility(View.GONE);
            }

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissGeneralDialog();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissGeneralDialog();
            }
        });
    }
}

    public void dismissGeneralDialog(){
        if(generalDialog != null && generalDialog.isShowing()){
            generalDialog.dismiss();
        }
    }

    public void showGeneralDialog(boolean nightMode, String title, String message, String b1Text, String b2Text, String b3Text, Boolean cancellable){
        if(generalDialog == null)
            setGeneralDialog(nightMode, title, message, b1Text, b2Text, b3Text, cancellable);

        if(generalDialog != null && !generalDialog.isShowing()){
            generalDialog.show();
        }
    }
}
