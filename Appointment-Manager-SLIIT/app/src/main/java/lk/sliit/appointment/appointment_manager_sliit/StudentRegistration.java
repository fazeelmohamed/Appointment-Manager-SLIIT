package lk.sliit.appointment.appointment_manager_sliit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class StudentRegistration extends AppCompatActivity {

    String[] spinnerItemsYear;
    String[] spinnerItemSemester;
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Spinner spinnerYear, spinnerSemester;
    String name, email, password,confirmPassword;
    String year, semester;
    boolean status=true;


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
        confirmPassword = etConfirmPassword.getText().toString();
        year = spinnerYear.getSelectedItem().toString();
        semester = spinnerSemester.getSelectedItem().toString();

        boolean emailValidStatus;



        if(name.length() == 0){
            Toast.makeText(view.getContext(), "Please Provide Name.", Toast.LENGTH_SHORT).show();
            status =false;
        }

        if(email.length() == 0){
            Toast.makeText(view.getContext(), "Please Provide Email.", Toast.LENGTH_SHORT).show();
            status =false;
        }

        emailValidStatus = isValidEmail(email);

        if(!emailValidStatus){
            Toast.makeText(view.getContext(), "Invalid email address.", Toast.LENGTH_SHORT).show();
            status =false;
        }


        if(!password.equals(confirmPassword.toString()))
        {
            Toast.makeText(view.getContext(), "Password not match.", Toast.LENGTH_SHORT).show();
            status =false;
        }


        if(status) {
            BackgroundWorker bw = new BackgroundWorker(this);
            bw.execute(name, email, password, year, semester);

            Intent intent = new Intent(this,StudentHome.class);
            this.startActivity(intent);
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        boolean retult;
        if (target == null) {
            retult = false;
            return retult;
        } else {
            retult = android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            return retult;

        }
    }

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
       // AlertDialog alertDialog;

        BackgroundWorker(Context ct){
            context = ct;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            String reg_url = "http://quick-appointment.b2creations.net/student_register.php";

                try {
                    String name = params[0];
                    String email = params[1];
                    String password = params[2];
                    String year = params[3];
                    String semester = params[4];


                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_date = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                            + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8") + "&"
                            + URLEncoder.encode("semester", "UTF-8") + "=" + URLEncoder.encode(semester, "UTF-8");
                    bufferedWriter.write(post_date);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line+"");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return builder.toString().trim();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String result) {

            //alertDialog.setMessage(result);
            //alertDialog.show();

            Toast.makeText(context, "Account created successfully.", Toast.LENGTH_SHORT).show();
        }

    }



}
