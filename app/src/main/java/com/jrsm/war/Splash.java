package com.jrsm.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jrsm.blackjack.R;

public class Splash extends AppCompatActivity {

    ImageView logo;
    Animation myAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        logo = findViewById(R.id.logo);

        myAnim = AnimationUtils.loadAnimation(this, R.anim.splash);
        myAnim.reset();
        logo.clearAnimation();
        logo.startAnimation(myAnim);

        final Intent i = new Intent(getApplicationContext(), MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
            }
        }, 2000);

    }
}
