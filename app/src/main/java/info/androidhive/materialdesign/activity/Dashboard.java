package info.androidhive.materialdesign.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import DBHandle.DatabaseHandler;
import connection.ConnectivityReceiver;
import gpstracker.GPSTracker;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Addendance_DB_Model;
import services.BackgroundService;


public class Dashboard extends Fragment {
    private MediaPlayer mediaPlayer;
    Button checkin,checkout,attendance,leave;
    private BeaconManager beaconManager;
    private Region region;
    private boolean isScanning = false;
    Snackbar snackbar;
    private BluetoothAdapter mBluetoothAdapter;
    RelativeLayout layout;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String KEY_IBAECONID = "ibeacon";
    public static final String KEY_MACADDRESS = "macaddress";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String major_conv;
    String minor_conv;
    String uuid;
    String currentDateTimeString;
    DatabaseHandler db ;
    Fragment fragment = null;
    String title;
    String status;
    GPSTracker gps;
    double longitude;
    double latitude; String latit; String longi;
    public Dashboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         title = getString(R.string.app_name);
        gps = new GPSTracker(getActivity());
        //reference to the bluetooth adapter
        beaconManager = new BeaconManager(getActivity());
        db=new DatabaseHandler(getActivity());
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song);
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        layout= (RelativeLayout)rootView.findViewById(R.id.dashboardlayout);
        checkin=(Button)rootView.findViewById(R.id.checkin);
        checkout=(Button)rootView.findViewById(R.id.checkout);
        attendance=(Button) rootView.findViewById(R.id.myattendance);
        leave=(Button)rootView.findViewById(R.id.myleave);
//
//
//        checkout.setVisibility(View.INVISIBLE);
//        checkin.setVisibility(View.INVISIBLE);
        checkout.setEnabled(false);
        if(gps.canGetLocation()){

              latitude = gps.getLatitude();
              longitude = gps.getLongitude();
              latit = String.valueOf(latitude);
              longi = String.valueOf(longitude);
            // \n is for new line
//            Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Attendence();
                title = getString(R.string.title_attendence);

                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                    // set the toolbar title
                }
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MyLeaveList();
                title = getString(R.string.title_leave);
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                    // set the toolbar title
                }
            }
        });
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Check_In("Checkin");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Check_out("Checkout");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }


