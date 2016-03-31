package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AdminSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule);
        //View b = findViewById(R.id.btnMon);
        //b.setVisibility(View.GONE);


    }

    public void btnMonClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }

    public void btnTueClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }

    public void btnWedClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }

    public void btnThuClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }

    public void btnFriClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }

    public void btnSatClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }

    public void btnSunClick(View view){
        Intent intent = new Intent(this,AdminTimeAllocate.class);
        this.startActivity(intent);
    }


}
