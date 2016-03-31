package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminSchedule extends AppCompatActivity {
    RequestQueue requestQueue;
    private static final String schedule_url = "http://quick-appointment.b2creations.net/admin_dashboard.php";
    Button btnMon,btnTue,btnWed,btnThu,btnFri,btnSat,btnSun,btnHome;
    //String monday="",tuesday="",wednesday="",thursday="",friday="",saturday="",sunday="";
    String subjectStr = "";
    String lecture_id;
    String[] dayArray = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule);



        btnMon =(Button) findViewById(R.id.btnMon);
        btnTue =(Button) findViewById(R.id.btnTue);
        btnWed =(Button) findViewById(R.id.btnWed);
        btnThu =(Button) findViewById(R.id.btnThu);
        btnFri =(Button) findViewById(R.id.btnFri);
        btnSat =(Button) findViewById(R.id.btnSat);
        btnSun =(Button) findViewById(R.id.btnSun);


        /*SubjectLecturer subjectLecturer = (SubjectLecturer) getIntent().getSerializableExtra("subLecKey");
        Toast.makeText(this,subjectLecturer.getTxtLecturer(),Toast.LENGTH_LONG).show();*/
        lecture_id = getIntent().getExtras().getString("ID");
        dayArray = getIntent().getExtras().getStringArray("dayArray");
        //Toast.makeText(this,day[6],Toast.LENGTH_LONG).show();


        if(dayArray[0].equals("mon")){
            btnMon.setVisibility(View.GONE);
        }if(dayArray[1].equals("tue")){
            btnTue.setVisibility(View.GONE);
        }if(dayArray[2].equals("wed")){
            btnWed.setVisibility(View.GONE);
        }if(dayArray[3].equals("thu")){
            btnThu.setVisibility(View.GONE);
        }if(dayArray[4].equals("fri")){
            btnFri.setVisibility(View.GONE);
        }if(dayArray[5].equals("sat")){
            btnSat.setVisibility(View.GONE);
        }if(dayArray[6].equals("sun")){
            btnSun.setVisibility(View.GONE);
        }

        /*if(dayArray[0].equals("mon")&&dayArray[1].equals("tue")&&dayArray[2].equals("wed")
                &&dayArray[3].equals("thu")&&dayArray[4].equals("fri")&&dayArray[5].equals("sat")&&dayArray[6].equals("sun")){
        }else{
            btnHome.setVisibility(View.GONE);
        }*/

        /*if(monday.equals("mon")){
            btnMon.setVisibility(View.GONE);
        }if(tuesday.equals("tue")){
            btnTue.setVisibility(View.GONE);
        }if(wednesday.equals("wed")){
            btnWed.setVisibility(View.GONE);
        }if(thursday.equals("thu")){
            btnThu.setVisibility(View.GONE);
        }if(friday.equals("fri")){
            btnFri.setVisibility(View.GONE);
        }if(saturday.equals("sat")){
            btnSat.setVisibility(View.GONE);
        }if(sunday.equals("sun")){
            btnSun.setVisibility(View.GONE);
        }*/

    }

    public void btnHomeClick(View view){
        //monday = "mon";
        Intent intent = new Intent(this,AdminDashboard.class);
        dayArray[0]="";dayArray[1]="";dayArray[2]="";dayArray[3]="";
        dayArray[4]="";dayArray[5]="";dayArray[6]="";
        this.startActivity(intent);
    }

    public void btnMonClick(View view){
        //monday = "mon";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        intent.putExtra("DAY","mon");
        dayArray[0]="mon";
        intent.putExtra("dayArray",dayArray);
        this.startActivity(intent);
    }

    public void btnTueClick(View view){
        //tuesday = "tue";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        dayArray[1]="tue";
        intent.putExtra("dayArray",dayArray);
        intent.putExtra("DAY","tue");
        this.startActivity(intent);
    }

    public void btnWedClick(View view){
        //wednesday = "wed";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        intent.putExtra("DAY","wed");
        dayArray[2]="wed";
        intent.putExtra("dayArray",dayArray);
        this.startActivity(intent);
    }

    public void btnThuClick(View view){
        //thursday = "thu";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        intent.putExtra("DAY","thu");
        dayArray[3]="thu";
        intent.putExtra("dayArray",dayArray);
        this.startActivity(intent);
    }

    public void btnFriClick(View view){
        //friday = "fri";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        intent.putExtra("DAY","fri");
        dayArray[4]="fri";
        intent.putExtra("dayArray",dayArray);
        this.startActivity(intent);
    }

    public void btnSatClick(View view){
        //saturday = "sat";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        intent.putExtra("DAY","sat");
        dayArray[5]="sat";
        intent.putExtra("dayArray",dayArray);
        this.startActivity(intent);
    }

    public void btnSunClick(View view){
        //sunday = "sun";
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        intent.putExtra("ID",lecture_id);
        intent.putExtra("DAY","sun");
        dayArray[6]="sun";
        intent.putExtra("dayArray",dayArray);
        this.startActivity(intent);
    }


    /*public void postRequest(){
        requestQueue = Volley.newRequestQueue(this);
        // prepare the Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, schedule_url,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            String lid = response.getString("lid");

                            if(response.names().get(0).equals("successes")){
                                Toast.makeText(getApplicationContext(),"SUCCESS "+response.getString("successes"),Toast.LENGTH_LONG).show();
                                //callAdminScheduleActivity(lid);
                            }else{
                                Toast.makeText(getApplicationContext(),"Error "+response.getString("error"),Toast.LENGTH_LONG).show();
                            }

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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();

                hashMap.put("subjectStr",subjectStr);
                subjectStr = "";//clear the str
                return hashMap;
            }
        };

        // add it to the RequestQueue
        requestQueue.add(request);
    }*/




}
