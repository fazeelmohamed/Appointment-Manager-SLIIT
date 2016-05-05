package lk.sliit.appointment.appointment_manager_sliit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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
import java.util.StringTokenizer;

public class ProvideAppointmentDetails_time extends AppCompatActivity {
    String lect_name,dayOfWeek,chosedDate;
    RequestQueue requestQueue;
    CustomRequest customRequest;
    String url = "http://quick-appointment.b2creations.net/getFreeTimeSlot.php";
    String freeTime;
    RadioButton radioButton;
    RadioGroup radioGroup;
    String chosedTime;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Pick Time");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_appointment_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");
        lect_name = intent.getStringExtra("lect_name");
        dayOfWeek = intent.getStringExtra("day_of_week");
        chosedDate = intent.getStringExtra("chosed_date");


        requestQueue = Volley.newRequestQueue(this);
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("lect_name",lect_name);
        hashMap.put("day_of_week",dayOfWeek);

        customRequest = new CustomRequest(Request.Method.POST, url, hashMap, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int count = 0;
                try{
                    JSONArray jsonArray = response.getJSONArray("freeTimeSlot");
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        freeTime = jo.getString("free_time");

                        count++;
                    }

                    getFreeTimeSlots(freeTime);

                    //radio button click event
                    radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
                    Button button = (Button)findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {

                            if(radioGroup.getCheckedRadioButtonId() == -1)
                            {
                                Toast.makeText(ProvideAppointmentDetails_time.this, "Please select your convenient time", Toast.LENGTH_SHORT).
                                        show();
                            }
                            else
                            {
                                int selectedId = radioGroup.getCheckedRadioButtonId();
                                radioButton = (RadioButton)findViewById(selectedId);

                                StringTokenizer st1 = new StringTokenizer((String) radioButton.getText(),"-");
                                chosedTime = st1.nextToken();  //tokenize selected time.Eg:11:30 AM-12:00 PM => 11:30 AM

                                btnClick();

                            }



                        }
                    });

                }catch (JSONException e){
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

    public void btnClick(){

        Intent intent = new Intent(this,AppointmentContents.class);
        intent.putExtra("chosed_time",chosedTime);
        intent.putExtra("lect_name", lect_name);
        intent.putExtra("chosed_date",chosedDate);
        intent.putExtra("user",user);
        this.startActivity(intent);


    }



    void getFreeTimeSlots(String pFreeTime){

        //layout for radio buttons
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);


        String freeTimeWithoutAt = pFreeTime.substring(0,pFreeTime.length()-1);  //remove '@'

        StringTokenizer st = new StringTokenizer(freeTimeWithoutAt,"@");

        int counter = 0;

        while(st.hasMoreElements()){
            String radioButtonString = "";

            switch (st.nextToken())
            {
                case "1": radioButtonString = "08:30 AM-09:00 AM";break;
                case "2": radioButtonString = "09:00 AM-09:30 AM";break;
                case "3": radioButtonString = "09:30 AM-10:00 AM";break;
                case "4": radioButtonString = "10:00 AM-10:30 AM";break;
                case "5": radioButtonString = "10:30 AM-11:00 AM";break;
                case "6": radioButtonString = "11:00 AM-11:30 AM";break;
                case "7": radioButtonString = "11:30 AM-12:00 PM";break;
                case "8": radioButtonString = "12:00 PM-12:30 PM";break;
                case "9": radioButtonString = "12:30 PM-01:00 PM";break;
                case "10": radioButtonString = "01:00 PM-01:30 PM";break;
                case "11": radioButtonString = "01:30 PM-102:00 PM";break;
                case "12": radioButtonString = "02:00 PM-02:30 PM";break;
                case "13": radioButtonString = "02:30 PM-03:00 PM";break;
                case "14": radioButtonString = "03:00 PM-03:30 PM";break;
                case "15": radioButtonString = "03:30 PM-04:00 PM";break;
                case "16": radioButtonString = "04:00 PM-04:30 PM";break;
                case "17": radioButtonString = "04:30 PM-15:00 PM";break;
                case "18": radioButtonString = "05:00 PM-05:30 PM";break;
                case "19": radioButtonString = "05:30 PM-06:00 PM";break;
                case "20": radioButtonString = "06:00 PM-06:30 PM";break;
                case "21": radioButtonString = "06:30 PM-07:00 PM";break;
                case "22": radioButtonString = "07:00 PM-07:30 PM";break;

            }

            radioButton = new RadioButton(this);    //create radio buttons according to the lecturer free time
            radioButton.setId(counter);
            radioButton.setText(radioButtonString);
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(radioButton);

            counter++;
        }

    }




}
