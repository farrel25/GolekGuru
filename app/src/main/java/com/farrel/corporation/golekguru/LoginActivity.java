package com.farrel.corporation.golekguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farrel.corporation.golekguru.dashboard.DashboardActivity;
import com.farrel.corporation.golekguru.databases.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView tvAppName, tvCaption, tvDesc;
    TextInputLayout tilUsername, tilPassword;
    Button btnLogin, btnSignUp;
    RelativeLayout rlProgressBar;

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
        rlProgressBar = findViewById(R.id.login_progress_bar);

        rlProgressBar.setVisibility(View.GONE);
    }


    /**
     * MOVE TO REGISTRATION PAGE
     */
    public void callSignUpActivity(View view) {
        Intent signUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);

        // shared animations
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


    /**
     * USER LOGIN
     */
    public void login(View view) {
        // check internet connection
        if (!isConnected(this)) {
            showCustomDialog();
            return;
        }

        // validate username and password
        if (!validateUsername() | !validatePassword()) {
            return;
        }

        rlProgressBar.setVisibility(View.VISIBLE);
        // get data from fields
        final String _usernameLoginData = tilUsername.getEditText().getText().toString().trim();
        final String _passwordLoginData = tilPassword.getEditText().getText().toString().trim();

        // Check wether user exist or not in database
        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(_usernameLoginData);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // if username exist then get password
                if (snapshot.exists()) {
                    tilUsername.setError(null);
                    tilUsername.setErrorEnabled(false);
                    String systemPassword = snapshot.child(_usernameLoginData).child("password").getValue(String.class);

                    // if password exist and matches with user's password then get other fields from database
                    if (systemPassword.equals(_passwordLoginData)) {
                        tilPassword.setError(null);
                        tilPassword.setErrorEnabled(false);

                        // get user data from firebase database
                        String _fullName = snapshot.child(_usernameLoginData).child("fullName").getValue(String.class);
                        String _username = snapshot.child(_usernameLoginData).child("username").getValue(String.class);
                        String _email = snapshot.child(_usernameLoginData).child("email").getValue(String.class);
                        String _phone = snapshot.child(_usernameLoginData).child("phone").getValue(String.class);
                        String _password = snapshot.child(_usernameLoginData).child("password").getValue(String.class);
                        String _gender = snapshot.child(_usernameLoginData).child("gender").getValue(String.class);
                        String _birthDate = snapshot.child(_usernameLoginData).child("birthDate").getValue(String.class);

                        // create a session
                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        sessionManager.createLoginSession(_fullName, _username, _email, _phone, _password, _gender, _birthDate);

                        rlProgressBar.setVisibility(View.GONE);

                        // intent to dashboard activity
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//                        Toast.makeText(LoginActivity.this, _fullName, Toast.LENGTH_SHORT).show();
                        
                    } else {
                        rlProgressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    rlProgressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Data akun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                rlProgressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * CHECK INTERNET CONNECTION
     */
    private boolean isConnected(LoginActivity loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Silahkan hubungkan perangkat anda dengan internet")
                .setCancelable(false)
                .setPositiveButton("Hubungkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();
    }


    /**
     * LOGIN VALIDATION
     */
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