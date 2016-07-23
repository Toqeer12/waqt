package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DBHandle.DatabaseHandler;
import info.androidhive.materialdesign.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pass = "emailKey";
    public static final String emi = "nameKey";
    SharedPreferences sharedpreferences;
    public static String emii,password;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
     public static String profile ,comp_logo,EmployeeId;
    public static String name;
    TextView txt;
    ImageView img;
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

        //checkConnection();
        password = sharedpreferences.getString(pass, null);
        emii = sharedpreferences.getString(emi, null);
        img=(ImageView)findViewById(R.id.image);
        txt=(TextView)findViewById(R.id.text);
        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
/*
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
*/

        // Reading all contacts
/*        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
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
                        comp_logo = person.getString("Company_image");
                        profile=person.getString("Emp_image");
                        EmployeeId = person.getString("EmployeeId");


                        Glide.with(getApplicationContext())
                                .load(profile.replace("https", "http"))
                                .asBitmap()
                                .fitCenter()
                                .into(img);
                        Log.d("Response", name);
                        txt.setText(person.getString("NameEn"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            Toast.makeText(getApplicationContext(),"Emi"+emii+"Password"+password,Toast.LENGTH_LONG).show();
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
        super.onBackPressed();
    }
}