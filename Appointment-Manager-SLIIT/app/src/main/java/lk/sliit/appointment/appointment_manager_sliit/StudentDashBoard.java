package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentDashBoard extends AppCompatActivity {

    User user;
    Button btnRequest;
    JSONObject jsonObject;
    private RequestQueue requestQueue;
    JSONArray jsonArray;
    //TableLayout tableLayout;
    String json_string;
    TextView tvwWelcome;
    private static final String student_appointment_list_url = "http://quick-appointment.b2creations.net/student_appointment_list.php";
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

    public void btnLogout(View view){
        Intent intent = new Intent(this, Login.class);
        this.startActivity(intent);

    }

    public void btnViewAppointment(View view){

        Map<String, String> map = new HashMap<String, String>();
        map.put("studentID", String.valueOf(user.getId()));

        //RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(this);
        // prepare the Request
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, student_appointment_list_url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("appointment");
                    callApoointment(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", );
                    }
                });

        // add it to the RequestQueue
        requestQueue.add(jsObjRequest);

    }

    public void callApoointment(JSONObject jObject){
        json_string = String.valueOf(jObject);
        Intent intent = new Intent(this,student_viewAppointments.class);
        intent.putExtra("json_string",json_string);
        intent.putExtra("user",user);
        startActivity(intent);
    }

}
