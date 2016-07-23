package info.androidhive.materialdesign.adapter;


import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.LoginActivity;
import info.androidhive.materialdesign.model.Attendence_history;

public class Attendance_Adapter extends RecyclerView.Adapter<Attendance_Adapter.ViewHolder> {
    private ArrayList<Attendence_history> attendence;

    public Attendance_Adapter(FragmentActivity activity, ArrayList<Attendence_history> attendence) {
        this.attendence = attendence;
    }

    @Override
    public Attendance_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Attendance_Adapter.ViewHolder viewHolder, int i) {

            viewHolder.emp_id.setText(attendence.get(i).getEmployeeId());
            viewHolder.checkin_date.setText(attendence.get(i).getCheck_in());
            viewHolder.checkout_date.setText(attendence.get(i).getCheck_out());
            viewHolder.checkin_time.setText(attendence.get(i).getCheck_in_time());
            viewHolder.checkout_time.setText(attendence.get(i).getCheck_out_time());
            viewHolder.total_hour.setText(attendence.get(i).getTotal_work_hour());


//        viewHolder.emp_id.setText(attendence.get(i).getEmployeeId());
//        viewHolder.checkin_date.setText(attendence.get(i).getCheck_out());
//        viewHolder.checkout_date.setText(attendence.get(i).getTotal_work_hour());
//        viewHolder.checkin_time.setText(attendence.get(i).getEmployeeId());
//        viewHolder.checkout_time.setText(attendence.get(i).getEmployeeId());
    }

    @Override
    public int getItemCount() {
        return attendence.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView emp_id,checkin_time,checkout_time,checkin_date,checkout_date,total_hour;
        public ViewHolder(View view) {
            super(view);

            total_hour=(TextView)view.findViewById(R.id.total_hour);
            emp_id = (TextView)view.findViewById(R.id.emp_id);
            checkin_time = (TextView)view.findViewById(R.id.checkin_time);
            checkout_time = (TextView)view.findViewById(R.id.checkout_time);
            checkin_date = (TextView)view.findViewById(R.id.checkin_date);
            checkout_date = (TextView)view.findViewById(R.id.checkout_date);
        }
    }

}