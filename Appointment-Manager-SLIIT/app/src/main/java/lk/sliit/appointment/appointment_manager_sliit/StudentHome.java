package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class StudentHome extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onAddAppointment(View view){

        Intent intent = new Intent(this, CreateAppointment.class);
        intent.putExtra("user",user);
        this.startActivity(intent);

    }

    public void btnLogout(View view){
        Intent intent = new Intent(this, Login.class);
        this.startActivity(intent);

    }

}
