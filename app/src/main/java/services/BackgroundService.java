package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DBHandle.DatabaseHandler;
import connection.ConnectivityReceiver;
import info.androidhive.materialdesign.activity.MainActivity;
import info.androidhive.materialdesign.model.Addendance_DB_Model;

/**
 * Created by Arrowtec on 7/16/2016.
 */
public class BackgroundService extends Service {

    DatabaseHandler db ;
    public static final String KEY_IBAECONID = "ibeacon";
    public static final String KEY_MACADDRESS = "macaddress";
    private static final String TAG = "HelloService";

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        db=new DatabaseHandler(getApplicationContext());
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                while(!ConnectivityReceiver.isConnected()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                    if(isRunning){
                        Log.i(TAG, "Internet not found Server running");
                        Log.i(TAG, "Service running");
                        Log.d("Reading: ", "Reading all contacts..");
              List<Addendance_DB_Model> contacts = db.getAllContacts();

                        for (Addendance_DB_Model cn : contacts) {
                            String log = "Id: "+cn.get_id()+" ,Name: " + cn.get_CompId() + " ,Phone: " + cn.get_EmpId()+"Date"+ cn.get_DateTime()+"Ibeacon"+cn.getStatus();
                            // Writing Contacts to log
                            Log.d("Reading: ", log);

                        }
                    }
                }

                    stopSelf();


                //Stop service once it finishes its task

            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        Log.i(TAG, "Internet found Server running");
        Log.i(TAG, "Service onDestroy");
        final List<Addendance_DB_Model> contacts = db.getAllContacts();

        for (final Addendance_DB_Model cn : contacts) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, "http://waqt.mydreamapps.com/API/ApiAttendances/EmployeeAttendance",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            db.deleteContact(db.getContact(cn.get_id()));
                            Log.d("Response", "Record Delete "+ cn.get_id());

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Response", error.toString());
                            // mediaPlayer.start();
                            // Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    String log = "Id: " + cn.get_id() + " ,Name: " + cn.get_CompId() + " ,Phone: " + cn.get_EmpId();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                    params.put("EmployeeID", cn.get_EmpId());
                    params.put("CheckInOutTime",cn.get_DateTime());
                    params.put("CompanyID",cn.get_CompId());
                    params.put("EstimoteUUID",cn.get_IbeaconId());
                    params.put("Status",cn.getStatus());
                    params.put("Latitude",cn.getLatit());
                    params.put("Longitude",cn.getLogni());

//                    params.put("Content-Type", "application/json; charset=utf-8");
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(postRequest);
        }
    }
}
