package info.androidhive.materialdesign.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.CustomRequest;
import butterknife.Bind;
import butterknife.ButterKnife;
import connection.ConnectivityReceiver;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.login_model_user;



public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String pass = "emailKey";
    public static final String emi = "nameKey";
    private ArrayList<login_model_user> ibeacon;
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MACADDRESS = "macaddress";
    TelephonyManager telephonyManager;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    public static String macAddress = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection();

            }
        });

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;

        if (isConnected) {
               login();
        } else {
            message = "Sorry! Not connected to internet";
            Snack_Bar(message);

        }



    }

    public void Snack_Bar(String message)

    {
        int color;
        color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String id;

        final String password = _passwordText.getText().toString();
         editor = sharedpreferences.edit();


        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
          macAddress = wInfo.getMacAddress();


        Log.d("Response",password+""+macAddress);
        String ab = sharedpreferences.getString(pass, null);
       // Toast.makeText(LoginActivity.this,"Thanks"+ab,Toast.LENGTH_LONG).show();
        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        registerUser(password, macAddress);
                     //  onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();

                    }
                }, 3000);
    }



    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

         String password = _passwordText.getText().toString();



        if (password.isEmpty() || password.length() < 6 || password.length() > 7) {
            _passwordText.setError("Enter 6 digit Password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void registerUser(final String password, final String macaddress){


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

            String url ="http://schoolhrms.mydreamapps.com/api/testapi/get?password="+password+"&mac"+macaddress;
            Log.d("URL",url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://schoolhrms.mydreamapps.com/api/testapi/get?password=zuvate@yahoo.com&mac=02:00:00:00:00:00",
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
                                            String name = person.getString("NameEn");
                                            if(name.equalsIgnoreCase("0"))
                                            {
                                                Log.d("Response", response.toString());

                                                Snack_Bar("Record Not Found");
                                                _loginButton.setEnabled(true);
                                            }
                                            else
                                            {
                                                editor.putString(pass, password);
                                                editor.putString(emi,macAddress);
                                                editor.putString("jsonArray",response.toString());
                                                editor.commit();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
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
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//              //  params.put(KEY_MACADDRESS,macaddress);
//                params.put(KEY_PASSWORD,password);
//
//                return params;
//            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}
