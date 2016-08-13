package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Leave_Model;

/**
 * Created by Ravi on 29/07/15.
 */
public class LeaveFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ProgressDialog progressDialog;
    List<String> leave_model;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private EditText shortdate;
    private EditText NumOFdays;
    private EditText Balance;
    private SimpleDateFormat dateFormatter;
    RelativeLayout layout;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog shortDatePickerDialog;
    Spinner spinnerOsversions;
    String shortDayTotal;
    String LongMonthTotal;
    String hajjOnce;
    String AnnualMonth;
    String item;
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
        leave_model=new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_leave, container, false);
        layout=(RelativeLayout)rootView.findViewById(R.id.layout);


        progressDialog = new ProgressDialog(getActivity());
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEtxt = (EditText) rootView.findViewById(R.id.etxt_fromdate);
        NumOFdays=(EditText)rootView.findViewById(R.id.numdays);
        shortdate=(EditText) rootView.findViewById(R.id.editdate);
        Balance=(EditText)rootView.findViewById(R.id.leavlblac);
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
         setDateTimeField();
        return rootView;
    }
    private void setDateTimeField() {
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
        if(v == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(v == toDateEtxt) {
            toDatePickerDialog.show();
        }
        else if(v==shortdate)
        {
            shortDatePickerDialog.show();
        }
    }



    private void ApplyLeave( ){





        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.ae/API/ApiLeaves/ApplyLeave",
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
                                        leave_model.add(title_en);






                                    if(person.getString("TitleEn").equalsIgnoreCase("Short"))
                                    {
                                        shortDayTotal   =   person.getString("ShortDayTotal");
                                    }
                                    else if(person.getString("TitleEn").equalsIgnoreCase("Long"))
                                    {
                                        LongMonthTotal  =   person.getString("LongMonthTotal");
                                    }
                                    else if(person.getString("TitleEn").equalsIgnoreCase("Hajj"))
                                    {
                                        hajjOnce        =   person.getString("HajjOnceTotal");
                                    }
                                    else
                                    {
                                        AnnualMonth     =   person.getString("AnnualYearTotal");
                                        Log.d("Response",person.getString("AnnualYearTotal"));
                                    }


                                }

                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_item, leave_model);
                                adapter_state
                                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerOsversions.setAdapter(adapter_state);
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
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(getActivity(), "Selected: " + item, Toast.LENGTH_LONG).show();
        if(item.equalsIgnoreCase("Short"))
        {

            fromDateEtxt.setVisibility(View.GONE);
            toDateEtxt.setVisibility(View.GONE);
            shortdate.setVisibility(View.VISIBLE);
            NumOFdays.setHint("Number OF Hour");
            Balance.setText(shortDayTotal);
            Balance.setEnabled(false);

        }
        else if(item.equalsIgnoreCase("Long"))
        {

            NumOFdays.setHint("Number OF Days");


            fromDateEtxt.setVisibility(View.VISIBLE);
            toDateEtxt.setVisibility(View.VISIBLE);
            shortdate.setVisibility(View.GONE);
            Balance.setText(LongMonthTotal);
            Balance.setEnabled(false);
        }
        else if(item.equalsIgnoreCase("Hajj"))
        {
            NumOFdays.setHint("Number OF Days");


            fromDateEtxt.setVisibility(View.VISIBLE);
            toDateEtxt.setVisibility(View.VISIBLE);
            shortdate.setVisibility(View.GONE);
            Balance.setText(hajjOnce);
            Balance.setEnabled(false);
        }
        else
        {
            NumOFdays.setHint("Number OF Days");


            fromDateEtxt.setVisibility(View.VISIBLE);
            toDateEtxt.setVisibility(View.VISIBLE);
            shortdate.setVisibility(View.GONE);
            Balance.setText(AnnualMonth);
            Balance.setEnabled(false);
        }

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
}
