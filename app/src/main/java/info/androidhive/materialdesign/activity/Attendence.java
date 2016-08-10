package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.sdk.repackaged.retrofit_v1_9_0.retrofit.http.POST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connection.ConnectivityReceiver;
import gpstracker.GPSTracker;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Attendance_Adapter;
import info.androidhive.materialdesign.model.Attendence_history;

/**
 * Created by Arrowtec on 7/13/2016.
 */
public class Attendence extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Attendence_history> attendence;
    Attendance_Adapter adapter;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefsAddendance";
    SharedPreferences.Editor editor;
    LinearLayout layout;
    ProgressDialog progressDialog;
    String[] checkin_output;
    String[] checkout_output;
    DateFormat inputFormat2;
    DateFormat outputFormat2;
    String check_in,check_out,total_work_hour,EmployeeId;
    String timeout_convt;
    String timein_convt;
    String tot_hour_convt;

    public Attendence() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        attendence=new ArrayList<Attendence_history>();
        View rootView = inflater.inflate(R.layout.fragment_attendence, container, false);
        progressDialog = new ProgressDialog(getActivity());
        layout=(LinearLayout)rootView.findViewById(R.id.layout_attendance);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
            AttendanceHistory(MainActivity.EmployeeId);
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
                    }, 3000);



        }



        // Inflate the layout for this fragment
        return rootView;
    }
public void OFFLINE_ATTENDACE()
{
    String json_array = sharedpreferences.getString("AttendanceJsonArray", null);
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
                check_in = person.getString("check_in");
                check_out = person.getString("check_out");
                total_work_hour = person.getString("total_hour");
                EmployeeId = person.getString("employee_id");

                checkin_output = check_in.split("T");
                checkout_output=check_out.split("T");
                inputFormat2  = new SimpleDateFormat("HH:mm:ss");
                outputFormat2 = new SimpleDateFormat("KK:mm a");

                try {
                    timeout_convt=outputFormat2.format(inputFormat2.parse(checkout_output[1].substring(0,8)));
                    timein_convt=outputFormat2.format(inputFormat2.parse(checkout_output[1].substring(0,8)));
                    tot_hour_convt=outputFormat2.format(inputFormat2.parse(total_work_hour.substring(0,7)));
                    Log.d("ResponseAdapter",checkin_output[1]);




                }
                catch (Exception ex)
                {

                }
                attendence.add(new Attendence_history(checkin_output[0],checkout_output[0],timeout_convt,timein_convt,tot_hour_convt,EmployeeId));
                adapter=new Attendance_Adapter(getActivity(),attendence);
                recyclerView.setAdapter(adapter);
                Log.d("Response", check_in);
            }
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    private void AttendanceHistory(final String Id){


        String url ="http://waqt.mydreamapps.com/API/ApiAttendances/AttendaceHistory=";
        Log.d("URL",url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.mydreamapps.com/API/ApiAttendances/AttendaceHistory",
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

                                editor.remove("AttendanceJsonArray");
                                editor.putString("AttendanceJsonArray", response.toString());
                                editor.commit();

                                check_in = person.getString("CheckinTime");
                                check_out = person.getString("CheckoutTime");
                                total_work_hour = person.getString("TotalHours");
                                String statusCode=person.getString("ResponseStatusCode");

                                if (statusCode.equalsIgnoreCase("0")) {
                                    Log.d("ResponseAdapterTotal", total_work_hour);
                                    Snack_Bar("Record Not Found");
                                    progressDialog.hide();

                                } else {
                                checkin_output = check_in.split("T");
                                checkout_output = check_out.split("T");
                                inputFormat2 =      new SimpleDateFormat("HH:mm:ss");
                                outputFormat2 = new SimpleDateFormat("KK:mm a");


                                    Log.d("ResponseAdapter", checkin_output[0]);
                                    try {
                                        timeout_convt = outputFormat2.format(inputFormat2.parse(checkout_output[1].substring(0, 8)));
                                        timein_convt = outputFormat2.format(inputFormat2.parse(checkout_output[1].substring(0, 8)));
                                        tot_hour_convt = outputFormat2.format(inputFormat2.parse(total_work_hour.substring(0, 7)));
                                    //    tot_hour_convt = outputFormat2.format(inputFormat2.parse(total_work_hour.substring(0, 7)));
                                        Log.d("ResponseAdapter2", checkin_output[0]);




                                    } catch (Exception ex) {

                                    }
                                    attendence.add(new Attendence_history(checkin_output[0], checkout_output[0], timeout_convt, timein_convt, tot_hour_convt, EmployeeId));
                                    adapter = new Attendance_Adapter(getActivity(), attendence);
                                    recyclerView.setAdapter(adapter);

                                    Log.d("Response", check_in.substring(0, 15));


                                }
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
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("EmployeeID",MainActivity.EmployeeId);
                params.put("Content-Type", "application/json; charset=utf-8");

                return params;
            }
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