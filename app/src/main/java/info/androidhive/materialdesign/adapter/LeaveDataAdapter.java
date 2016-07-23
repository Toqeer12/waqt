package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;

import info.androidhive.materialdesign.activity.Leave_Detail;
import info.androidhive.materialdesign.model.Leave_Model;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class LeaveDataAdapter extends RecyclerView.Adapter<LeaveDataAdapter.ViewHolder> {
    private ArrayList<Leave_Model> attendence;
    Context context;

    public LeaveDataAdapter(FragmentActivity context, ArrayList<Leave_Model> attendence) {
        this.attendence = attendence;
        this.context=context;
    }

    @Override
    public LeaveDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leave_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


         holder.leavetype.setText(attendence.get(position).getTitleEn());
        holder.applied_status.setText(attendence.get(position).getLeaveStatus());
        holder.applieddate.setText(attendence.get(position).getApplyDate());
//        holder.tv_api_minor.setText(attendence.get(position).getApplied());



        if(attendence.get(position).getLeaveStatus().equalsIgnoreCase("Approved"))
        {
            holder.status_icon.setImageResource(R.drawable.waqt_approved);
        }
        else if (attendence.get(position).getLeaveStatus().equalsIgnoreCase("Rejected"))
        {
            holder.status_icon.setImageResource(R.drawable.waqt_rejected);
        }
        else if(attendence.get(position).getLeaveStatus().equalsIgnoreCase("Cancelled")){
            holder.status_icon.setImageResource(R.drawable.waqt_cancelled);
        }
        else if(attendence.get(position).getLeaveStatus().equalsIgnoreCase("InProcess"))
        {
            holder.status_icon.setImageResource(R.drawable.waqt_inprocess);
        }
        else
        { holder.status_icon.setImageResource(R.drawable.waqt_approved_grey);

        }
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
                Intent i=new Intent(context, Leave_Detail.class);
                i.putExtra("object",attendence);
                i.putExtra("position",position);

                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return attendence.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView leavetype,applieddate,applied_status;
        public ImageView status_icon;
        public CardView cardItem;
        public ViewHolder(View view) {
            super(view);
            cardItem=(CardView)view.findViewById(R.id.cardItem);
            leavetype = (TextView)view.findViewById(R.id.type);
            applieddate= (TextView)view.findViewById(R.id.appliedDate);
            applied_status= (TextView)view.findViewById(R.id.appliedstatus);
//            tv_api_minor = (TextView)view.findViewById(R.id.hd);
            status_icon=(ImageView) view.findViewById(R.id.status_icon);

        }


    }

}
