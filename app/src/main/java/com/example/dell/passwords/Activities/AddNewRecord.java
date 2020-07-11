package com.example.dell.passwords.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.Listeners.ConfirmListener;
import com.example.dell.passwords.R;

public class AddNewRecord extends AppCompatActivity implements View.OnClickListener {

    EditText u_name,u_pass,u_user_id;
    EditText u_description;
String title,name,password,note;
    DBHelper dbHelper;
    Button u_button;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
        dbHelper = new DBHelper(this);
        u_name = findViewById(R.id.urlName);
        u_pass = findViewById(R.id.urlPassword);
        u_button = findViewById(R.id.saveButton);
        u_user_id = findViewById(R.id.urlUser);
        u_description = findViewById(R.id.urlDiscription);
        boolean b = ApplicationClass.isYesEdit();
        if (b) {
            title =ApplicationClass.getTitle();
            name =ApplicationClass.getUserTitle();
            password = ApplicationClass.getPassword();
            note =ApplicationClass.getNote();
            u_name.setText(title);
            u_pass.setText(password);
            u_user_id.setText(name);
            u_description.setText(note);
            u_button.setText("Update");
//            u_name.setBackgroundColor(getResources().getColor(R.color.DrakGray));
           // u_name.setBackgroundDrawable(getDrawable(R.drawable.linear_layout_twobackground));
            u_name.setTextColor(getResources().getColor(R.color.DrakGray));
            u_name.setEnabled(false);
            ApplicationClass.setEdit(false);


        }

        else {

            u_name.setText("");
            u_pass.setText("");
            u_user_id.setText("");
            u_description.setText("");
        }

        u_button.setOnClickListener(this);
        u_name.addTextChangedListener(watcher);
        u_pass.addTextChangedListener(watcher);
        u_user_id.addTextChangedListener(watcher);
        u_description.addTextChangedListener(watcher);
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (u_name.getText().length()>30)
            {
                Toast.makeText(getApplicationContext(),"Maximum limit reached.",Toast.LENGTH_LONG).show();
            }

                if( u_pass.getText().length()>40)
                {
                    Toast.makeText(getApplicationContext(),"Maximum limit reached.",Toast.LENGTH_LONG).show();
                }

                    if(u_user_id.getText().length()>40) {
                        Toast.makeText(getApplicationContext(), "Maximum limit reached.", Toast.LENGTH_LONG).show();
                    }
                    if(u_description.getText().length()>60)
            {
                Toast.makeText(getApplicationContext(),"Maximum limit reached.",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    @Override
    public void onClick(View view) {

        int id = view.getId();
        String urlname = u_name.getText().toString();
        String urlPassword = u_pass.getText().toString();
        String userId = u_user_id.getText().toString();
        String des = u_description.getText().toString();

        switch (id)
        {
            case R.id.saveButton:


                if(urlname.length()==0 || urlPassword.length()==0 ||userId.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Name, username & password can't be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(urlname.length()<5)
//                {
//                    Toast.makeText(getApplicationContext(), "Name should be atleast 5 character long.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(urlPassword.length()<5)
//                {
//                    Toast.makeText(getApplicationContext(), "Password should be atleast 5 character long.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(userId.length()<5)
//                {
//                    Toast.makeText(getApplicationContext(), "User Id should be atleast 5 character long.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                Log.d("VT","SaveButton1");
                if(u_button.getText().equals("Save") ){
                    Log.d("VT","SaveButton2");
                    boolean i = dbHelper.InsertRecord(urlname, userId, urlPassword, des, ApplicationClass.getUserName());
                    if (i) {
                        Toast.makeText(getApplicationContext(), "Record saved successfully.", Toast.LENGTH_SHORT).show();
                        u_name.setText("");
                        u_name.requestFocus();
                        u_pass.setText("");
                        u_user_id.setText("");
                        u_description.setText("");
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Record Saved.", Toast.LENGTH_SHORT).show();

                    }
                }
                Log.d("VT","UpdateButton1");
                if(u_button.getText() .equals("Update"))
                {
                    Log.d("VT","UpdateButton2");
                    boolean i = dbHelper.updateTableQuan(urlname, userId, urlPassword, des);
                    if (i) {
                        Toast.makeText(getApplicationContext(), "Record updated successfully.", Toast.LENGTH_SHORT).show();
                        u_name.setText("");
                        u_name.requestFocus();
                        u_pass.setText("");
                        u_user_id.setText("");
                        u_description.setText("");
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Record updated.", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }


}
