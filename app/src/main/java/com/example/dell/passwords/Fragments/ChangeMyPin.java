package com.example.dell.passwords.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.DatabaseHandler.DBHelper;
import com.example.dell.passwords.Listeners.HomePageListener;
import com.example.dell.passwords.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMyPin extends Fragment implements View.OnClickListener {
    HomePageListener mListener;
    EditText e1,e2,e3;
    Button b1;
    DBHelper dbHelper;
    ImageView oldPass,newPass,ConNew;
    View mianView;
    String mS1,mS2,mS3;
    private boolean visible = false;
    private boolean Nvisible = false;
    private boolean CNvisible = false;

    public ChangeMyPin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mianView =  inflater.inflate(R.layout.fragment_change_pin, container, false);


        dbHelper = new DBHelper(getActivity());
        e1 = mianView.findViewById(R.id.old_pin);
        oldPass = mianView.findViewById(R.id.showoldpin);
        e2 = mianView.findViewById(R.id.new_pin);
        newPass = mianView.findViewById(R.id.shownewpin);
        e3 = mianView.findViewById(R.id.c_new_pin);

        ConNew = mianView.findViewById(R.id.showconnewpin);
        b1 = mianView.findViewById(R.id.change_pin);
        oldPass.setOnClickListener(this);
        newPass.setOnClickListener(this);
        ConNew.setOnClickListener(this);
        b1.setOnClickListener(this);
        e1.addTextChangedListener(watcher);
        e2.addTextChangedListener(watcher);
        e3.addTextChangedListener(watcher);


        return  mianView;
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (e1.getText().length() > 0) {
                if (e1.getText().length() > 0) {
                    Log.d("VT", "Pin length e1" + e1.getText().length());
                    visible = true;
                    oldPass.setVisibility(View.VISIBLE);
                } else {

                    visible = false;
                    oldPass.setVisibility(View.GONE);
                }
            }
            if (e2.getText().length() > 0) {
                if (e2.getText().length() > 0) {
                    Log.d("VT", "Pin length e2 " + e2.getText().length());
                    Nvisible = true;
                    newPass.setVisibility(View.VISIBLE);
                } else {

                    Nvisible = false;
                    newPass.setVisibility(View.GONE);
                }
            }
            if (e3.getText().length() > 0) {
                if (e3.getText().length() > 0) {
                    Log.d("VT", "Pin length e3 " + e2.getText().length());
                    CNvisible = true;
                    ConNew.setVisibility(View.VISIBLE);
                } else {

                    CNvisible = false;
                    ConNew.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomePageListener) {
            mListener = (HomePageListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomePageListener");
        }
        mListener.resetHomePage();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        mS1 = e1.getText().toString();
        mS2 = e2.getText().toString();
        mS3 = e3.getText().toString();

        switch (id)
        {
            case    R.id.showoldpin:
                Log.d("V T","ImageVisisble value of visible "+visible);

                if(visible)
                {
                    Log.d("V T","ImageVisisble");
                    oldPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    e1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    e1.setSelection(e1.length());
                    Log.d("V T","ImageVisisble text length : "+e1.length());

                    visible = false;
                }
                else if (!visible)
                {
                    oldPass.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                    e1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    e1.setSelection(e1.length());
                    visible = true;
                }

                break;

            case R.id.shownewpin:


                if(Nvisible)
                {
                    newPass.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    e2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    e2.setSelection(e2.length());
                    Nvisible = false;
                }
                else if (!Nvisible)
                {
                    newPass.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                    e2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    e2.setSelection(e2.length());
                    Nvisible = true;
                }
                break;
            case    R.id.showconnewpin:

                if(CNvisible)
                {
                    ConNew.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    e3.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    e3.setSelection(e3.length());
                    CNvisible = false;
                }
                else if (!CNvisible)
                {
                    ConNew.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                    e3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    e3.setSelection(e3.length());
                    CNvisible = true;
                }
                break;



            case R.id.change_pin:

                if(mS1.length()==0 || mS2.length()==0||mS3.length()==0)
                {
                    Toast.makeText(getContext(),"Please provide all details.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(mS2.length()<6 || mS3.length()<6)
                {
                    Toast.makeText(getContext(),"New Pin should contain at least 6 characters.",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!mS2.equals(mS3))
                {
                    Toast.makeText(getContext(),"New Pin & Confirm New Pin are not same.",Toast.LENGTH_LONG).show();
                    return;
                }

                String username = ApplicationClass.getUserName();

                boolean b = dbHelper.ChangePin(username,mS1,mS2);
                if(b)
                {
                    Toast.makeText(getActivity(),"Pin changed successfully.",Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                }
                else
                    Toast.makeText(getActivity(),"old pin is incorrect.",Toast.LENGTH_LONG).show();


                break;
        }
    }
}
