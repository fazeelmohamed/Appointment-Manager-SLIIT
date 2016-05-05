package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class StudentDashBoard extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");

        TextView txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        txtWelcome.setText("Welcome "+user.getName());
    }

    public void onAddAppointment(View view){
        Intent intent = new Intent(this, CreateAppointment.class);
        intent.putExtra("user",user);
        this.startActivity(intent);


    }

}
