package com.chenkovegor.gameofthrones.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenkovegor.gameofthrones.R;
import com.chenkovegor.gameofthrones.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = ConstantManager.TAG_PREFIX + "BaseActivity";
    protected ProgressDialog mProgressDialog;

    public void showProgress(){
        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 22, 7, 5)));
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress_splash);
    }

    public void hideProgress(){
        if(mProgressDialog != null) {
            if(mProgressDialog.isShowing()){
                mProgressDialog.hide();
            }
        }
    }

    public void setMessageProgressDialogAndShow(String text){
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.splash_screen_message);
        TextView messageForUserTxt = (TextView) mProgressDialog.findViewById(R.id.message_txt);
        messageForUserTxt.setText(text);
        Button button = (Button) mProgressDialog.findViewById(R.id.ok_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hideProgress();
                mProgressDialog = null;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
        super.onPause();
    }
}
