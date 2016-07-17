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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.repackaged.retrofit_v1_9_0.retrofit.http.POST;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import DBHandle.DatabaseHandler;
import connection.ConnectivityReceiver;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Contact;
import services.BackgroundService;


public class Dashboard extends Fragment {
    private MediaPlayer mediaPlayer;
    Button checkin,checkout;
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
    DatabaseHandler db ;
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


        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Check_In();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Check_out();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }


public void Check_In() throws IOException {


        if (mBluetoothAdapter.isEnabled()) {
            //     status.setText("BlueTooth is currently switched ON");
            //     changeStatus.setText("Switch OFF Bluetooth");
          /*  beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                @Override
                public void onEnteredRegion(Region region, List<Beacon> list) {
                    Log.d("Response","Enter in Regin");


                }
                @Override
                public void onExitedRegion(Region region) {
                    // could add an "exit" notification too if you want (-:
                    Log.d("Response","You are out of the Regin");
                }

            });
            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    int major=Integer.parseInt(MainActivity.major);
                    int minor=Integer.parseInt(MainActivity.minor);
                    beaconManager.startMonitoring(new Region("monitored region",
                            UUID.fromString(MainActivity.uuid), major, minor));
                }
            });
*/
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
                    mBluetoothAdapter.startLeScan(leScanCallback);
                }
            }




        } else {
            Snack_Bar_Cus();
        }

    }



    public void Check_out() throws IOException {

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

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord)
        {
            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5)
            {
                if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15)
                { //Identifies correct data length
                    patternFound = true;
                    break;
                }
                startByte++;
            }
            Log.d("Response",""+patternFound);
            if (patternFound==true)
            {
                //Convert to hex String
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                //UUID detection
                String uuid =  hexString.substring(0,8) + "-" +
                        hexString.substring(8,12) + "-" +
                        hexString.substring(12,16) + "-" +
                        hexString.substring(16,20) + "-" +
                        hexString.substring(20,32);

                // major
                final int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

                // minor
                final int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);
                byte txpw = scanRecord[29];

                String major_conv=String.valueOf(major);
                String minor_conv=String.valueOf(minor);
                if(ConnectivityReceiver.isConnected())
                {
                    Post_Check_in_out(major_conv,minor_conv,uuid);
                    Log.d("Response","Found");
                }
                else
                {
                    mBluetoothAdapter.stopLeScan(leScanCallback);
                    Log.d("Response","Internet Not Found");
                    Log.d("Insert: ", "Inserting ..");
                    db.addContact(new Contact("Ravi", "9100000000"));
                    db.addContact(new Contact("Srinivas", "9199999999"));
                    db.addContact(new Contact("Tommy", "9522222222"));
                    db.addContact(new Contact("Karthik", "9533333333"));
                    Intent intent = new Intent(getActivity(), BackgroundService.class);
                    getActivity().startService(intent);
                }


            }
            else if(patternFound==false)
            {
                POST_Check_ERROR();
            }

        }
    };

    private void POST_Check_ERROR() {
        mBluetoothAdapter.stopLeScan(leScanCallback);
        Log.d("Response","Not Found");
        Toast.makeText(getActivity(),"IBEACON Not Fount",Toast.LENGTH_LONG).show();
    }

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


    private void Post_Check_in_out (final String major, String minor, String uuid){
        mBluetoothAdapter.stopLeScan(leScanCallback);

        final String ibeaconId = major+""+minor+""+uuid;
        // JsonArrayRequest stringRequest = new JsonArrayRequest (Request.Method.POST, "http://192.168.1.140:8080/test.php",
        //  new Response.Listener<JSONArray>() {
        //     @Override
        //    public void onResponse(JSONArray response) {
        //       Log.d("Response",response.toString());
        //       editor.putString("jsonArray",response.toString());
        //       editor.commit();

        //       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //      startActivity(intent);
        //      finish();
        //   }
        Log.d("Response", ibeaconId);

        Log.d("Response", MainActivity.emii);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://192.168.1.140:8080/test2.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Toast.makeText(getActivity(), response,Toast.LENGTH_LONG).show();
//                        editor.putString("jsonArray",response);
  //                      editor.commit();
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
                params.put(KEY_IBAECONID,ibeaconId);
                params.put(KEY_MACADDRESS,MainActivity.emii);

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
