package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class AdminTimeAllocate extends AppCompatActivity {


    String lecture_id;
    String day;
    String[] dayArray = new String[7];

    /*
    * 08:30am - 09:00am = 1     09:00am - 09:30am = 2
    * 09:30am - 10:00am = 3     10:00am - 10:30am = 4
    * 10:30am - 11:00am = 5     11:00am - 11:30am = 6
    * 11:30am - 12:00am = 7     12:00am - 12:30am = 8
    * 12:30am - 01:00am = 9     01:00am - 01:30am = 10
    * 01:30am - 02:00am = 11     02:00am - 02:30am = 12
    * 02:30am - 03:00am = 13     03:00am - 03:30am = 14
    * 03:30am - 04:00am = 15     04:00am - 04:30am = 16
    * 04:30am - 05:00am = 17     05:00am - 05:30am = 18
    * 05:30am - 06:00am = 19     06:00am - 06:30am = 20
    * 06:30am - 07:00am = 21     07:00am - 07:30am = 22
    *
    * */
    CheckBox ckbTime1,ckbTime2,ckbTime3,ckbTime4,ckbTime5,ckbTime6,ckbTime7,ckbTime8,ckbTime9,ckbTime10,
            ckbTime11,ckbTime12,ckbTime13,ckbTime14,ckbTime15,ckbTime16,ckbTime17,ckbTime18,ckbTime19,ckbTime20,
            ckbTime21,ckbTime22;
    String timeStr  = "";
    RequestQueue requestQueue1;
    private static final String time_allocation_url = "http://quick-appointment.b2creations.net/admin_time_allocation.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_time_allocate);



        lecture_id = getIntent().getExtras().getString("ID");
        day = getIntent().getExtras().getString("DAY");
        dayArray = getIntent().getExtras().getStringArray("dayArray");


        ckbTime1 = (CheckBox)findViewById(R.id.ckbTime1);
        ckbTime2 = (CheckBox)findViewById(R.id.ckbTime2);
        ckbTime3 = (CheckBox)findViewById(R.id.ckbTime3);
        ckbTime4 = (CheckBox)findViewById(R.id.ckbTime4);
        ckbTime5 = (CheckBox)findViewById(R.id.ckbTime5);
        ckbTime6 = (CheckBox)findViewById(R.id.ckbTime6);
        ckbTime7 = (CheckBox)findViewById(R.id.ckbTime7);
        ckbTime8 = (CheckBox)findViewById(R.id.ckbTime8);
        ckbTime9 = (CheckBox)findViewById(R.id.ckbTime9);
        ckbTime10 = (CheckBox)findViewById(R.id.ckbTime10);
        ckbTime11 = (CheckBox)findViewById(R.id.ckbTime11);
        ckbTime12 = (CheckBox)findViewById(R.id.ckbTime12);
        ckbTime13 = (CheckBox)findViewById(R.id.ckbTime13);
        ckbTime14 = (CheckBox)findViewById(R.id.ckbTime14);
        ckbTime15 = (CheckBox)findViewById(R.id.ckbTime15);
        ckbTime16 = (CheckBox)findViewById(R.id.ckbTime16);
        ckbTime17 = (CheckBox)findViewById(R.id.ckbTime17);
        ckbTime18 = (CheckBox)findViewById(R.id.ckbTime18);
        ckbTime19 = (CheckBox)findViewById(R.id.ckbTime19);
        ckbTime20 = (CheckBox)findViewById(R.id.ckbTime20);
        ckbTime21 = (CheckBox)findViewById(R.id.ckbTime21);
        ckbTime22 = (CheckBox)findViewById(R.id.ckbTime22);

        //Toast.makeText(this, "LID = "+lecture_id + ", Day = " + day, Toast.LENGTH_LONG).show();
    }

    public void callAdminScheduleActivity2(String lid,String[] dayArray){
        Intent intent = new Intent(this, AdminSchedule.class);
        intent.putExtra("ID", lid);
        intent.putExtra("dayArray",dayArray);
        startActivity(intent);
    }

    public void btnNext2Click(View view){


        Toast.makeText(getApplicationContext(),"Btn clicked okay",Toast.LENGTH_SHORT).show();


        if(ckbTime1.isChecked()) timeStr +="1@";
        if(ckbTime2.isChecked()) timeStr +="2@";
        if(ckbTime3.isChecked()) timeStr +="3@";
        if(ckbTime4.isChecked()) timeStr +="4@";
        if(ckbTime5.isChecked()) timeStr +="5@";
        if(ckbTime6.isChecked()) timeStr +="6@";
        if(ckbTime7.isChecked()) timeStr +="7@";
        if(ckbTime8.isChecked()) timeStr +="8@";
        if(ckbTime9.isChecked()) timeStr +="9@";
        if(ckbTime10.isChecked()) timeStr +="10@";
        if(ckbTime11.isChecked()) timeStr +="11@";
        if(ckbTime12.isChecked()) timeStr +="12@";
        if(ckbTime13.isChecked()) timeStr +="13@";
        if(ckbTime14.isChecked()) timeStr +="14@";
        if(ckbTime15.isChecked()) timeStr +="15@";
        if(ckbTime16.isChecked()) timeStr +="16@";
        if(ckbTime17.isChecked()) timeStr +="17@";
        if(ckbTime18.isChecked()) timeStr +="18@";
        if(ckbTime19.isChecked()) timeStr +="19@";
        if(ckbTime20.isChecked()) timeStr +="20@";
        if(ckbTime21.isChecked()) timeStr +="21@";
        if(ckbTime22.isChecked()) timeStr +="22@";



        if(!timeStr .equals("")) {
                requestQueue1 = Volley.newRequestQueue(this);

            Toast.makeText(getApplicationContext(),"Timestring is:"+timeStr,Toast.LENGTH_SHORT).show();

            Map<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("lecturerID",lecture_id);
            hashMap.put("timeStr",timeStr );
            hashMap.put("dayValue",day );
            timeStr  = "";//clear the str

            // prepare the Request
                //JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, time_allocation_url,
                  CustomRequest request1 = new CustomRequest(Request.Method.POST, time_allocation_url,hashMap,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    String lid = response.getString("lecidvalue");
                                    /*String[] dayArray = new String[7];
                                    dayArray[0] = response.getString("mon");dayArray[1] = response.getString("tue");
                                    dayArray[2] = response.getString("wed");dayArray[3] = response.getString("thu");
                                    dayArray[4] = response.getString("fri");dayArray[5] = response.getString("sat");
                                    dayArray[6] = response.getString("sun");*/

                                    //if(response.names().get(0).equals("successmsg")){
                                        Toast.makeText(getApplicationContext(),"SUCCESS "+response.getString("successmsg"),Toast.LENGTH_LONG).show();
                                        callAdminScheduleActivity2(lid,dayArray);
                                   /* }else{
                                        Toast.makeText(getApplicationContext(),"Error "+response.getString("error"),Toast.LENGTH_LONG).show();
                                    }*/

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
                /*{
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("lecturerID",lecture_id);
                        hashMap.put("timeStr",timeStr );
                        hashMap.put("dayValue",day );
                        timeStr  = "";//clear the str
                        return hashMap;
                    }
                };*/

                // add it to the RequestQueue
                requestQueue1.add(request1);


        }else{
            Toast.makeText(getApplicationContext(),"There should be atleast one checked check box",Toast.LENGTH_LONG).show();
        }



    }


}
