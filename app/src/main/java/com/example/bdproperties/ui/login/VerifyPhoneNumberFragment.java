package com.example.bdproperties.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bdproperties.R;
import com.google.android.material.textfield.TextInputEditText;

public class VerifyPhoneNumberFragment extends Fragment {
    String mobileNumber;
    Bundle bundle;
    TextInputEditText otp1,otp2,otp3,otp4,otp5,otp6;
    public VerifyPhoneNumberFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_verify_phone_number, container, false);

        otp1 =view.findViewById(R.id.otp_box_1);
        otp2 =view.findViewById(R.id.otp_box_2);
        otp3 =view.findViewById(R.id.otp_box_3);
        otp4 =view.findViewById(R.id.otp_box_4);
        otp5 =view.findViewById(R.id.otp_box_5);
        otp6 =view.findViewById(R.id.otp_box_6);


        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null){
                    if(s.length()==1)
                        otp2.requestFocus();
                }
            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null){
                    if(s.length()==1)
                        otp3.requestFocus();
                }
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null){
                    if(s.length()==1)
                        otp4.requestFocus();
                }
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null){
                    if(s.length()==1)
                        otp5.requestFocus();
                }
            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null){
                    if(s.length()==1)
                        otp6.requestFocus();
                }
            }
        });

        bundle =getArguments();
        mobileNumber=bundle.getString("mobielNo");
        Toast.makeText(getContext(), ""+mobileNumber, Toast.LENGTH_SHORT).show();
        return view;
    }
}