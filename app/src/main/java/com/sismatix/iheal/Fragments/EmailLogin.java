package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sismatix.iheal.Preference.LoginModel;
import com.sismatix.iheal.Preference.SharedPref;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */

public class EmailLogin extends Fragment {

    EditText login_email, login_password;
    Button btn_login;

    //final String loginURL = "http://192.168.43.254/android-users/login.php";

    public EmailLogin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_email_login, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        login_email = (EditText) v.findViewById(R.id.login_email);
        login_password = (EditText) v.findViewById(R.id.login_password);
        btn_login = (Button)v.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();
            }
        });

        return v;


    }

    private void validateUserData() {
        final String username = login_email.getText().toString();
        final String password = login_password.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(username)) {
            login_email.setError("Please enter your username");
            login_email.requestFocus();
            // Vibrate for 100 milliseconds
           // v.vibrate(100);
            btn_login.setEnabled(true);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            login_password.setError("Please enter your password");
            login_password.requestFocus();
            //Vibrate for 100 milliseconds
            //v.vibrate(100);
            btn_login.setEnabled(true);
            return;
        }



        //Login User if everything is fine
        loginUser(username,password);


    }

    private void loginUser(String username, String password) {

        //making api call
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginModel> login = api.login(username,password);

        login.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if(response.body().getIsSuccess() == 1){
                    //get username
                    Log.e("Login_body",""+response.body());

                    String user = response.body().getUsername();

                    //storing the user in shared preferences
                    SharedPref.getInstance(getContext()).storeUserName(user);
//                    Toast.makeText(MainActivity.this,response.body().getUsername(),Toast.LENGTH_LONG).show();

                    //startActivity(new Intent(getContext(),Home.class));

                    android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.rootLayout, new Home());
                    fragmentTransaction.commit();

                }else{
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }

        });

    }

}
