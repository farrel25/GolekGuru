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

public class SignUpActivity extends AppCompatActivity {

    TextView tvAppName, tvCaption, tvDesc;
    TextInputLayout tilFullName, tilUsername, tilEmail, tilPhoneNumber, tilPassword;
    Button btnNext, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        // Hooks
        tvAppName = findViewById(R.id.sign_up_tv_app_name);
        tvCaption = findViewById(R.id.sign_up_tv_caption);
        tvDesc = findViewById(R.id.sign_up_tv_desc);
        tilFullName = findViewById(R.id.sign_up_til_full_name);
        tilUsername = findViewById(R.id.sign_up_til_username);
        tilEmail = findViewById(R.id.sign_up_til_email);
        tilPhoneNumber = findViewById(R.id.sign_up_til_phone);
        tilPassword = findViewById(R.id.sign_up_til_password);
        btnNext = findViewById(R.id.sign_up_btn_next);
        btnLogin = findViewById(R.id.sign_up_btn_login);
    }

    public void callLoginActivity(View view) {
        Intent loginActivity = new Intent(SignUpActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(tvAppName, "trans_app_name");
        pairs[1] = new Pair<View, String>(tvCaption, "trans_caption");
        pairs[2] = new Pair<View, String>(tvDesc, "trans_desc");
        pairs[3] = new Pair<View, String>(tilUsername, "trans_username");
        pairs[4] = new Pair<View, String>(tilPassword, "trans_password");
        pairs[5] = new Pair<View, String>(btnNext, "trans_btn_submit");
        pairs[6] = new Pair<View, String>(btnLogin, "trans_btn_submit2");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);

        startActivity(loginActivity, options.toBundle());
        finish();
    }

    public void callSignUpActivity2(View view) {
    }
}