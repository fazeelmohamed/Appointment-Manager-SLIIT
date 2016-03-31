package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

public class LecturerDashboard extends AppCompatActivity {
    User user;
    Button btnRequest;
    JSONObject jsonObject;
    private RequestQueue requestQueue;
    JSONArray jsonArray;
    //TableLayout tableLayout;
    String json_string;
    TextView tvwWelcome;
    private static final String lec_dashboard_url = "http://quick-appointment.b2creations.net/lacturer_dashboard.php";
    private static final String lec_appointment_url = "http://quick-appointment.b2creations.net/lecturer_appointment_details.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_dashboard);

        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");

        btnRequest = (Button)findViewById(R.id.btnRequest);
        //tableLayout = (TableLayout) findViewById(R.id.myTableLayout);

        tvwWelcome = (TextView)findViewById(R.id.tvw_Welcome);
        tvwWelcome.setText("Welcome "+user.getName());
        //Toast.makeText(this, "ID :"+ user.getId(), Toast.LENGTH_LONG).show();
        /*ArrayList<Double> debtList = datasource.debtList;
        ArrayList<Double> feeList = datasource.feeList;
        TableLayout table = (TableLayout) findViewById(R.id.myTableLayout);
        for(int i=0;i<debtList.size();i++)
        {
            TableRow row=new TableRow(this);
            double debt = debtList.get(i);
            double fee = feeList.get(i);
            TextView tvDebt=new TextView(this);
            tvDebt.setText(""+debt);
            TextView tvFee=new TextView(this);
            tvFee.setText(""+fee);
            row.addView(tvDebt);
            row.addView(tvFee);
            table.addView(row);
        }*/
    }


    public void btnRequestClick(View view){

        Map<String, String> map = new HashMap<String, String>();
        map.put("LecturerID",String.valueOf(user.getId()));

        //RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(this);
        // prepare the Request
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, lec_dashboard_url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("appointment");
                    callRequest(response);

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

    public void callRequest(JSONObject jObject){
        json_string = String.valueOf(jObject);
        Intent intent = new Intent(this,Lacturer_RequestDisplay_ListView.class);
        intent.putExtra("json_string",json_string);
        intent.putExtra("user",user);
        startActivity(intent);
    }


    public void btnAppointmentClick(View view){

        Map<String, String> map = new HashMap<String, String>();
        map.put("LecturerID",String.valueOf(user.getId()));

        //RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(this);
        // prepare the Request
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, lec_appointment_url, map, new Response.Listener<JSONObject>() {
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
        Intent intent = new Intent(this,LecturerAppointmentDetails.class);
        intent.putExtra("json_string",json_string);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void btnLecLogoutClick(View view){
        //monday = "mon";
        Intent intent = new Intent(this,Login.class);
        this.startActivity(intent);
    }




}
