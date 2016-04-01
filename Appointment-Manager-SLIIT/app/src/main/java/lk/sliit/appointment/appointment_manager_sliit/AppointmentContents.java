package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AppointmentContents extends AppCompatActivity {

    String chosedTime,chosedDate,lecturerName;
    String title,description;
    EditText editText_title,editText_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_contents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        chosedTime = intent.getStringExtra("chosed_time");
        chosedDate = intent.getStringExtra("chosed_date");
        lecturerName = intent.getStringExtra("lect_name");



    }

    public void onAdd(View view){

        editText_title = (EditText)findViewById(R.id.etTitle);
        editText_description = (EditText)findViewById(R.id.etDescription);

        title = editText_title.getText().toString();
        description = editText_description.getText().toString();

        if((title.length() == 0) ||(description.length() == 0))
        {
            Toast.makeText(AppointmentContents.this,"Please enter necessary details.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(AppointmentContents.this,"Appointment created succesfully.",Toast.LENGTH_SHORT).show();
        }

    }

}
