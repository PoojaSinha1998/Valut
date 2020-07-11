package com.example.dell.passwords.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ApplicationClass applicationClass;
    EditText name, pass;
    DBHelper dbHelper;
    ProgressBar progressBar;

    Button login;
    ImageView showPass;
    boolean visible = false;
    String s_name, s_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        initilizeFields();
    }

    private void initilizeFields() {
        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        showPass = findViewById(R.id.showsignpass);
        progressBar = findViewById(R.id.progressBar);

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pass.getText().length()>0)
                {
                    Log.d("VT","password length "+pass.getText().length());
                    visible = true;
                    showPass.setVisibility(View.VISIBLE);
                }
                else {

                    visible = false;
                    showPass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(visible)
                {
                    showPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pass.setSelection(pass.length());
                    visible = false;
                }
                else if (!visible)
                {
                    showPass.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setSelection(pass.length());
                    visible = true;
                }
            }
        });
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        s_name = name.getText().toString();
        s_pass = pass.getText().toString();

        switch (id) {
            case R.id.loginButton:

                if (s_name.length() == 0 && s_pass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please provide username & password.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (s_name.length() >=0 && s_pass.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please provide password.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (s_name.length() <= 0 && s_pass.length() >=0) {
                    Toast.makeText(getApplicationContext(), "Please provide username.", Toast.LENGTH_LONG).show();
                    return;
                }



                int userAvailable = dbHelper.CheckLogin(s_name, s_pass);
                if (userAvailable == 0) {
                    Log.d("VT", "value of userAvailable 1: " + userAvailable);
                    Toast.makeText(getApplicationContext(), "Username or password is incorrect.", Toast.LENGTH_LONG).show();
                    name.setText("");
                    pass.setText("");
                    name.requestFocus();
                    return;
                }
                if (userAvailable == 2) {
                    Log.d("VT", "Invalid Password: " + userAvailable);
                    Toast.makeText(getApplicationContext(), "Username or password is incorrect.", Toast.LENGTH_LONG).show();
                    name.setText("");
                    pass.setText("");
                    name.requestFocus();
                    return;
                }
                if (userAvailable == 1) {
                    Log.d("VT", "value of userAvailable 3: " + userAvailable);
                    new MyProgressBarOne().execute();
                    //user matched

                }

                break;
        }
    }

    class MyProgressBarOne extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            // what to do before background task

            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);
            ApplicationClass.setUserName(s_name);
            dialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), AllData.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
