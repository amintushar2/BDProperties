package com.example.bdproperties.ui.saleregistrationfrom;

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
        FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragmentReplace, new SellRegistrationFromFragment());
        ft1.commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rentRadioButton:
                        Toast.makeText(getContext(), "RRRR", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentReplace, new Rent());
                        ft.commit();


                        break;
                    case R.id.sellRadioButton:
                        FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.fragmentReplace, new SellRegistrationFromFragment());
                        ft1.commit();
                        break;
                }
            }
        });

        return view;
    }
}