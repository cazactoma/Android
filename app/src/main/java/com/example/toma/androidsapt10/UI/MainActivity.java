package com.example.toma.androidsapt10.UI;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toma.androidsapt10.Entities.User;
import com.example.toma.androidsapt10.R;
import com.example.toma.androidsapt10.ViewModels.UserViewModel;


public class MainActivity extends AppCompatActivity {
    private EditText text_username;
    private EditText text_password;
    private Button button_login;
    private Button button_signup;
    private CheckBox checkBox_account;
    private TextView text_welcome,text_error;
    private UserViewModel userViewModel;

    private void initControls()
    {
        text_username=(EditText)findViewById(R.id.username);
        text_password=(EditText)findViewById(R.id.password);
        button_login=(Button)findViewById(R.id.login);
        button_signup=(Button)findViewById(R.id.signup);
        checkBox_account=(CheckBox)findViewById(R.id.checkBox);
        text_welcome=(TextView)findViewById(R.id.welcome);
        text_error=(TextView)findViewById(R.id.error);
        text_welcome.setText("Welcome!");
        button_signup.setVisibility(View.GONE);
        button_login.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();

        userViewModel=ViewModelProviders.of(this, new UserViewModel.Factory(getApplicationContext())).get(UserViewModel.class);

        checkBox_account.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    text_error.setVisibility(View.GONE);
                    button_signup.setVisibility(View.VISIBLE);
                    button_login.setVisibility(View.GONE);
                }
                else
                {
                    text_error.setVisibility(View.GONE);
                    button_signup.setVisibility(View.GONE);
                    button_login.setVisibility(View.VISIBLE);
                }
            }
        });

        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if(user!=null)
                {
                    Log.d("ACTIVITY","LOG IN OK");
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("ACTIVITY", "VALUE USER IS NULL");
                }

        }});

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_error.setVisibility(View.GONE);
                boolean loginSuccess=userViewModel.login(text_username.getText().toString(), text_password.getText().toString());
                if(!loginSuccess) {
                    text_error.setVisibility(View.VISIBLE);
                    text_error.setText("Invalid credentials.Please try again");
                }

            }
        });

//        button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text_error.setVisibility(View.GONE);
//                boolean loginSuccess=userViewModel
//                        .login(text_username.getText().toString(), text_password.getText().toString());
//
//            }
//        });

//        button_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text_error.setVisibility(View.GONE);
//                boolean registerSuccess=userViewModel.register(text_username.getText().toString(), text_password.getText().toString());
//                if(registerSuccess)
//                {
//                    text_error.setVisibility(View.VISIBLE);
//                    text_error.setText("You are now registered. Please login");
//                    button_signup.setVisibility(View.GONE);
//                    button_login.setVisibility(View.VISIBLE);
//                    checkBox_account.setVisibility(View.GONE);
//                }
//                else
//                {
//                    text_error.setVisibility(View.VISIBLE);
//                    text_error.setText("Can't create an account. This username already exists.Please try again");
//
//                }
//            }
//        });

    }
}
