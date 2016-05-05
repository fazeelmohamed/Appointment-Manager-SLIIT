package lk.sliit.appointment.appointment_manager_sliit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends AppCompatActivity {
    EditText txtLecturer,txtEmail,txtPassword,txtConfirmPassword;

    CheckBox ckbSPDC,ckbSE3,ckbHCI,ckbSEP,ckbMAD,ckbSE2,ckbITP,ckbITA,ckbPS,ckbSPD,
            ckbST2,ckbST1;
    Button btnNext1,btnLogout;
    String subjectStr = "";
    JSONObject jsonObject;
    private RequestQueue requestQueue;
    private static final String dashboard_url = "http://quick-appointment.b2creations.net/admin_dashboard.php";
    private StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        txtLecturer = (EditText)findViewById(R.id.etLecturer);
        txtEmail = (EditText)findViewById(R.id.etEmail);
        txtPassword = (EditText)findViewById(R.id.etPassword);
        txtConfirmPassword = (EditText)findViewById(R.id.etConfPass);

        ckbSPDC = (CheckBox)findViewById(R.id.ckbSPDC);
        ckbSE3 = (CheckBox)findViewById(R.id.ckbSE3);
        ckbHCI = (CheckBox)findViewById(R.id.ckbHCI);
        ckbSEP = (CheckBox)findViewById(R.id.ckbSEP);
        ckbMAD = (CheckBox)findViewById(R.id.ckbMAD);
        ckbSE2 = (CheckBox)findViewById(R.id.ckbSE2);
        ckbITP = (CheckBox)findViewById(R.id.ckbITP);
        ckbITA = (CheckBox)findViewById(R.id.ckbITA);
        ckbPS = (CheckBox)findViewById(R.id.ckbPS);
        ckbSPD = (CheckBox)findViewById(R.id.ckbSPD);
        ckbST2 = (CheckBox)findViewById(R.id.ckbST2);
        ckbST1 = (CheckBox)findViewById(R.id.ckbST1);

        btnNext1 = (Button)findViewById(R.id.btnNext1);
        btnLogout = (Button)findViewById(R.id.btnLogout);

    }

    public void callAdminScheduleActivity(String lid,String[] dayArray){
        Intent intent = new Intent(this, AdminSchedule.class);
        intent.putExtra("ID",lid);
        intent.putExtra("dayArray",dayArray);
        startActivity(intent);
    }

    public void btnLogoutClick(View view){
        //monday = "mon";
        Intent intent = new Intent(this,Login.class);
        this.startActivity(intent);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public void btnNext1Click(View view){

        String lacturer = txtLecturer.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();


        if(ckbSPDC.isChecked()){
            subjectStr+="1@";
        }
        if(ckbSE3.isChecked()){
            subjectStr+="2@";
        }
        if(ckbHCI.isChecked()){
            subjectStr+="3@";
        }
        if(ckbSEP.isChecked()){
            subjectStr+="4@";
        }
        if(ckbMAD.isChecked()){
            subjectStr+="5@";
        }
        if(ckbSE2.isChecked()){
            subjectStr+="6@";
        }
        if(ckbITP.isChecked()){
            subjectStr+="7@";
        }
        if(ckbITA.isChecked()){
            subjectStr+="8@";
        }
        if(ckbPS.isChecked()){
            subjectStr+="9@";
        }
        if(ckbSPD.isChecked()){
            subjectStr+="10@";
        }
        if(ckbST2.isChecked()){
            subjectStr+="11@";
        }
        if(ckbST1.isChecked()){
            subjectStr+="12@";
        }
        //Toast.makeText(getApplicationContext(),txtLecturer.getText().toString(),Toast.LENGTH_LONG).show();

        if(!lacturer.equals("") && !email.equals("") && !password.equals("") && !confirmPassword.equals("") && !subjectStr.equals("")){
          if(isValidEmail(email)) {
              if (txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {

                  Map<String, String> map = new HashMap<String, String>();
                  map.put("Lecturer", txtLecturer.getText().toString());
                  map.put("Email", txtEmail.getText().toString());
                  map.put("Password", txtPassword.getText().toString());
                  map.put("subjectStr", subjectStr);
                  subjectStr = "";//clear the str

                  //RequestQueue mRequestQueue = Volley.newRequestQueue(this);
                  requestQueue = Volley.newRequestQueue(this);
                  // prepare the Request
                  CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, dashboard_url, map, new Response.Listener<JSONObject>() {
                      @Override
                      public void onResponse(JSONObject response) {

                          try {

                              String lid = response.getString("lidvalue");
                              String[] dayArray = new String[7];
                              dayArray[0] = response.getString("mon");
                              dayArray[1] = response.getString("tue");
                              dayArray[2] = response.getString("wed");
                              dayArray[3] = response.getString("thu");
                              dayArray[4] = response.getString("fri");
                              dayArray[5] = response.getString("sat");
                              dayArray[6] = response.getString("sun");

                              if (response.names().get(0).equals("successes")) {
                                  Toast.makeText(getApplicationContext(), "SUCCESS " + response.getString("successes"), Toast.LENGTH_LONG).show();
                                  callAdminScheduleActivity(lid, dayArray);
                              } else {
                                  Toast.makeText(getApplicationContext(), "Error " + response.getString("error"), Toast.LENGTH_LONG).show();
                              }

                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  },
                          new Response.ErrorListener() {
                              @Override
                              public void onErrorResponse(VolleyError error) {
                                  //Log.d("Error.Response", );
                              }
                          });

                  // add it to the RequestQueue
                  requestQueue.add(jsObjRequest);
                  //AppController.getInstance().addToRequestQueue(jsObjRequest);

              } else {
                  Toast.makeText(getApplicationContext(), "Password and Confirm Password doesn't match", Toast.LENGTH_LONG).show();
              }
          }else {
              Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_LONG).show();
          }
        }else{
            Toast.makeText(getApplicationContext(),"Fields can not be null",Toast.LENGTH_LONG).show();
        }



    }



}
