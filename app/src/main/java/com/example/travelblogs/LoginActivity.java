package com.example.travelblogs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textUsernameLayout;
    private TextInputLayout textPasswordInput;
    private Button loginButton;

    private ProgressBar progressBar;

    private BlogPreference preference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        preference = new BlogPreference(this);
        if(preference.isLoggedIn()){
            startMainActivity();
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        textUsernameLayout = findViewById(R.id.textUsernameLayout);
        textPasswordInput = findViewById(R.id.textPasswordInput);
        loginButton = findViewById(R.id.loginButton);

//        loginButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                LoginActivity.this.onLoginClicked();
//            }
//        });
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> onLoginClicked());

        // for validation message cleanup
        textUsernameLayout.getEditText().addTextChangedListener(createTextWatcher(textUsernameLayout));
        textPasswordInput.getEditText().addTextChangedListener(createTextWatcher(textPasswordInput));

        // Login indicator
        progressBar = findViewById(R.id.progressBar);
    }

    // Validation username and password
    public void onLoginClicked(){
        String username = textUsernameLayout.getEditText().getText().toString();
        String password = textPasswordInput.getEditText().getText().toString();

        if(username.isEmpty()){
            textUsernameLayout.setError("Username must not be empty");
        }
        else if(password.isEmpty()){
            textPasswordInput.setError("Password must not be empty");
        }
        else if(!username.equals("admin") || !password.equals("admin")){
            showErrorDialog();
        }
        else{
            performLogin();
        }
    }

    // validation message cleanup
    private TextWatcher createTextWatcher(TextInputLayout textPasswordInput){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  // not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textPasswordInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // not needed
            }
        };
    }

    // Error dialog box
    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username or password is not correct. Please try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();

    }

    //Loading indicator
    private void performLogin(){
        preference.setLoggedIn(true);

        textUsernameLayout.setEnabled(false);
        textPasswordInput.setEnabled(false);
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        // after clicking the login button we want delay of 2sec
        Handler handler = new Handler();
        handler.postDelayed(()->{
            startMainActivity();
            finish(); // to finish the login activity
        },2000);
    }

    // to start the main activity
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

