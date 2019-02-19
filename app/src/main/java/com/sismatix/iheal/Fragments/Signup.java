package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.Retrofit.ApiClient;
import com.sismatix.iheal.Retrofit.ApiInterface;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends Fragment implements View.OnClickListener {

    TextInputLayout signup_input_layout_fullname, signup_input_layout_email, signup_input_layout_password;
    EditText signup_fullname, signup_email, signup_password;
    Button btn_signup;
    TextView tv_login;


    public Signup() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        signup_input_layout_fullname = (TextInputLayout) v.findViewById(R.id.signup_input_layout_fullname);
        signup_input_layout_email = (TextInputLayout) v.findViewById(R.id.signup_input_layout_email);
        signup_input_layout_password = (TextInputLayout) v.findViewById(R.id.signup_input_layout_password);
        signup_fullname = (EditText) v.findViewById(R.id.signup_input_fullname);
        signup_email = (EditText) v.findViewById(R.id.signup_input_email);
        signup_password = (EditText) v.findViewById(R.id.signup_input_password);
        tv_login = (TextView) v.findViewById(R.id.tv_login);

        btn_signup = (Button)v.findViewById(R.id.btn_signup);

        tv_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);

        return v;
    }

    public  void loadFragment(Fragment fragment) {
        Log.e("clickone", "");
        android.support.v4.app.FragmentManager manager =getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
    private void validateSignupData() {

        final String signup_fullnamee = signup_fullname.getText().toString();
        final String signup_emailid = signup_email.getText().toString();
        final String signup_passwordd = signup_password.getText().toString();
        if(signup_fullname.getText().toString().equals("")){
            signup_input_layout_fullname.setError("Please enter your Fullname");
        }else if (signup_email.getText().length() == 0) {
            signup_input_layout_email.setError("Please enter your Email id");
        }
        else if (isValidEmailAddress(signup_email.getText().toString()) == false) {
            signup_input_layout_email.setError("Please enter valid email.");
        }
        else if(signup_password.getText().length() == 0) {

            signup_input_layout_password.setError("Please enter your Password");
        }else if (signup_password.getText().toString().length() <= 5) {
            signup_input_layout_password.setError("Password must be at least 6 characters long.");
        }
        signUpUser(signup_fullnamee, signup_emailid,signup_passwordd);
    }
    private void signUpUser(String signup_fullnamee, String signup_emailid, String signup_passwordd) {

        ApiInterface apii = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> signup = apii.signup(signup_fullnamee,signup_emailid,signup_passwordd);

        signup.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response", "" + response.body().toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    Log.e("status",""+status);
                    String meassg=jsonObject.getString("message");
                    Log.e("message",""+meassg);
                    if (status.equalsIgnoreCase("success")){
                        Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                        /*Login_preference.setcustomer_id(getActivity(),jsonObject.getString("customer_id"));
                        Login_preference.setemail(getActivity(),jsonObject.getString("email"));
                        Login_preference.setfullname(getActivity(),jsonObject.getString("fullname"));*/
                        EmailLogin nextFrag= new EmailLogin();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rootLayout, nextFrag, "login")
                                .addToBackStack(null)
                                .commit();
                    }else if (status.equalsIgnoreCase("error")){
                        Toast.makeText(getContext(), ""+meassg, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.e("",""+e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onClick(View view) {
        if(view==btn_signup){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    validateSignupData();
                }
            }, 1000);



        }else if(view==tv_login){
            EmailLogin nextFrag= new EmailLogin();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, nextFrag, "login")
                    .addToBackStack(null)
                    .commit();
        }
    }
}
