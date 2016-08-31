package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import DBHandle.DatabaseHandler;
import info.androidhive.materialdesign.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pass = "emailKey";
    public static final String emi = "nameKey";
    SharedPreferences sharedpreferences;
    SharedPreferences prefs ;
    public static String emii,password,comp_id;
    SharedPreferences.Editor editor;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    public static String profile ,comp_logo,EmployeeId;
    public static String name;
    TextView txt;
    ProgressDialog progressDialog;
    ImageView img;
    String langPref = "Language";
    private Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        //checkConnection();
        password = sharedpreferences.getString(pass, null);
        emii = sharedpreferences.getString(emi, null);
        img=(ImageView)findViewById(R.id.image);
        txt=(TextView)findViewById(R.id.text);
        DatabaseHandler db = new DatabaseHandler(this);
        progressDialog = new ProgressDialog(MainActivity.this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
/*
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Addendance_DB_Model("Ravi", "9100000000"));
        db.addContact(new Addendance_DB_Model("Srinivas", "9199999999"));
        db.addContact(new Addendance_DB_Model("Tommy", "9522222222"));
        db.addContact(new Addendance_DB_Model("Karthik", "9533333333"));
*/

        // Reading all contacts
/*        Log.d("Reading: ", "Reading all contacts..");
        List<Addendance_DB_Model> contacts = db.getAllContacts();

        for (Addendance_DB_Model cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }*/
        if(password==null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            String json_array = sharedpreferences.getString("jsonArray", null);

                try {
                    JSONArray jsoArray=new JSONArray(json_array);
                    for (int i = 0; i < jsoArray.length(); i++) {
                        JSONObject person = (JSONObject) jsoArray
                                .get(i);
                        name = person.getString("NameEn");
                        comp_logo = person.getString("CompanyImagePath");
                        profile=person.getString("ImagePath");
                        EmployeeId = person.getString("EmployeeID");
                        comp_id=person.getString("CompanyID");

                        Glide.with(getApplicationContext())
                                .load(comp_logo.replace("https", "http"))
                                .asBitmap()
                                .fitCenter()
                                .into(img);
                        Log.d("Response", name);
                        txt.setText(person.getString("CompanyNameEn"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


        //    Toast.makeText(getApplicationContext(),"Emi"+emii+"Password"+password,Toast.LENGTH_LONG).show();
        }
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        // display the first navigation drawer view on app launch
       displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        String backStateName = null;
        backStateName=MainActivity.class.getName();
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new Dashboard();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new Profile();
                title = getString(R.string.title_profile);
                break;
            case 2:
                fragment = new LeaveFragment();
                title = getString(R.string.title_leave);
                break;
            case 3:
                fragment = new Attendence();
                title = getString(R.string.title_attendence);
                break;
            case 4:
                fragment = new MyLeaveList();
                title=getString(R.string.title_leavelist);
                break;
            case 5:
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);

            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
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
    @Override
    public void onBackPressed() {

    }
    public void loadLocale()
    {


        String language = prefs.getString(langPref, "");
        changeLang(language);
    }
    public void saveLocale(String lang)
    {


        editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }
    public void changeLang(String lang)
    {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }
    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myLocale != null){
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    private void updateTexts()
    {
                recreate();
//        Intent i=new Intent(MainActivity.this,MainActivity.class);
//        startActivity(i);
//        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        switch (id) {
            case R.id.about:
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading....");
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialoug);
                dialog.setTitle("Select Language");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);

                // set the custom dialog components - text, image and button


                Button english = (Button) dialog.findViewById(R.id.english);
                Button arab = (Button) dialog.findViewById(R.id.arabic);
                // if button is clicked, close the custom dialog
                english.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
//                        String lang;
//                        lang = "en";
                        Log.d("Response","Arabic Click");
//                        item.setTitle(prefs.getString("langsave", ""));
//                        editor = prefs.edit();
//                        editor.putString("langsave", "English");
//                        editor.commit();
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        String lang;
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        //  registerUser(password, macAddress);
                                        //  onLoginSuccess();
                                        // onLoginFailed();
                                        lang = "en";
                                        changeLang(lang);
                                        loadLocale();

                                        progressDialog.dismiss();
                                        dialog.dismiss();

                                    }
                                }, 1000);
                    }
                });
                arab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Log.d("Response","Arabic Click");
//                        item.setTitle(prefs.getString("langsave", ""));
//                        editor = prefs.edit();
//                        editor.putString("langsave", "English");
         //               editor.commit();

                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        String lang;
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        //  registerUser(password, macAddress);
                                        //  onLoginSuccess();
                                        // onLoginFailed();
                                        lang = "ar";
                                        changeLang(lang);
                                        loadLocale();

                                        progressDialog.dismiss();
                                        dialog.dismiss();

                                    }
                                }, 3000);
                    }
                });

                dialog.show();
//                if(item.getTitle().toString().equalsIgnoreCase("Arabic")) {
//                    lang = "ar";
//                    Log.d("Response","Arabic Click");
//                    item.setTitle(prefs.getString("langsave", ""));
//                    editor = prefs.edit();
//                    editor.putString("langsave", "English");
//                    editor.commit();
//                    loadLocale();
//
//
//                }
//                else if(item.getTitle().toString().equalsIgnoreCase("English"))
//                {
//                    lang = "en";
//                    Log.d("Response","English Click");
//                    item.setTitle(prefs.getString("langsave", ""));
//                    editor = prefs.edit();
//                    editor.putString("langsave", "Arabic");
//                    editor.commit();
//                    loadLocale();
//                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }



}