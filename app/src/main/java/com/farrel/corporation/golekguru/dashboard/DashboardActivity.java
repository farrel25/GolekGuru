package com.farrel.corporation.golekguru.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.farrel.corporation.golekguru.R;
import com.farrel.corporation.golekguru.databases.SessionManager;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        TextView textView = findViewById(R.id.tv_coba);
        // get Session Data
//        SessionManager sessionManager = new SessionManager(DashboardActivity.this);
//        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();
//        String fullName = userDetails.get(SessionManager.KEY_FULL_NAME);
//        String phone = userDetails.get(SessionManager.KEY_PHONE);
//        textView.setText(fullName + "\n" + phone);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.bottom_nav_course:
                        fragment = new CourseFragment();
                        break;
                    case R.id.bottom_nav_schedule:
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }
}