package com.example.bdproperties.ui.saleregistrationfrom;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bdproperties.R;


public class SaleRegistrationFrom extends Fragment {

    RadioButton rentRadioButton,selectRadioButton;
    RadioGroup radioGroup;
    int purposeID =1 ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public SaleRegistrationFrom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sale_registration_from, container, false);

        rentRadioButton = view.findViewById(R.id.rentRadioButton);
        selectRadioButton=view.findViewById(R.id.sellRadioButton);
        radioGroup =view.findViewById(R.id.radioGroup);
        selectRadioButton.setChecked(true);
        preferences =this.getContext().getSharedPreferences("MyFref",0);
        //  preferences = getActivity().getSharedPreferences("MyFref", 0);
        editor =preferences.edit();
        editor.putInt("purpose",purposeID);

        FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragmentReplace, new SellRegistrationFromFragment());
        ft1.commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rentRadioButton:
                       purposeID =2;
                       editor.putInt("purpose",purposeID);
                       editor.apply();

                        FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.fragmentReplace, new SellRegistrationFromFragment());
                        ft2.commit();
                        break;
                    case R.id.sellRadioButton:
                        purposeID = 1;
                        editor.putInt("purpose",purposeID);
                        editor.apply();
                        FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.fragmentReplace, new SellRegistrationFromFragment());
                        ft1.commit();
                }
            }
        });

        return view;
    }
}