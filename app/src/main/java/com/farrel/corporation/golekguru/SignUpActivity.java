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

        // Add shared Animation
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
        /**
         * single OR "|" means, it is going to execute all the functions defined inside this if below, one after another
         * double OR "||" means, either this one or the second one
         */
        if (!validateFullname() | !validateUsername() | !validateEmail() | !validatePhoneNumber() | !validatePassword()) {
            return;
        }

        Intent signUpActivity2 = new Intent(SignUpActivity.this, SignUpActivity2.class);

        // pass signup data on this activity to second signup activity
        signUpActivity2.putExtra("fullName", tilFullName.getEditText().getText().toString().trim());
        signUpActivity2.putExtra("userName", tilUsername.getEditText().getText().toString().trim());
        signUpActivity2.putExtra("email", tilEmail.getEditText().getText().toString().trim());
        signUpActivity2.putExtra("phone", tilPhoneNumber.getEditText().getText().toString().trim());
        signUpActivity2.putExtra("password", tilPassword.getEditText().getText().toString().trim());

        // Add shared Animation
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(tvAppName, "trans_app_name");
        pairs[1] = new Pair<View, String>(tvCaption, "trans_caption");
        pairs[2] = new Pair<View, String>(tvDesc, "trans_desc");
        pairs[3] = new Pair<View, String>(btnNext, "trans_btn_submit");
        pairs[4] = new Pair<View, String>(btnLogin, "trans_btn_submit2");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);

        startActivity(signUpActivity2, options.toBundle());
    }


    /**
     * VALIDATION FUNCTION
     */
    private boolean validateFullname() {
        String val = tilFullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            tilFullName.setError("Nama lengkap wajib diisi");
            // tilFullName.requestFocus();
            return false;
        } else {
            tilFullName.setError(null);
            tilFullName.setErrorEnabled(false); // to remove some space below its field
            return true;
        }
    }

    private boolean validateUsername() {
        String val = tilUsername.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";
        /**
         * checkSpaces regex (regular expression) structure
         * A and z  => you can write any capital A till small z
         * w        => white spaces
         * {1,20}   => limitation with minimum 1 and maximum 20 characters
         */

        if (val.isEmpty()) {
            tilUsername.setError("Username wajib diisi");
            return false;
        } else if (val.length() > 20) {
            tilUsername.setError("Username terlalu banyak karakter");
            return false;
        }
        // if value is not going to match with regex defined before
        else if (!val.matches(checkSpaces)) {
            tilUsername.setError("spasi tidak diperbolehkan");
            return false;
        } else {
            tilUsername.setError(null);
            tilUsername.setErrorEnabled(false); // to remove some space below its field
            return true;
        }
    }

    private boolean validateEmail() {
        String val = tilEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        /**
         * checkSpaces regex (regular expression) structure
         * [a-zA-Z0-9._-]   => you can write any small a - small z, capital A - Capital Z, number 0 - 9, and "." "_" "-" characters
         * @[a-z]           => must write @ at the beginning, then you can write any small a - small z
         * \\.              => must write "."
         * [a-z]            => you can write any small a - small z
         */

        if (val.isEmpty()) {
            tilEmail.setError("Email wajib diisi");
            return false;
        }
        // if value is not going to match with regex defined before
        else if (!val.matches(checkEmail)) {
            tilEmail.setError("Format email tidak sesuai");
            return false;
        } else {
            tilEmail.setError(null);
            tilEmail.setErrorEnabled(false); // to remove some space below its field
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String val = tilPhoneNumber.getEditText().getText().toString().trim();
        String checkPhone = "^?[0-9]{8,20}$";
        /**
         * checkSpaces regex (regular expression) structure
         * [a-zA-Z0-9._-]   => you can write any small a - small z, capital A - Capital Z, number 0 - 9, and "." "_" "-" characters
         * @[a-z]           => must write @ at the beginning, then you can write any small a - small z
         * \\.              => must write "."
         * [a-z]            => you can write any small a - small z
         */

        if (val.isEmpty()) {
            tilPhoneNumber.setError("No. hp wajib diisi");
            return false;
        }
        // if value is not going to match with regex defined before
        else if (!val.matches(checkPhone)) {
            tilPhoneNumber.setError("Format No. hp tidak sesuai");
            return false;
        } else {
            tilPhoneNumber.setError(null);
            tilPhoneNumber.setErrorEnabled(false); // to remove some space below its field
            return true;
        }
    }

    private boolean validatePassword() {
        String val = tilPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            tilPassword.setError("Password wajib diisi");
            return false;
        }
        // if value is not going to match with regex defined before
//        else if (!val.matches(checkPassword)) {
//            tilPassword.setError("Password minimal memiliki 4 karakter");
//            return false;
//        }
        else {
            tilPassword.setError(null);
            tilPassword.setErrorEnabled(false); // to remove some space below its field
            return true;
        }
    }
}