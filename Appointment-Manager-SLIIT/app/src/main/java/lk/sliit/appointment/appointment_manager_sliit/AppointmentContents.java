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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AppointmentContents extends AppCompatActivity {

    String chosedTime,chosedDate,lecturerName;
    String title,description;
    EditText editText_title,editText_description;
    User user;
    RequestQueue requestQueue;
    CustomRequest customRequest;
    String url = "http://quick-appointment.b2creations.net/createAppointment.php";
    /*SimpleDateFormat formatter_date = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter_time = new SimpleDateFormat("HH:mm");
    Date date,time;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_contents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");
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
        }
        else
        {

            requestQueue = Volley.newRequestQueue(this);

            HashMap<String,String> hashmap = new HashMap<String, String>();
            hashmap.put("studId",String.valueOf(user.getId()));
            hashmap.put("lectName",lecturerName);
            hashmap.put("date",chosedDate);
            hashmap.put("time",chosedTime);
            hashmap.put("title",title);
            hashmap.put("description",description);


            customRequest = new CustomRequest(Request.Method.POST, url, hashmap, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Toast.makeText(AppointmentContents.this, response.getString("success").toString(), Toast.LENGTH_SHORT).show();
                        editText_title.setText("");
                        editText_description.setText("");

                        redirectDashboard();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(customRequest);


        }


    }

    public void redirectDashboard(){
        Intent intent = new Intent(this,StudentDashBoard.class);
        intent.putExtra("user",user);
        this.startActivity(intent);

    }

}
