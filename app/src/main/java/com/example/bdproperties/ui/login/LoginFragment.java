package com.example.bdproperties.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bdproperties.MainActivity;
import com.example.bdproperties.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    FirebaseAuth myAppAuth;
    EditText usernameEditText ;
    EditText passwordEditText ;
    Button loginButton ,createButton;
    ProgressBar loadingProgressBar ;

    public LoginFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.login);
        loadingProgressBar = view.findViewById(R.id.loading);
        createButton = view.findViewById(R.id.button2);
        myAppAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = usernameEditText.getText().toString();
                String passwordd = passwordEditText.getText().toString();

                if (emailText.isEmpty()||passwordd.isEmpty()) {

                    usernameEditText.setError("Fill Id");
                    passwordEditText.setError("Fill valid Pass");
                    Toast.makeText(getContext(), "Please enter all of informatin", Toast.LENGTH_SHORT).show();
                }  else {
                    loginUser(emailText,passwordd);

                }
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String emailText = usernameEditText.getText().toString();
//                Bundle bundle = new Bundle();
//                bundle.putString("mobielNo",
//                        emailText);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.ownerRegistrationFormFillup);
            }
        });
        return view;
    }

    private void loginUser(String emailText, String passwordd) {

        myAppAuth.signInWithEmailAndPassword(emailText,passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.saleRegistrationFrom);
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }    else {
                    Toast.makeText(getContext(), "Not Success ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


}

}