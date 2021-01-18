package com.farrel.corporation.golekguru.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.farrel.corporation.golekguru.R;
import com.farrel.corporation.golekguru.databases.SessionManager;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView textView = findViewById(R.id.tv_coba);

        // get Session Data
        SessionManager sessionManager = new SessionManager(DashboardActivity.this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        String fullName = userDetails.get(SessionManager.KEY_FULL_NAME);
        String phone = userDetails.get(SessionManager.KEY_PHONE);

        textView.setText(fullName + "\n" + phone);
    }
}