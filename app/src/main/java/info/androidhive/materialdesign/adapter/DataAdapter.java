package info.androidhive.materialdesign.adapter;


import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Attendence_history;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Attendence_history> attendence;

    public DataAdapter(FragmentActivity activity, ArrayList<Attendence_history> attendence) {
        this.attendence = attendence;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_name.setText(attendence.get(i).getCheck_in());
        viewHolder.tv_version.setText(attendence.get(i).getCheck_out());
        viewHolder.tv_api_level.setText(attendence.get(i).getTotal_work_hour());
        viewHolder.tv_api_minor.setText(attendence.get(i).getCheck_in());
    }

    @Override
    public int getItemCount() {
        return attendence.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tv_api_level,tv_api_minor;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_version = (TextView)view.findViewById(R.id.tv_version);
            tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);
            tv_api_minor = (TextView)view.findViewById(R.id.tv_api_minor);
        }
    }

}