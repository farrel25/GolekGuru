package com.farrel.corporation.golekguru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignUpActivity2 extends AppCompatActivity {

    TextView tvAppName, tvCaption, tvDesc;
    Button btnSignUp, btnLogin;
    RadioGroup rgGender;
    RadioButton rbGender;
    DatePicker dpBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove status bar view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2);

        // Hooks
        tvAppName = findViewById(R.id.sign_up2_tv_app_name);
        tvCaption = findViewById(R.id.sign_up2_tv_caption);
        tvDesc = findViewById(R.id.sign_up2_tv_desc);
        rgGender = findViewById(R.id.sign_up2_radio_group_gender);
        dpBirthDate = findViewById(R.id.sign_up2_birth_date);
        btnSignUp = findViewById(R.id.sign_up2_btn_sign_up);
        btnLogin = findViewById(R.id.sign_up2_btn_login);

        // get passed data from SignupActivity
        String fullName = getIntent().getStringExtra("fullName");
        String userName = getIntent().getStringExtra("userName");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String password = getIntent().getStringExtra("password");
    }

    public void signUp(View view) {
        /**
         * single OR "|" means, it is going to execute all the functions defined inside this if below, one after another
         * double OR "||" means, either this one or the second one
         */
        if (!validateGender() | !validateBirthDate()) {
            return;
        }

        // get selected Gender data
        rbGender = findViewById(rgGender.getCheckedRadioButtonId());
        String _gender = rbGender.getText().toString();

        // get birth Date data
        int birthDay = dpBirthDate.getDayOfMonth();
        int birthMonth = dpBirthDate.getMonth();
        int birthYear = dpBirthDate.getYear();
        String _birthDate = birthDay + "-" + birthMonth + "-" + birthYear;
    }

    public void callLoginActivity(View view) {
        Intent loginActivity = new Intent(SignUpActivity2.this, LoginActivity.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(tvAppName, "trans_app_name");
        pairs[1] = new Pair<View, String>(tvCaption, "trans_caption");
        pairs[2] = new Pair<View, String>(tvDesc, "trans_desc");
        pairs[3] = new Pair<View, String>(btnSignUp, "trans_btn_submit");
        pairs[4] = new Pair<View, String>(btnLogin, "trans_btn_submit2");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity2.this, pairs);

        startActivity(loginActivity, options.toBundle());
        finish();
    }



    /**
     * VALIDATION FUNCTION
     */
    public boolean validateGender() {
        // radioGroup.getCheckedRadioButtonId() == -1 => none of the radio button selected
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Silahkan pilih jenis kelamin", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateBirthDate() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userBornYear = dpBirthDate.getYear();
        int isAgeValid = currentYear - userBornYear;

        if (isAgeValid < 6) {
            Toast.makeText(this, "Umur anda belum mencukupi", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}