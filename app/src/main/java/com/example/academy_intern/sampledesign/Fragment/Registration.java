package com.example.academy_intern.sampledesign.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academy_intern.sampledesign.Activities.AdminDashboard;
import com.example.academy_intern.sampledesign.Activities.UserDashboard;
import com.example.academy_intern.sampledesign.ApiConnection.Api;
import com.example.academy_intern.sampledesign.Model.UserProfile;
import com.example.academy_intern.sampledesign.R;
import com.example.academy_intern.sampledesign.Services.MyFCMClass;
import com.example.academy_intern.sampledesign.Services.SessionManager;

import static com.example.academy_intern.sampledesign.Activities.MainActivity.IS_USER_ADMIN;
import static com.example.academy_intern.sampledesign.Activities.MainActivity.IS_USER_LOGGED_IN;
import static com.example.academy_intern.sampledesign.Activities.MainActivity.LOGGED_IN_USER_ID;
import static com.example.academy_intern.sampledesign.Activities.MainActivity.USER_BALANCE;
//import static com.example.academy_intern.sampledesign.Activities.MainActivity.storeUserDetailsInHashMap;
import static com.example.academy_intern.sampledesign.Activities.MainActivity.storeUserDetailsInString;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends Fragment {

    Button btnRegister;
    EditText edtIdentity, edtName, edtEmail, edtPassword, edtConfirmPassword;
    TextView tvLogin;
//    public static boolean IS_USER_LOGGED_IN;
//    public static int LOGGED_IN_USER_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_screen, container, false);

        btnRegister = view.findViewById(R.id.btn_register);
        edtIdentity = view.findViewById(R.id.et_id_number);
        edtName = view.findViewById(R.id.et_name);
        edtEmail = view.findViewById(R.id.et_email);
        edtPassword = view.findViewById(R.id.et_password);
        edtConfirmPassword = view.findViewById(R.id.et_confirm_pass);
        tvLogin = view.findViewById(R.id.tv_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.display, new Login()).commit();
            }
        });

    }

    public void registerUser() {

        String identityNumber = edtIdentity.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        final SessionManager sessionManager = new SessionManager(getActivity());
        MyFCMClass.MyFirebaseMessagingService myFirebaseMessagingService = new MyFCMClass().new MyFirebaseMessagingService();
        String deviceToken = myFirebaseMessagingService.createDeviceToken();

        if (identityNumber.isEmpty()) {
            edtIdentity.setError("Id number cannot be Empty");
            edtIdentity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Name cannot be empty");
            edtName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edtEmail.setError("Email should be at least 1 character");
            edtEmail.requestFocus();
            return;

        }

        if (password.isEmpty()) {

            edtPassword.setError("Field cannot be Empty");
            edtPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtPassword.setError("password must be at least 6 characters");
            edtPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            edtConfirmPassword.setError("Field cannot be Empty");
            edtConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Passwords do not match");
            edtConfirmPassword.requestFocus();
            return;
        }

        Call <UserProfile> call = Api.getClient().register(edtIdentity.getText().toString().trim(),
                edtName.getText().toString().trim(),
                edtEmail.getText().toString().trim(),
                edtPassword.getText().toString().trim(),
                deviceToken);

        call.enqueue(new Callback <UserProfile>()
        {
            @Override
            public void onResponse(Call <UserProfile> call, Response <UserProfile> response) {
                String successMessage = response.body().getName() + ", you have been registered!";
                String failMessage = "You are already registered!";

                if (response.code() == 200)
                {
                    USER_BALANCE = response.body().getPoints();

                    sessionManager.createLoginSession(response.body().getUserId(), response.body().isUserRole(), response.body().getPoints());
                    IS_USER_LOGGED_IN = sessionManager.isLoggedIn();
                    LOGGED_IN_USER_ID = sessionManager.getLoggedInId();
                    IS_USER_ADMIN = sessionManager.getUserRole();

                    if(IS_USER_ADMIN){

                        Toast.makeText(getActivity(), successMessage, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),AdminDashboard.class);
                        startActivity(intent);

                    }
                    else
                    {
                        storeUserDetailsInString();
//                        storeUserDetailsInHashMap();
                        Toast.makeText(getActivity(), successMessage, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),UserDashboard.class);
                        startActivity(intent);
                    }

                } else

                {
                    Toast.makeText(getActivity(), failMessage, Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().replace(R.id.display, new Login()).commit();
                }
            }

            @Override
            public void onFailure(Call <UserProfile> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());
            }
        });

    }
}