package com.svs.myprojects.mymedicalrecords.doctor;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.doctor.appointment.AppointmentFragment;
import com.svs.myprojects.mymedicalrecords.doctor.arrange.WorkPlanFragment;
import com.svs.myprojects.mymedicalrecords.doctor.home.DoctorHomeFragment;
import com.svs.myprojects.mymedicalrecords.doctor.request.RequestFragment;

public class DoctorMainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        if(findViewById(R.id.doctor_main_container) != null) {
            DoctorHomeFragment sellerHomeFragment = new DoctorHomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, sellerHomeFragment).commit();
        }
        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        DoctorHomeFragment doctorHomeFragment = new DoctorHomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, doctorHomeFragment).commit();
                        break;
                    case R.id.menu_request:
                        RequestFragment requestFragment = new RequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, requestFragment).commit();
                        break;
                    case R.id.menu_appointment:
                        AppointmentFragment appointmentRecordFragment = new AppointmentFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, appointmentRecordFragment).commit();
                        break;
                    case R.id.menu_plan:
                        WorkPlanFragment workPlanFragment = new WorkPlanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, workPlanFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}
