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
    Button btnNext1;

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







        //Returns the enabled status for this view.
        //dualcamera1.isEnabled()  //return True if this view is enabled, false otherwise.

        //Indicates whether the view is currently in pressed state.
        //dualcamera1.isPressed() //return True if this view is enabled, false otherwise.


    }

    public void callAdminScheduleActivity(){
        Intent intent = new Intent(this, AdminSchedule.class);
        startActivity(intent);
    }
    public void btnNext1Click(View view){

        SubjectLecturer subLec = new SubjectLecturer();

        subLec.setTxtLecturer(txtLecturer.getText().toString());
        subLec.setTxtEmail(txtEmail.getText().toString());
        subLec.setTxtPassword(txtPassword.getText().toString());
        subLec.setTxtConfirmPassword(txtConfirmPassword.getText().toString());

        subLec.setCkbSPDC(ckbSPDC.isEnabled());
        subLec.setCkbHCI(ckbHCI.isEnabled());
        subLec.setCkbSE3(ckbSE3.isEnabled());
        subLec.setCkbSEP(ckbSEP.isEnabled());
        subLec.setCkbMAD(ckbMAD.isEnabled());
        subLec.setCkbSE2(ckbSE2.isEnabled());
        subLec.setCkbITP(ckbITP.isEnabled());
        subLec.setCkbITA(ckbITA.isEnabled());
        subLec.setCkbPS(ckbPS.isEnabled());
        subLec.setCkbSPD(ckbSPD.isEnabled());
        subLec.setCkbST2(ckbST2.isEnabled());
        subLec.setCkbST1(ckbST1.isEnabled());


        if(txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
            requestQueue = Volley.newRequestQueue(this);

            btnNext1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request = new StringRequest(Request.Method.POST, dashboard_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.names().get(0).equals("success")){
                                    Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_LONG).show();
                                    callAdminScheduleActivity();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Error "+jsonObject.getString("error"),Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("Lecturer",txtLecturer.getText().toString());
                            hashMap.put("Email",txtEmail.getText().toString());
                            hashMap.put("Password",txtPassword.getText().toString());

                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Password and Confirm Password doesn't match",Toast.LENGTH_LONG).show();
        }


    }

    /*class BackgroundDashboardTask extends AsyncTask<Object,Void,String> {
        Context context;
        AlertDialog alertDialog;
        public BackgroundDashboardTask(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            //alertDialog = new AlertDialog.Builder(context).create();
            //alertDialog.setTitle("Login Status");

        }

        @Override
        protected String doInBackground(Object... params) {
            String type =(String)params[0];
            String login_url = "http://quick-appointment.b2creations.net/login.php";
            if(type.equals("login")){
                try {
                    SubjectLecturer subLec = (SubjectLecturer)params[1];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    String post_data = URLEncoder.encode("lecturer", "UTF-8")+"="+URLEncoder.encode(subLec.getTxtLecturer(),"UTF-8")+"&"
                            +URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(subLec.getTxtEmail(), "UTF-8")+"&"
                            +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(subLec.getTxtPassword(), "UTF-8")+"&"
                            +URLEncoder.encode("isSPDC", "UTF-8")+"="+URLEncoder.encode((boolean)subLec.isCkbSPDC(), "UTF-8")+"&"
                            +URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(subLec.getTxtEmail(), "UTF-8")+"&"
                            +URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(subLec.getTxtEmail(), "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine())!= null){
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return  result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            LoginRedirect loginRedirect = new LoginRedirect();
            User user = loginRedirect.getJSON(result);

            if(user.isLogin_status() == true){

                Toast.makeText(context, "Login success", Toast.LENGTH_LONG).show();
                if(user.getUser_type()== 3) {
                    Intent intent = new Intent(context, AdminDashboard.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }else if(user.getUser_type()== 2) {
                    Intent intent = new Intent(context, LecturerDashboard.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                else if(user.getUser_type()== 1) {
                    Intent intent = new Intent(context, StudentDashBoard.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }else {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }



            //alertDialog.setMessage(result);
            //alertDialog.show();
        }
    }*/

}