public void Check_In(String check_in) throws IOException {


        if (mBluetoothAdapter.isEnabled()) {

            if (isScanning)
            {
                if (mBluetoothAdapter != null)
                {
                    mBluetoothAdapter.stopLeScan(leScanCallback);

                }
            }
            else
            {
                if (mBluetoothAdapter != null)
                {
                    status=check_in;
                    mBluetoothAdapter.startLeScan(leScanCallback);
                }
            }




        } else {
            Snack_Bar_Cus();
        }

    }



    public void Check_out(String check_out) throws IOException {

        if (mBluetoothAdapter.isEnabled()) {
        //     status.setText("BlueTooth is currently switched ON");
        //     changeStatus.setText("Switch OFF Bluetooth");
            if (isScanning)
            {
                if (mBluetoothAdapter != null)


                {
                    mBluetoothAdapter.stopLeScan(leScanCallback);

                }
            }
            else
            {
                if (mBluetoothAdapter != null)
                {
                    status=check_out;
                    mBluetoothAdapter.startLeScan(leScanCallback);
                }
            }

    } else {
            Snack_Bar_Cus();
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





    BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        String ibeaconId;

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5) {
                if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                    patternFound = true;
                    break;
                }
                startByte++;
            }
            Log.d("Response", "" + patternFound);
            if (patternFound == true) {
                //Convert to hex String
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                //UUID detection
                uuid = hexString.substring(0, 8) + "-" +
                        hexString.substring(8, 12) + "-" +
                        hexString.substring(12, 16) + "-" +
                        hexString.substring(16, 20) + "-" +
                        hexString.substring(20, 32);

                // major
                final int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

                // minor
                final int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);
                byte txpw = scanRecord[29];

                major_conv = String.valueOf(major);
                minor_conv = String.valueOf(minor);

                if (ConnectivityReceiver.isConnected())

                {

                    mBluetoothAdapter.stopLeScan(leScanCallback);
                    ibeaconId = uuid + "" + major_conv + "" + minor_conv;

                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Log.d("Response Data2", currentDateTimeString);
                    Post_Check_in_out(ibeaconId, currentDateTimeString);
                    Log.d("Response", "UUID" + uuid + "Major" + major + "Minor" + minor);
                } else {
                    ibeaconId = uuid + "" + major_conv + "" + minor_conv;
                    mBluetoothAdapter.stopLeScan(leScanCallback);
                    Log.d("Response", "UUID" + uuid + "Major" + major + "Minor" + minor);
                    Log.d("Response", "Internet Not Found");
                    Log.d("Insert: ", "Inserting ..");
                    Snack_Bar("Checked Successfully");
                    mediaPlayer.start();
                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Log.d("Response Data", currentDateTimeString);
                    db.addContact(new Addendance_DB_Model(MainActivity.EmployeeId, MainActivity.comp_id, currentDateTimeString, ibeaconId,status,latit,longi));

                    Intent intent = new Intent(getActivity(), BackgroundService.class);
                    getActivity().startService(intent);
                }


            } else if (patternFound == false) {

                // WIFI Check

                if (ConnectivityReceiver.isConnected()) {
                    Post_Check_In_Out_Offical(currentDateTimeString, "0");
                    POST_Check_ERROR();
                } else {
                    mBluetoothAdapter.stopLeScan(leScanCallback);

                    Log.d("Response", "Internet Not Found");
                    Log.d("Insert: ", "Inserting ..");
                    Snack_Bar("Checked Successfully");
                    mediaPlayer.start();
                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Log.d("Response Data2", currentDateTimeString);
                    db.addContact(new Addendance_DB_Model(MainActivity.EmployeeId, MainActivity.comp_id, currentDateTimeString, "0",status,latit,longi));

                    Intent intent = new Intent(getActivity(), BackgroundService.class);
                    getActivity().startService(intent);
                }

            }

        }
    };


    /**
     * bytesToHex method
     */
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    private void Post_Check_in_out (final String ibeacon, final String datatime){
        mBluetoothAdapter.stopLeScan(leScanCallback);
        Log.d("Response", ibeacon);
        String json_array = sharedpreferences.getString("jsonArray", null);


        JSONArray jsoArray= null;


        Log.d("Response", MainActivity.emii);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.mydreamapps.com/API/ApiAttendances/EmployeeAttendance",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Snack_Bar("Checked Successfully");

                                mediaPlayer.start();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response",error.toString());
                       // mediaPlayer.start();
                       // Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("EmployeeID",MainActivity.EmployeeId);
                params.put("CheckInOutTime",datatime);
                params.put("CompanyID",MainActivity.comp_id);
                params.put("EstimoteUUID",ibeacon);
                params.put("Status",status);
                params.put("Latitude",latit);
                params.put("Longitude",longi);
//                params.put("Content-Type", "application/json; charset=utf-8");
                Log.d("Response",""+params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postRequest);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    public void Snack_Bar_Cus()

    {

        Snackbar snackbar = Snackbar
                .make(layout, "BlueTooth adapter not found", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(turnOn, 0);


                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }
    private void showSnack(boolean isConnected) {
        String message;

        if (isConnected) {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        } else {
            message = "Sorry! Not connected to internet";
            Snack_Bar(message);
            // finish();

        }



    }
    private void Post_Check_In_Out_Offical (final String currentdate, final String ibeaconId ){
        mBluetoothAdapter.stopLeScan(leScanCallback);

        Log.d("Response", MainActivity.emii);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.mydreamapps.com/API/ApiAttendances/EmployeeAttendance",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        //Toast.makeText(getActivity(), response,Toast.LENGTH_LONG).show();
//                        editor.putString("jsonArray",response);
                        //                      editor.commit();
                        Snack_Bar("Checked Successfully");
                        mediaPlayer.start();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response",error.toString());
                        // mediaPlayer.start();
                        // Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("EmployeeID",MainActivity.EmployeeId);
                params.put("CheckInOutTime",currentdate);
                params.put("CompanyID",MainActivity.comp_id);
                params.put("EstimoteUUID",ibeaconId);
                params.put("Status",status);
                params.put("Latitude",latit);
                params.put("Longitude",longi);
//                params.put("Content-Type", "application/json; charset=utf-8");
                Log.d("Response",""+params);
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


    private void POST_Check_ERROR() {
        mBluetoothAdapter.stopLeScan(leScanCallback);
        Log.d("Response","Not Found");
        Snack_Bar("Failed Please Try Again");
        // Toast.makeText(getActivity(),"IBEACON Not Fount",Toast.LENGTH_LONG).show();
    }
}
