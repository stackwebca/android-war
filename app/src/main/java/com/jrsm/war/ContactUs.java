package com.jrsm.war;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jrsm.blackjack.R;

public class ContactUs extends AppCompatActivity {

    EditText name, subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        name = findViewById(R.id.name);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon_logo);
    }

    public void send(View v) {

        String username = String.valueOf(name.getText());
        String usersubject = String.valueOf(subject.getText());
        String usermessage = String.valueOf(message.getText());

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType(("message/rfc822"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"labelmanager@jrsm.ca"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Email from " + usersubject + " " + username);
        i.putExtra(Intent.EXTRA_TEXT, "Message Body: " + " " + usermessage);
        try {
            startActivity(Intent.createChooser(i, "Send Mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no mail services installed", Toast.LENGTH_SHORT);
        }
    }
    public void quit(View v) {
        // TODO Auto-generated method stub
        finish();
        System.exit(0);
    }

}

