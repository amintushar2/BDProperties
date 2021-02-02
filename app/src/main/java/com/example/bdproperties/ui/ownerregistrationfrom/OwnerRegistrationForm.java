package com.example.bdproperties.ui.ownerregistrationfrom;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bdproperties.R;
import com.example.bdproperties.pojos.OwnerDetailsDataSet;
import com.example.bdproperties.services.ApiClient;
import com.example.bdproperties.services.RealStateApiServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerRegistrationForm extends Fragment {
    TextInputLayout dateofBirth;
    AutoCompleteTextView confirmpass,personNameEditText,mobilePhoneEditText,nidEditText,dateofbirthEditText,emaailEditText,genderEditText,passwordViewText;
    MaterialButton createOwnerButton;
    CheckBox agrementCheckBox;

    String personname ;
    String mobileNo ;
    String emailid ;
    String comPass,gender,dOb ;
    private FirebaseAuth mAuth;
    MaterialDatePicker.Builder materialDateBuilder;
    public OwnerRegistrationForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_registration_form, container, false);
        mAuth = FirebaseAuth.getInstance();
        personNameEditText=view.findViewById(R.id.personName);
        mobilePhoneEditText=view.findViewById(R.id.mobilenoEdt);
        nidEditText=view.findViewById(R.id.ownerNId);
        dateofbirthEditText=view.findViewById(R.id.ownerDateBirth);
        dateofBirth =view.findViewById(R.id.textInputLayout2);
        emaailEditText=view.findViewById(R.id.emailEdt);
        createOwnerButton=view.findViewById(R.id.createAccountBtn);
        genderEditText=view.findViewById(R.id.gender);
        agrementCheckBox=view.findViewById(R.id.termsConditionCheck);
        personname = personNameEditText.getText().toString();
        mobileNo = mobilePhoneEditText.getText().toString();
        emailid = emaailEditText.getText().toString();
        gender = genderEditText.getText().toString();
        dOb = dateofbirthEditText.getText().toString();
        materialDateBuilder = MaterialDatePicker.Builder.datePicker();

        MaterialDatePicker  materialDatePicker = materialDateBuilder.build();

        final Calendar cldr = Calendar.getInstance();
        cldr.clear();
        long today = cldr.getTimeInMillis();
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());
        constraintsBuilder.setEnd(today);


        materialDateBuilder.setCalendarConstraints(constraintsBuilder.build());


        dateofBirth.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(),"DatePCiker");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateofbirthEditText.setText(materialDatePicker.getHeaderText());
            }
        });


        createOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean bol = checkValidation();

                if (bol == false) {
                    String phoneNumber = mobilePhoneEditText.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("mobielNo.",
                            phoneNumber);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.verifyPhoneNumberFragment);
                    navController.navigate(R.id.nav_home,bundle);
                }




            }
        });


        return view;

    }

    private void registerUser(String emailid, String password1) {

        mAuth.createUserWithEmailAndPassword(emailid,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                upadteUI(user);
            }
        });

    }

    private void upadteUI(FirebaseUser user) {
        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(getContext(), ""+user.getEmail(), Toast.LENGTH_SHORT).show();
            saveOwnerData(createOwnerDetails());
        }else{
            Toast.makeText(getContext(), "NotLoging", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOwnerData(OwnerDetailsDataSet ownerDetails) {
        RealStateApiServices realStateApiServices = ApiClient.getClient().create(RealStateApiServices.class);
        Call<Void>call = realStateApiServices.setOwnerDetails(ownerDetails);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Bundle bundle = new Bundle();
                    bundle.putString("email",
                            emaailEditText.getText().toString());
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_home,bundle);
                    Toast.makeText(getContext(), "Update", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }
    Boolean checkValidation() {


        Boolean flag = false;
        if (personname.isEmpty() || emailid.isEmpty() || mobileNo.isEmpty() || comPass.isEmpty()) {
            Toast.makeText(getContext(), "Filup Info.", Toast.LENGTH_SHORT).show();
            flag = true;
            personNameEditText.setError("Enter Name");
            emaailEditText.setError("Enter Email");

        }
        if (!emailid.contains("@gmail.com")) {
            emaailEditText.setError("Enter valid Id");
            flag = true;
        }
        if (mobileNo.length() < 11) {
            passwordViewText.setError("At Least 8 Charecter");
            flag = true;

        }
        if (!agrementCheckBox.isChecked()){
            Toast.makeText(getContext(), "Please Agree Our Terms and Conditions", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }
    public OwnerDetailsDataSet createOwnerDetails(){
        OwnerDetailsDataSet ownerDetailsDataSet = new OwnerDetailsDataSet();
        ownerDetailsDataSet.setName(personNameEditText.getText().toString());
        ownerDetailsDataSet.setEmail(emaailEditText.getText().toString());
        ownerDetailsDataSet.setGender(genderEditText.getText().toString());
        ownerDetailsDataSet.setNID(String.valueOf(nidEditText.getText()));
        ownerDetailsDataSet.setGender(nidEditText.getText().toString());
        ownerDetailsDataSet.setMobile(mobilePhoneEditText.getText().toString());
        ownerDetailsDataSet.setDOB(dateofbirthEditText.getText().toString());
        ownerDetailsDataSet.setPassword(passwordViewText.getText().toString());
        return ownerDetailsDataSet;

    }
}