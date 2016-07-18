package info.androidhive.materialdesign.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;

import info.androidhive.materialdesign.model.Leave_Model;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class LeaveDataAdapter extends RecyclerView.Adapter<LeaveDataAdapter.ViewHolder> {
    private ArrayList<Leave_Model> attendence;

    public LeaveDataAdapter(FragmentActivity activity, ArrayList<Leave_Model> attendence) {
        this.attendence = attendence;
    }

    @Override
    public LeaveDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leave_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(attendence.get(position).getStartLeave());
        holder.tv_version.setText(attendence.get(position).getEndLeave());
        holder.tv_api_level.setText(attendence.get(position).getApplied());
        holder.tv_api_minor.setText(attendence.get(position).getApplied());
    }


    @Override
    public int getItemCount() {
        return attendence.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tv_api_level,tv_api_minor;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.from);
            tv_version = (TextView)view.findViewById(R.id.to);
            tv_api_level = (TextView)view.findViewById(R.id.applied);
            tv_api_minor = (TextView)view.findViewById(R.id.hd);
        }
    }

}
