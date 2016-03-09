package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class StudentRegistration extends AppCompatActivity {

    String[] spinnerItemsYear;
    String[] spinnerItemSemester;
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Spinner spinnerYear, spinnerSemester;
    String name, email, password,confirmPassword;
    String type;
    String year, semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.spinnerItemsYear = new String[]{"1st Year","2nd Year","3rd Year","4th Year"};
        Spinner s1 = (Spinner)findViewById(R.id.spinnerYear);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItemsYear);
        s1.setAdapter(adapter1);

        this.spinnerItemSemester = new String[]{"1st Semester","2nd Semester"};
        Spinner s2 = (Spinner)findViewById(R.id.spinnerSemester);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItemSemester);
        s2.setAdapter(adapter2);

        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        spinnerYear = (Spinner)findViewById(R.id.spinnerYear);
        spinnerSemester = (Spinner)findViewById(R.id.spinnerSemester);




    }

    public void OnRegister(View view){

        name = etName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        //confirmPassword = etConfirmPassword.getText().toString();
        year = spinnerYear.getSelectedItem().toString();
        semester = spinnerSemester.getSelectedItem().toString();


        type = "student_register";

        BackgroundWorker bw = new BackgroundWorker(this);
        bw.execute(type,name, email, password, year, semester);

        Intent intent = new Intent(this,StudentHome.class);
        this.startActivity(intent);

    }



}
