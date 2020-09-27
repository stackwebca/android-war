package com.jrsm.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jrsm.blackjack.R;
public class aboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon_logo);
    }

    public void contactUs(View v){
        Intent i = new Intent(getApplicationContext(), ContactUs.class);
        startActivity(i);
    }

    public void goTrivia(View v){
        Intent i = new Intent(getApplicationContext(), Trivia.class);
        startActivity(i);
    }

    public void quit(View v) {
        // TODO Auto-generated method stub
        finish();
        System.exit(0);
    }
}
