package com.farrel.corporation.golekguru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextView tvAppName, tvCaption, tvDesc;
    TextInputLayout tilUsername, tilPassword;
    Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // Hooks
        tvAppName = findViewById(R.id.login_tv_app_name);
        tvCaption = findViewById(R.id.login_tv_caption);
        tvDesc = findViewById(R.id.login_tv_desc);
        tilUsername = findViewById(R.id.login_til_username);
        tilPassword = findViewById(R.id.login_til_password);
        btnLogin = findViewById(R.id.login_btn_login);
        btnSignUp = findViewById(R.id.login_btn_sign_up);
    }

    public void callSignUpActivity(View view) {
        Intent signUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(tvAppName, "trans_app_name");
        pairs[1] = new Pair<View, String>(tvCaption, "trans_caption");
        pairs[2] = new Pair<View, String>(tvDesc, "trans_desc");
        pairs[3] = new Pair<View, String>(tilUsername, "trans_username");
        pairs[4] = new Pair<View, String>(tilPassword, "trans_password");
        pairs[5] = new Pair<View, String>(btnLogin, "trans_btn_submit");
        pairs[6] = new Pair<View, String>(btnSignUp, "trans_btn_submit2");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

        startActivity(signUpActivity, options.toBundle());
        finish();
    }
}