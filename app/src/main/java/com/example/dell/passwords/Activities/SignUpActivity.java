package com.example.dell.passwords.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mUser,mPassword,mPin;
    Button mSignup;
    String sUser,sPassword,sPin;
    DBHelper dbHelper;
    AlertDialog dialog;
    ProgressBar progressBar;
    ImageView showPass, pin;
    boolean visible = false;
    boolean pvisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new DBHelper(this);
        initilize();
    }

    private void initilize() {
        mUser = findViewById(R.id.s_username);
        mPassword  = findViewById(R.id.s_password);
        mPin = findViewById(R.id.s_pin);
        mSignup = findViewById(R.id.signup);
        progressBar = findViewById(R.id.progressBar);
        showPass= findViewById(R.id.showpassword);
        pin = findViewById(R.id.showpin);

    //    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        mPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mPin.getText().length()>0)
                {
                    Log.d("VT","password length "+mPin.getText().length());
                    pvisible = true;
                    pin.setVisibility(View.VISIBLE);
                }
                else {

                    pvisible = false;
                    pin.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mPassword.getText().length()>0)
                {
                    Log.d("VT","password length "+mPassword.getText().length());
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
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPassword.setSelection(mPassword.length());
                    visible = false;
                }
                else if (!visible)
                {
                    showPass.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassword.setSelection(mPassword.length());
                    visible = true;
                }
            }
        });
        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pvisible)
                {
                    pin.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    mPin.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPin.setSelection(mPin.length());
                    pvisible = false;
                }
                else if (!pvisible)
                {
                    pin.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                    mPin.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    mPin.setSelection(mPin.length());
                    pvisible = true;
                }
            }
        });

        mSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        sUser = mUser.getText().toString();
        sPassword = mPassword.getText().toString();
        sPin = mPin.getText().toString();


        switch (id)
        {
            case    R.id.signup:
                if(sUser.length()==0 || sPassword.length() == 0 || sPin.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please provide all details.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sUser.length()<5)
                {
                    Toast.makeText(getApplicationContext(),"Username should contain at least 5 characters.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sPassword.length()<10)
                {
                    Toast.makeText(getApplicationContext(),"Password should contain at least 10 characters.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sPin.length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Pin should contain at least 6 digits.",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean dataInserted =  dbHelper.insertSignUpData(sUser,sPassword,sPin);
                if(dataInserted)
                {
                    new MyProgressBar().execute();
                }


                break;
        }
    }
    class MyProgressBar extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        @Override
        protected void onPreExecute() {
            // what to do before background task
//            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
            progressBar.setVisibility(View.GONE);
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
            dialog.dismiss();
            progressBar.setVisibility(View.GONE);
           successful();
        }

    }


    public void successful()
    {
//        startRun=false;
        //customHandler.removeCallbacks(updateTimerThread);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        View view = getLayoutInflater().inflate(R.layout.time_exced, null);
        Button timeover = view.findViewById(R.id.limitexceed);



        timeover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   myCountDownTimer.start();
                Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();

            }
        });
        builder.setView(view);
        dialog = builder.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
