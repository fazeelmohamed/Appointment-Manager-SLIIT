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
    String lect_name,dayOfWeek;
    RequestQueue requestQueue;
    test test;
    String url = "http://quick-appointment.b2creations.net/getFreeTimeSlot.php";
    String freeTime;
    RadioButton radioButton;
    RadioGroup radioGroup;
    String chosedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Pick Time");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_appointment_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        lect_name = intent.getStringExtra("lect_name");
        dayOfWeek = intent.getStringExtra("day_of_week");

        requestQueue = Volley.newRequestQueue(this);
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("lect_name",lect_name);
        hashMap.put("day_of_week",dayOfWeek);

        test = new test(Request.Method.POST, url, hashMap, new Response.Listener<JSONObject>() {
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
                                    chosedTime = st1.nextToken();  //tokenize selected time.Eg:11.30-12.00 => 11.30

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
        requestQueue.add(test);

    }

    public void btnClick(){
        Toast.makeText(this, "on confirm", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,AppointmentContents.class);
        intent.putExtra("test",chosedTime);
        this.startActivity(intent);


    }



    void getFreeTimeSlots(String pFreeTime){

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);


        String freeTimeWithoutAt = pFreeTime.substring(0,pFreeTime.length()-1);  //remove '@'

        StringTokenizer st = new StringTokenizer(freeTimeWithoutAt,"@");

        int counter = 0;

        while(st.hasMoreElements()){
            String radioButtonString = "";

            switch (st.nextToken())
            {
                case "1": radioButtonString = "8.30-9.00";break;
                case "2": radioButtonString = "9.00-9.30";break;
                case "3": radioButtonString = "9.30-10.00";break;
                case "4": radioButtonString = "10.00-10.30";break;
                case "5": radioButtonString = "10.30-11.00";break;
                case "6": radioButtonString = "11.00-11.30";break;
                case "7": radioButtonString = "11.30-12.00";break;
                case "8": radioButtonString = "12.00-12.30";break;
                case "9": radioButtonString = "12.30-13.00";break;
                case "10": radioButtonString = "13.00-13.30";break;
                case "11": radioButtonString = "13.30-14.00";break;
                case "12": radioButtonString = "14.00-14.30";break;
                case "13": radioButtonString = "14.30-15.00";break;
                case "14": radioButtonString = "15.00-15.30";break;
                case "15": radioButtonString = "15.30-16.00";break;
                case "16": radioButtonString = "16.00-16.30";break;
                case "17": radioButtonString = "16.30-17.00";break;
                case "18": radioButtonString = "17.00-17.30";break;
                case "19": radioButtonString = "17.30-18.00";break;
                case "20": radioButtonString = "18.00-18.30";break;
                case "21": radioButtonString = "18.30-19.00";break;
                case "22": radioButtonString = "19.00-19.30";break;

            }

            radioButton = new RadioButton(this);    //create radio buttons according to the lecturer free time
            radioButton.setId(counter);
            radioButton.setText(radioButtonString);
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(radioButton);

            counter++;
        }

    }




}
