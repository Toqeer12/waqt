package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.CustomAdapter;
import info.androidhive.materialdesign.model.Apply_Leave;

/**
 * Created by Ravi on 29/07/15.
 */
public class LeaveFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ProgressDialog progressDialog;
    ArrayList<Apply_Leave> leave_model;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private EditText shortdate;
    private EditText NumOFdays;
    private EditText Balance,leavelimit;
    private SimpleDateFormat dateFormatter;
    RelativeLayout layout;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog shortDatePickerDialog;
    Spinner spinnerOsversions;
    String shortDayTotal;
    String LongMonthTotal;
    String hajjOnce;
    String AnnualMonth,leave_id;
    String item;
    Button submit,canncel;
    Fragment fragment = null;
    String title;
    String  leavepaidtype;
    CheckBox checkBox;
    EditText reason,unitType;
    String LeaveTypeId;
    public LeaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        leave_model=new ArrayList<Apply_Leave>();
        View rootView = inflater.inflate(R.layout.fragment_leave, container, false);
        layout=(RelativeLayout)rootView.findViewById(R.id.layout);


        progressDialog = new ProgressDialog(getActivity());
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        reason=(EditText)rootView.findViewById(R.id.editreason);
        checkBox=(CheckBox)rootView.findViewById(R.id.paidleave);
        fromDateEtxt = (EditText) rootView.findViewById(R.id.etxt_fromdate);
        NumOFdays=(EditText)rootView.findViewById(R.id.numdays);
        shortdate=(EditText) rootView.findViewById(R.id.editdate);
        Balance=(EditText)rootView.findViewById(R.id.leavlblac);
        submit=(Button)rootView.findViewById(R.id.submit);
        canncel=(Button)rootView.findViewById(R.id.cancel);
        unitType=(EditText)rootView.findViewById(R.id.unit);
        leavelimit=(EditText)rootView.findViewById(R.id.leavelimit);
        shortdate.setInputType(InputType.TYPE_NULL);
        shortdate.requestFocus();
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) rootView.findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        spinnerOsversions = (Spinner) rootView.findViewById(R.id.osversions);
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //  registerUser(password, macAddress);
                        //  onLoginSuccess();
                        // onLoginFailed();
                        ApplyLeave( );
                        progressDialog.dismiss();

                    }
                }, 3000);


        // Inflate the layout for this fragment
        checkBox.setChecked(true);
         setDateTimeField();
        Log.d("Response",""+checkBox.isChecked());
        return rootView;
    }
    private void setDateTimeField() {

        canncel.setOnClickListener(this);
        submit.setOnClickListener(this);
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        shortdate.setOnClickListener(this);

        spinnerOsversions.setOnItemSelectedListener(this);
        Calendar newCalendar = Calendar.getInstance();
        shortDatePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                shortdate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


     }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        String shortleave,fromdate,todate,numboerofdays,reasonleave,unitype;
        if(v == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(v == toDateEtxt) {
            toDatePickerDialog.show();
        }
        else if(v==shortdate)
        {
            shortDatePickerDialog.show();
        }
        if(v==submit)
        {


            progressDialog.setMessage("Applying Leave");
            progressDialog.show();
            if(item.equalsIgnoreCase("Short"))
            {
                unitype=unitType.getText().toString();
                shortleave=shortdate.getText().toString();
                fromdate=shortdate.getText().toString();
                todate=shortdate.getText().toString();
                numboerofdays=NumOFdays.getText().toString();
                reasonleave=reason.getText().toString();
                Log.d("Response",shortdate.getText().toString());
                Log.d("Response",NumOFdays.getText().toString());
                Log.d("Response",reason.getText().toString());

                if(checkBox.isChecked())
                {
                leavepaidtype="true";
                    Log.d("Response",""+leavepaidtype);
                }
                else
                {
                    leavepaidtype="false";
                    Log.d("Response",""+leavepaidtype);
                }
                PostLeave(unitype,shortleave,fromdate,todate,numboerofdays,reasonleave,leavepaidtype,item);
            }
            else
            {
                shortleave=null;
                unitype=unitType.getText().toString();
                fromdate=fromDateEtxt.getText().toString();
                todate=toDateEtxt.getText().toString();
                numboerofdays=NumOFdays.getText().toString();
                reasonleave=reason.getText().toString();
                Log.d("Response",fromDateEtxt.getText().toString());
                Log.d("Response",toDateEtxt.getText().toString());
                Log.d("Response",NumOFdays.getText().toString());
                Log.d("Response",reason.getText().toString());
                if(checkBox.isChecked())
                {
                    leavepaidtype="true";
                    Log.d("Response",""+leavepaidtype);
                }
                else
                {
                    leavepaidtype="false";
                    Log.d("Response",""+leavepaidtype);
                }
                PostLeave(unitype,shortleave,fromdate,todate,numboerofdays,reasonleave,leavepaidtype,item);
            }



         }
        if(v==canncel)
        {
            fragment = new Dashboard();
            title = getString(R.string.title_attendence);

            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

                // set the toolbar title
            }
        }
    }



    private void ApplyLeave( ){





        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.ae/API/ApiLeaves/ListLeaveType",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try {
                            Log.d("Response", response.toString());
                            JSONArray jsoArray = new JSONArray(response);
                            for (int i = 0; i < jsoArray.length(); i++) {
                                JSONObject person = (JSONObject) jsoArray
                                        .get(i);
                                String name = person.getString("ResponseStatusCode");
                                if(name.equalsIgnoreCase("0"))
                                {
                                    Log.d("Response", response.toString());

                                    Snack_Bar("Wrong Password");

                                }
                                else
                                {
                                    String title_en=person.getString("TitleEn");
                                    leave_id=person.getString("LeaveTypeID");
                                    leave_model.add(new Apply_Leave(title_en,person.getString("LeaveTypeID")));
                                }
                                CustomAdapter customAdapter=new CustomAdapter(getActivity(),leave_model);
                                spinnerOsversions.setAdapter(customAdapter);
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //item = parent.getItemAtPosition(position).toString();
        item =  leave_model.get(position).getLeaveType();
        LeaveTypeId=leave_model.get(position).getLeaveTypeId();
        // Showing selected spinner item
        GetLeaveStatus(LeaveTypeId);
        Toast.makeText(getActivity(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    public void PostLeave(final String unitype,final String shortleave, final String fromdate, final String todate, final String numboerofdays, final String reasonleave, final String leavepaidtype, final String item)
    {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String formattedDate = df.format(c.getTime());

//        final JSONObject params = new JSONObject();
//        try {
//            params.put("LeaveTypeID",leave_id);
//            params.put("LeaveStatus","InProcess");
//            params.put("ApplyDate",parseDateToddMMyyyy(formattedDate));
//            params.put("FromDate",parseDateToddMMyyyy(fromdate));
//            params.put("ToDate",parseDateToddMMyyyy(todate));
//            params.put("UnitType",unitype);
//            params.put("Reason",reasonleave);
//            params.put("IsPaid",leavepaidtype);
//            params.put("EmployeeID",MainActivity.EmployeeId);
//            params.put("ReplacementEmployeeID","0");
//            params.put("Quantity",numboerofdays);
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.ae/API/ApiLeaves/AddEmployeeLeave\n",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Log.d("Response",response);
                        try {
                            JSONArray jsoArray = new JSONArray(response);


                            if(jsoArray.length()==0)
                            {
                                Log.d("Response", response.toString());

                                Snack_Bar("Record Not Found");
                                progressDialog.hide();

                            }
                            else {
                                for (int i = 0; i < jsoArray.length(); i++) {
                                    JSONObject person = (JSONObject) jsoArray
                                            .get(i);
                                    Snack_Bar(person.getString("ResponseMessage").toString());
                                    progressDialog.hide();
                                    reason.getText().clear();
                                    shortdate.getText().clear();
                                    NumOFdays.getText().clear();


                                }
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
                params.put("LeaveTypeID",leave_id);
                params.put("LeaveStatus","InProcess");
                params.put("ApplyDate",parseDateToddMMyyyy(formattedDate));
                params.put("FromDate",parseDateToddMMyyyy(fromdate));
                params.put("ToDate",parseDateToddMMyyyy(todate));
                params.put("UnitType",unitype);
                params.put("Reason",reasonleave);
                params.put("IsPaid",leavepaidtype);
                params.put("EmployeeID",MainActivity.EmployeeId);
                params.put("ReplacementEmployeeID","0");
                params.put("Quantity",numboerofdays);
                params.put("Content-Type", "application/json; charset=utf-8");
                Log.d("Response",params.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postRequest);
    }


    private void GetLeaveStatus(final String leaveTypeId){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.ae//API/ApiLeaves/LeaveTypeView",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try {
                            Log.d("Response", response.toString());
                            JSONArray jsoArray = new JSONArray(response);
                            for (int i = 0; i < jsoArray.length(); i++) {
                                JSONObject person = (JSONObject) jsoArray
                                        .get(i);
                                String name = person.getString("ResponseStatusCode");
                                if(name.equalsIgnoreCase("0"))
                                {
                                    Log.d("Response", response.toString());

                                    Snack_Bar("Wrong Password");

                                }
                                else
                                {

                                        if(person.getString("LeaveTypeID").equalsIgnoreCase("11"))
                                        {
                                            fromDateEtxt.setVisibility(View.GONE);
                                            toDateEtxt.setVisibility(View.GONE);
                                            shortdate.setVisibility(View.VISIBLE);
                                            NumOFdays.setHint("Number OF Hour");
                                            Balance.setText(person.getString("Total"));
                                            leavelimit.setText(person.getString("ApplyLimit"));
                                            unitType.setText(person.getString("UnitType"));
                                            Balance.setEnabled(false);
                                            leavelimit.setEnabled(false);
                                            unitType.setEnabled(false);

                                        }
                                        else if(person.getString("LeaveTypeID").equalsIgnoreCase("12"))
                                        {

                                            NumOFdays.setHint("Number OF Days");
                                            fromDateEtxt.setVisibility(View.VISIBLE);
                                            toDateEtxt.setVisibility(View.VISIBLE);
                                            shortdate.setVisibility(View.GONE);
                                            Balance.setText(person.getString("Total"));
                                            leavelimit.setText(person.getString("ApplyLimit"));
                                            unitType.setText(person.getString("UnitType"));
                                            Balance.setEnabled(false);
                                            leavelimit.setEnabled(false);
                                            unitType.setEnabled(false);
                                        }

                                        else if(person.getString("LeaveTypeID").equalsIgnoreCase("13"))
                                        {
                                            NumOFdays.setHint("Number OF Days");
                                            fromDateEtxt.setVisibility(View.VISIBLE);
                                            toDateEtxt.setVisibility(View.VISIBLE);
                                            shortdate.setVisibility(View.GONE);
                                            Balance.setEnabled(false);
                                            leavelimit.setEnabled(false);
                                            unitType.setEnabled(false);
                                            Balance.setText(person.getString("Total"));
                                            leavelimit.setText(person.getString("ApplyLimit"));
                                            unitType.setText(person.getString("UnitType"));
                                        }
                                        else
                                        {
                                            NumOFdays.setHint("Number OF Days");
                                            fromDateEtxt.setVisibility(View.VISIBLE);
                                            toDateEtxt.setVisibility(View.VISIBLE);
                                            shortdate.setVisibility(View.GONE);
                                            Balance.setEnabled(false);
                                            leavelimit.setEnabled(false);
                                            unitType.setEnabled(false);
                                            Balance.setText(person.getString("Total"));
                                            leavelimit.setText(person.getString("ApplyLimit"));
                                            unitType.setText(person.getString("UnitType"));
                                        }




                                }

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
                params.put("LeaveTypeID",leaveTypeId);
                params.put("Content-Type", "application/json; charset=utf-8");

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postRequest);
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "MM/dd/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
