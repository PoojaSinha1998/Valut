package com.example.dell.passwords.Activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.passwords.R;

public class ShowPasssword extends AppCompatActivity {

    TextView show_password;
    TextView show_name,show_url;
    TextView show_des;
    Button showOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_passsword);
        
        show_name = findViewById(R.id.showName);
        show_password = findViewById(R.id.showPassword);
        show_url = findViewById(R.id.showUser);
        show_des = findViewById(R.id.showDiscription);
        showOk = findViewById(R.id.okButton);
        setDataonFields();
        
        showOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setDataonFields() {

        Bundle bundle = getIntent().getExtras();

//Extract the data…
        String name = bundle.getString("NAME");
        String url = bundle.getString("URL");
        String password = bundle.getString("PASSWORD");
        String dec = bundle.getString("DES");

        show_name.setText(name);
        show_password.setText(password);
        show_url.setText(url);
        show_des.setText(dec);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
