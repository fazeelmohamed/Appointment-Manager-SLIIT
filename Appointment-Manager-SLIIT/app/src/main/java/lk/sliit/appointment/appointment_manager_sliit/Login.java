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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
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


public class Login extends AppCompatActivity {
    EditText txtEmail,txtPassword;
    String json_string;
    boolean bool_result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);


    }

    public void btnLoginClick(View view){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String type = "login";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(type,email,password);



    }

    public void btnRegisterClick(View view){
        Intent intent = new Intent(this,StudentRegistration.class);
        this.startActivity(intent);
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        public BackgroundTask(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            //alertDialog = new AlertDialog.Builder(context).create();
            //alertDialog.setTitle("Login Status");

        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://quick-appointment.b2creations.net/login.php";
            if(type.equals("login")){
                try {
                    String email = params[1];
                    String password = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("user_email", "UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&" +
                            URLEncoder.encode("user_password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
