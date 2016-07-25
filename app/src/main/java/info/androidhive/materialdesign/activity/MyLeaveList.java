package info.androidhive.materialdesign.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import connection.ConnectivityReceiver;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Attendance_Adapter;
import info.androidhive.materialdesign.adapter.LeaveDataAdapter;
import info.androidhive.materialdesign.model.Attendence_history;
import info.androidhive.materialdesign.model.Leave_Model;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class MyLeaveList extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Leave_Model> leaveobject;
    LeaveDataAdapter adapter;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefsLeaveHistory";
    SharedPreferences.Editor editor;
    RelativeLayout layout;
    ProgressDialog progressDialog;
    String[] Applydate;
    String[] checkout_output;
    DateFormat inputFormat2;
    DateFormat outputFormat2;
    String timeApplyDate_convt;
    String fromLeave;
    String toLeave;
    String Qunatity;
    String  reason;

    String TermStart;
    String TermEnd;
    String ProcessDate;
    String ConcurrencyType;
    String UnitType;
    String isPaid;

    String LeaveTitle,ApplyDate,Status;
    public MyLeaveList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        leaveobject=new ArrayList<Leave_Model>() ;
        progressDialog = new ProgressDialog(getActivity());
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.fragment_leave_list, container, false);
        layout=(RelativeLayout)rootView.findViewById(R.id.layout_leave);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view2);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading....");
        editor = sharedpreferences.edit();
        if(ConnectivityReceiver.isConnected())
        {
            progressDialog.show();
            LeaveHistory(MainActivity.EmployeeId);
        }
        else {

            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            //  registerUser(password, macAddress);
                            //  onLoginSuccess();
                            // onLoginFailed();
                            OFFLINE_ATTENDACE();
                            progressDialog.dismiss();

                        }
                    }, 2000);



        }



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void OFFLINE_ATTENDACE()
    {
        String json_array = sharedpreferences.getString("LeaveHistoryJsonArray", null);
        Log.d("Response",""+json_array);
        if (json_array==null) {

            Snack_Bar("Record Not Found");
            progressDialog.dismiss();
        }
        else
        {
            try {



                JSONArray jsoArray = new JSONArray(json_array);
                for (int i = 0; i < jsoArray.length(); i++) {
                    JSONObject person = (JSONObject) jsoArray
                            .get(i);
                    LeaveTitle = person.getString("TitleEn");
                    ApplyDate = person.getString("ApplyDate");
                    Status = person.getString("LeaveStatus");
                    fromLeave=person.getString("fromLeave");
                    toLeave=person.getString("toLeave");
                    reason=person.getString("reason");
                    TermStart=person.getString("TermStart");
                    TermEnd=person.getString("TermEnd");
                    Qunatity=person.getString("Qunatity");
                    ProcessDate=person.getString("ProcessDate");
                    UnitType=person.getString("UnitType");
                    ConcurrencyType=person.getString("ConcurrencyType");
                    isPaid=person.getString("isPaid");
                    Applydate = ApplyDate.split("T");

                    inputFormat2 = new SimpleDateFormat("HH:mm:ss");
                    outputFormat2 = new SimpleDateFormat("KK:mm a");


                    Log.d("ResponseAdapter", Applydate[0]);
                    try {
                        timeApplyDate_convt = outputFormat2.format(inputFormat2.parse(Applydate[1].substring(0, 8)));







                    } catch (Exception ex) {

                    }
                   // leaveobject.add(new Leave_Model(Applydate[0],timeApplyDate_convt,Status,LeaveTitle));
                    leaveobject.add(new Leave_Model(fromLeave, toLeave, Qunatity,  reason,  timeApplyDate_convt, isPaid, Status,  Applydate[0],  TermStart,  TermEnd,  ProcessDate,  LeaveTitle,  ConcurrencyType,  UnitType));

                    adapter = new LeaveDataAdapter(getActivity(), leaveobject);
                    recyclerView.setAdapter(adapter);


                    Log.d("Response", LeaveTitle);
                }
                //
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void LeaveHistory(final String Id){


        String url ="http://schoolhrms.mydreamapps.com/api/testapi/GetLeaveHistory?id=23";
        Log.d("URL",url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://schoolhrms.mydreamapps.com/api/testapi/GetLeaveHistory?id="+Id,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //  progressDialog.show();
                        try {
                            Log.d("Response", response.toString());
                            JSONArray jsoArray = new JSONArray(response);
                            Log.d("Response",""+jsoArray.length());
                            if(jsoArray.length()==0)
                            {
                                Log.d("Response", response.toString());

                                Snack_Bar("Record Not Found");
                                progressDialog.hide();

                            }
                            else
                            {
                                for (int i = 0; i < jsoArray.length(); i++) {
                                    JSONObject person = (JSONObject) jsoArray
                                            .get(i);

                                    editor.remove("LeaveHistoryJsonArray");
                                    editor.putString("LeaveHistoryJsonArray", response.toString());
                                    editor.commit();

                                    LeaveTitle = person.getString("TitleEn");
                                    ApplyDate = person.getString("ApplyDate");
                                    Status = person.getString("LeaveStatus");
                                    fromLeave=person.getString("fromLeave");
                                    toLeave=person.getString("toLeave");
                                    reason=person.getString("reason");
                                    TermStart=person.getString("TermStart");
                                    TermEnd=person.getString("TermEnd");
                                    Qunatity=person.getString("Qunatity");
                                    ProcessDate=person.getString("ProcessDate");
                                    UnitType=person.getString("UnitType");
                                    ConcurrencyType=person.getString("ConcurrencyType");
                                    isPaid=person.getString("isPaid");
                                    Applydate = ApplyDate.split("T");

                                        inputFormat2 = new SimpleDateFormat("HH:mm:ss");
                                        outputFormat2 = new SimpleDateFormat("KK:mm a");


                                        Log.d("ResponseAdapter", Applydate[0]);
                                        try {
                                            timeApplyDate_convt = outputFormat2.format(inputFormat2.parse(Applydate[1].substring(0, 8)));







                                        } catch (Exception ex) {

                                        }
                                    //    leaveobject.add(new Leave_Model(Applydate[0],timeApplyDate_convt,Status,LeaveTitle));
                                        leaveobject.add(new Leave_Model(fromLeave, toLeave, Qunatity,  reason,  timeApplyDate_convt, isPaid, Status,  Applydate[0],  TermStart,  TermEnd,  ProcessDate,  LeaveTitle,  ConcurrencyType,  UnitType));
                                        adapter = new LeaveDataAdapter(getActivity(), leaveobject);
                                        recyclerView.setAdapter(adapter);




                                }
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response",error.toString());
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postRequest);
    }

    public void Snack_Bar(String message)

    {
        int color;
        color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

}
