package com.inovaufrpe.makeparty.user.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.inovaufrpe.makeparty.R;


public class SplashActivity extends Activity implements Runnable {

    /**
     * @param savedInstanceState Objeto da classe Bundle que contem o estado anterior da activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    /**
     * @see EntrarOuCadastrarActivity
     */

    public void run() {
        startActivity(new Intent(this, EntrarOuCadastrarActivity.class));
        finish();
    }
}
