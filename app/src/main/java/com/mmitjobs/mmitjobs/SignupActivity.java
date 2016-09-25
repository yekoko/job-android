package com.mmitjobs.mmitjobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mmitjobs.mmitjobs.api.MainApi;
import com.mmitjobs.mmitjobs.api.MainService;
import com.mmitjobs.mmitjobs.model.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText txt_username;
    EditText txt_email;
    EditText txt_password;
    EditText txt_phone;
    Button btnsign_up;
    TextView login_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_email    = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_phone    = (EditText) findViewById(R.id.txt_phone);
        btnsign_up  = (Button) findViewById(R.id.btn_signup);
        login_link = (TextView) findViewById(R.id.link_login);

        login_link.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnsign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }


    public void signup()
    {
        if (!validate()){
            onSignupFailed();
            return;
        }

        btnsign_up.setEnabled(false);



        String name = txt_username.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        String phone = txt_phone.getText().toString();


        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            Register user = new Register(name,email,password,phone);
            Call<Register> loginCall = MainApi.createService(MainService.class).postRegister(user);
            loginCall.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    int status = response.code();
                    Register user = response.body();
                    Log.d("error",response.message());

                    Toast.makeText(SignupActivity.this,"Phone no is already taken!",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {

                }
            });
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            onSignupSuccess();

                            progressDialog.dismiss();
                        }
                    },3000);
        }
        else{
            btnsign_up.setEnabled(true);
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignupSuccess(){
        btnsign_up.setEnabled(true);
        setResult(RESULT_OK,null);
        finish();
    }
    public void onSignupFailed(){
        Toast.makeText(getBaseContext(),"Signup Failed", Toast.LENGTH_SHORT).show();
        btnsign_up.setEnabled(true);
    }
    public boolean validate()
    {
        boolean valid = true;

        String name = txt_username.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        String phone = txt_phone.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            txt_username.setError("at least 3 characters");
            valid = false;
        }else {
            txt_username.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txt_email.setError("Enter a valid email address");
            valid = false;
        }else {
            txt_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10){
            txt_password.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        }else {
            txt_password.setError(null);
        }

        if (phone.isEmpty() ){
            txt_phone.setError("phone no is required!");
            valid = false;
        }else {
            txt_phone.setError(null);
        }
        return valid;
    }
}
