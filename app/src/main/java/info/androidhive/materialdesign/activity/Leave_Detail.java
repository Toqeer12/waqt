package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.repackaged.retrofit_v1_9_0.retrofit.http.POST;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Leave_Model;

/**
 * Created by Arrowtec on 7/23/2016.
 */
public class Leave_Detail extends AppCompatActivity {
    private Toolbar mToolbar;
    TextView frmleave,toLeave,reason,quantity,termstart,termend,ispaid,leavestatus,unit_type,concur_type,procesdate,apply_date,title_en;
    DateFormat inputFormat2;
    DateFormat outputFormat2;
    ArrayList<Leave_Model> object;
    String fromLeave,toleavecheck,termstartcheck,termendcheck,paid,prodate,reasontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_detail);
        object= (ArrayList<Leave_Model>) getIntent().getSerializableExtra("object");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        inilizeText();
        Log.d("Get Index",""+object.get(getIntent().getIntExtra("position",0)).getLeaveStatus());


        mToolbar.setTitle("Atttendance System");
        mToolbar.setNavigationIcon(R.drawable.ic_reply_black_24dp);


            fromLeave=object.get(getIntent().getIntExtra("position",0)).getFromLeave().split("T")[0];



                    if(object.get(getIntent().getIntExtra("position",0)).getToLeave().equalsIgnoreCase("null"))
                    {toleavecheck="Not Found";

                    }
                    else
                    {toleavecheck=object.get(getIntent().getIntExtra("position",0)).getToLeave().split("T")[0];

                    }
                    if(object.get(getIntent().getIntExtra("position",0)).getTermStart().equalsIgnoreCase("null"))
                    {
                        termstartcheck="Not Found";
                    }
                    else
                    {
                        termstartcheck=object.get(getIntent().getIntExtra("position",0)).getTermStart().split("T")[0];
                    }
                    if(object.get(getIntent().getIntExtra("position",0)).getTermEnd().equalsIgnoreCase("null"))
                    {
                        termendcheck="Not Found";
                    }
                    else
                    {
                            termendcheck=object.get(getIntent().getIntExtra("position",0)).getTermEnd().split("T")[0];
                    }
                    if(object.get(getIntent().getIntExtra("position",0)).getIsPaid().equalsIgnoreCase("false"))
                    {

                        paid="Not Paid";
                    }
                    else {
                        paid = object.get(getIntent().getIntExtra("position", 0)).getIsPaid().split("T")[0];
                    }

                   if(object.get(getIntent().getIntExtra("position",0)).getProcessDate().equalsIgnoreCase("null"))
                    {
                    prodate="Not Found";
                   }
                 else
                 {
                   prodate = object.get(getIntent().getIntExtra("position",0)).getProcessDate().split("T")[0];
                  }
        if(object.get(getIntent().getIntExtra("position",0)).getReason().equalsIgnoreCase("null"))
        {
            reasontext="Not Found";
        }
        else

        {
            reasontext=object.get(getIntent().getIntExtra("position",0)).getReason();
        }

        frmleave.setText(fromLeave);
        toLeave.setText(toleavecheck);
        reason.setText(reasontext);
        quantity.setText(object.get(getIntent().getIntExtra("position",0)).getQunatity());
        termstart.setText(termstartcheck);
        termend.setText(termendcheck);
        ispaid.setText(paid);
        leavestatus.setText(object.get(getIntent().getIntExtra("position",0)).getLeaveStatus());
        unit_type.setText(object.get(getIntent().getIntExtra("position",0)).getUnitType());
        concur_type.setText(object.get(getIntent().getIntExtra("position",0)).getConcurrencyType());
        procesdate.setText(prodate);
        apply_date.setText(object.get(getIntent().getIntExtra("position",0)).getApplyDate());
        title_en.setText(object.get(getIntent().getIntExtra("position",0)).getTitleEn());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void inilizeText()
    {

        frmleave=(TextView)findViewById(R.id.fromleave);
        toLeave=(TextView)findViewById(R.id.toleave);
        reason=(TextView)findViewById(R.id.reason);
        quantity=(TextView)findViewById(R.id.quantity);
        termstart=(TextView)findViewById(R.id.term_start);
        termend=(TextView)findViewById(R.id.term_end);
        ispaid=(TextView)findViewById(R.id.paid);
        leavestatus=(TextView)findViewById(R.id.leavestatus);
        unit_type=(TextView)findViewById(R.id.unit_type);
        concur_type=(TextView)findViewById(R.id.con_type);
        title_en=(TextView)findViewById(R.id.title_en);
        apply_date=(TextView)findViewById(R.id.apply_date);
        procesdate=(TextView)findViewById(R.id.proces_date);
    }
}
