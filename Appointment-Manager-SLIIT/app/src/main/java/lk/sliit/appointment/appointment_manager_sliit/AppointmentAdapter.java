package lk.sliit.appointment.appointment_manager_sliit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User G on 3/29/2016.
 */
public class AppointmentAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public AppointmentAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Appointment object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_lec_request_layout,parent,false);
            contactHolder = new ContactHolder();
            contactHolder.title = (TextView) row.findViewById(R.id.tvw_title);

            contactHolder.app_id = (TextView) row.findViewById(R.id.tvw_appID);
            contactHolder.app_id.setVisibility(View.GONE);
            contactHolder.date = (TextView) row.findViewById(R.id.tvw_date);
            contactHolder.date.setVisibility(View.GONE);
            contactHolder.time = (TextView) row.findViewById(R.id.tvw_time);
            contactHolder.time.setVisibility(View.GONE);
            contactHolder.disc = (TextView) row.findViewById(R.id.tvw_disc);
            contactHolder.disc.setVisibility(View.GONE);
            contactHolder.stYear = (TextView) row.findViewById(R.id.tvw_stYear);
            contactHolder.stYear.setVisibility(View.GONE);
            contactHolder.stname = (TextView) row.findViewById(R.id.tvw_stName);
            contactHolder.stname.setVisibility(View.GONE);
            contactHolder.stSem = (TextView) row.findViewById(R.id.tvw_stSem);
            contactHolder.stSem.setVisibility(View.GONE);

            row.setTag(contactHolder);
        }else {
            contactHolder = (ContactHolder)row.getTag();
        }
        Appointment appointment = (Appointment)this.getItem(position);
        contactHolder.title.setText(appointment.getTitle());
        contactHolder.app_id.setText(appointment.getApp_id());
        contactHolder.date.setText(appointment.getDate());
        contactHolder.time.setText(appointment.getTime());
        contactHolder.disc.setText(appointment.getDescription());
        contactHolder.stYear.setText(appointment.getSt_year());
        contactHolder.stname.setText(appointment.getSt_name());
        contactHolder.stSem.setText(appointment.getApp_id());


        return row;
    }

    static class ContactHolder{

        TextView app_id,title,disc,stname,stYear,stSem,date,time;
    }
}
