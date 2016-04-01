package lk.sliit.appointment.appointment_manager_sliit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class CreateAppointment extends AppCompatActivity {

    String[] lect_name = new String[100];
    String[] spinner_subjects_load;
    Spinner spSubject,spLecturer;
    String chosedSubject;
    String chosedLectName;
    String chosedDateString;
    RequestQueue requestQueue;
    String url = "http://quick-appointment.b2creations.net/getLecturerNames.php";
    test stringRequest;
    String dayOfWeekString;
    Calendar currentDate,chosedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(currentDate.getTime());   //get current date


        spinner_subjects_load = new String[]{"SPDC", "HCI", "MAD", "SEIII", "SEP", "DAA", "ITP"};

        spSubject = (Spinner) findViewById(R.id.spSubject);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_subjects_load);
        spSubject.setAdapter(adapter1);

        requestQueue = Volley.newRequestQueue(this);

        spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                chosedSubject = spSubject.getSelectedItem().toString();

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("subj_name", chosedSubject);

                stringRequest = new test(Request.Method.POST, url, hashMap, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int count = 0;
                        try {

                            JSONArray jsonArray = response.getJSONArray("Lecturers");
                            while (count < jsonArray.length()) {
                                JSONObject jo = jsonArray.getJSONObject(count);
                                lect_name[count] = jo.getString("name");
                                count++;
                            }

                            spLecturer = (Spinner) findViewById(R.id.spLecturer);

                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CreateAppointment.this, android.R.layout.simple_spinner_item, lect_name);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spLecturer.setAdapter(adapter2);

                            chosedLectName = spLecturer.getSelectedItem().toString();
                            Toast.makeText(CreateAppointment.this, chosedLectName, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                requestQueue.add(stringRequest);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final CalendarView calendarView1 = (CalendarView)findViewById(R.id.calendarView1);

        calendarView1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                chosedDate = Calendar.getInstance();
                chosedDate.setTimeInMillis(view.getDate());
                int dayOfWeek = chosedDate.get(Calendar.DAY_OF_WEEK);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                chosedDateString = dateFormat.format(chosedDate.getTime());   //get selected date

                if(chosedDate.before(currentDate)){
                    Toast.makeText(CreateAppointment.this,"Invalid Date.",Toast.LENGTH_SHORT).show();
                }



                Toast.makeText(CreateAppointment.this,chosedDateString,Toast.LENGTH_SHORT).show();


                switch (dayOfWeek)
                {
                    case 1 :
                        dayOfWeekString = "sun";
                        break;
                    case 2 :
                        dayOfWeekString = "mon";
                        break;
                    case 3 :
                        dayOfWeekString = "tue";
                        break;
                    case 4 :
                        dayOfWeekString = "wed";
                        break;
                    case 5 :
                        dayOfWeekString = "thu";
                        break;
                    case 6 :
                        dayOfWeekString = "fri";
                        break;
                    case 7 :
                        dayOfWeekString = "sut";
                        break;
                }




            }

        });

    }

    public void onCheck(View view){

        Intent intent  = new Intent(this,ProvideAppointmentDetails_time.class);
        //  intent.putExtra("subj_name",chosedSubject);
        intent.putExtra("lect_name",chosedLectName);
        intent.putExtra("day_of_week",dayOfWeekString);
        intent.putExtra("chosed_date",chosedDateString);
        this.startActivity(intent);
    }
}
