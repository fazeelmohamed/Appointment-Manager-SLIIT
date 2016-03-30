package lk.sliit.appointment.appointment_manager_sliit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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

public class Lacturer_RequestDisplay_ListView extends AppCompatActivity {

    User user;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AppointmentAdapter appointmentAdapter;
    ListView listView;
    TextView tvw_appid;
    Button btnGoHome;
    private RequestQueue requestQueue;
    private static final String lec_listview_url = "http://quick-appointment.b2creations.net/lacturer_listview_update.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacturer__request_display__list_view);

        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");


        //app_id = (TextView)findViewById(R.id.txt_appID);
        //app_id.setVisibility(View.GONE);

        //row layout will be parse inside the ContactAdapter
        appointmentAdapter = new AppointmentAdapter(this,R.layout.row_lec_request_layout);
        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(appointmentAdapter);

        btnGoHome = (Button)findViewById(R.id.btnGoHome);

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(Lacturer_RequestDisplay_ListView.this, "You clicked YES",Toast.LENGTH_SHORT).show();
                        updateAppointmentTable(tvw_appid.getText().toString(),"1");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Toast.makeText(Lacturer_RequestDisplay_ListView.this, "You clicked NO",Toast.LENGTH_SHORT).show();
                        updateAppointmentTable(tvw_appid.getText().toString(), "2");
                        break;
                }
            }
        };

        AdapterView.OnItemClickListener itemList = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup viewGroup = (ViewGroup)view;
                TextView tvw_title = (TextView)viewGroup.findViewById(R.id.tvw_title);
                TextView tvw_stname = (TextView)viewGroup.findViewById(R.id.tvw_stName);
                TextView tvw_styear = (TextView)viewGroup.findViewById(R.id.tvw_stYear);
                TextView tvw_stsem = (TextView)viewGroup.findViewById(R.id.tvw_stSem);
                TextView tvw_disc = (TextView)viewGroup.findViewById(R.id.tvw_disc);
                         tvw_appid = (TextView)viewGroup.findViewById(R.id.tvw_appID);
                TextView tvw_date = (TextView)viewGroup.findViewById(R.id.tvw_date);
                TextView tvw_time = (TextView)viewGroup.findViewById(R.id.tvw_time);
                //Toast.makeText(Lacturer_RequestDisplay_ListView.this, textView.getText().toString(),Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(Lacturer_RequestDisplay_ListView.this);
                builder.setMessage("Title : "+tvw_title.getText().toString()+" \n" +
                        "Student name : "+tvw_stname.getText().toString()+" \n" +
                        "Year : "+tvw_styear.getText().toString()+" Semester : "+tvw_stsem.getText().toString() +"  \n" +
                        "Date : "+tvw_date.getText().toString()+" Time : "+tvw_time.getText().toString() +"  \n" +
                        "Description : "+tvw_disc.getText().toString()+" \n")
                        .setPositiveButton("Accept", dialogClickListener)
                        .setNegativeButton("Reject", dialogClickListener).show();

            }
        };
        listView.setOnItemClickListener(itemList);


        json_string = getIntent().getExtras().getString("json_string");
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("appointment");
            int count = 0;
            String app_id,student_id,date,time,title,description,st_name,st_email,st_year,st_sem;

            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                app_id = JO.getString("app_id");
                student_id = JO.getString("student_id");
                date = JO.getString("date");
                time = JO.getString("time");
                title = JO.getString("title");
                description = JO.getString("description");
                st_name = JO.getString("st_name");
                st_email = JO.getString("st_email");
                st_year = JO.getString("st_year");
                st_sem = JO.getString("st_sem");

                Appointment appointment = new Appointment(app_id,student_id,date,time,title,description,st_name,st_email,st_year,st_sem);
                appointmentAdapter.add(appointment);



                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void btnGoHomeClick(View view){
        Intent intent = new Intent(this,LecturerDashboard.class);
        intent.putExtra("user",user);
        this.startActivity(intent);
    }

    /*class ItemList implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup viewGroup = (ViewGroup)view;
            TextView textView = (TextView)viewGroup.findViewById(R.id.txt_title);
            Toast.makeText(Lacturer_RequestDisplay_ListView.this,textView.getText().toString(),Toast.LENGTH_SHORT).show();
        }
    }*/

    public void updateAppointmentTable(String app_id,String status){

        Map<String, String> map = new HashMap<String, String>();
        map.put("LecturerID",String.valueOf(user.getId()));
        map.put("app_id",app_id);
        map.put("status",status);

        //RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(this);
        // prepare the Request
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, lec_listview_url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("appointment");
                    reLoadThisClass(response);

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

    public void reLoadThisClass(JSONObject jObject){
        json_string = String.valueOf(jObject);
        Intent intent = new Intent(this,Lacturer_RequestDisplay_ListView.class);
        intent.putExtra("json_string",json_string);
        intent.putExtra("user",user);
        startActivity(intent);
    }

}
