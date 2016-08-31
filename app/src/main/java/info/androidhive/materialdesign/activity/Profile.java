package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;

/**
 * Created by Ravi on 29/07/15.
 */
public class Profile extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    TextView name;
    ImageView imageprofile;
    TextView nick_name,dob,maritial,otherdetail,dlno,dlexp,highqual,namepr,email,country,desg,depart,comp,gender,empcode,ssn,sin,levl,milserv,jdate,emptyp,grd,shift,whour,sfstart,sfend,gdval;
    View rootView;
     ProgressDialog progressDialog;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
          rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        name=(TextView)rootView.findViewById(R.id.name);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        imageprofile=(ImageView)rootView.findViewById(R.id.profile_image);
        //  Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();

            name.setText(MainActivity.name);
        InitalField();


        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        Glide.with(getActivity())
                .load(MainActivity.profile.replace("https", "http"))
                .asBitmap()
                .fitCenter()
                .into(imageprofile);
        // Inflate the layout for this fragment




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        raw_function();
                        //  onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();

                    }
                }, 3000);


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





    public void raw_function()
    {

        String json_array = sharedpreferences.getString("jsonArray", null);

        try {
            JSONArray jsoArray=new JSONArray(json_array);
            for (int i = 0; i < jsoArray.length(); i++) {
                JSONObject person = (JSONObject) jsoArray
                        .get(i);

                String emailemp = person.getString("EmailID");
                String name =person.getString("NameEn");
                String othde =person.getString("OtherDetails");
                String DOB =person.getString("DOB");
                String maritalstatus=person.getString("MaritalStatus");
                String highestqual=person.getString("HighestQualification");
                String dlnumber=person.getString("DLNumber");
                String dlexpiry=person.getString("DLExpiry");
                String designation=person.getString("DesignationName");
                String departmentName=person.getString("DepartmentNameEn");
                String countryemp=person.getString("CountryNameEn");
                String companyId=person.getString("CompanyNameEn");
                String ssnemp=person.getString("SSN");
                String sinemp=person.getString("SIN");
                String gend=person.getString("Gender");
                String miltryserv=person.getString("MilitaryService");
//                String levlemp=person.getString("LevelID");
                String employecode=person.getString("EmployeeCode");
                String joinDate=person.getString("JoiningDate");
                String employeTyp=person.getString("EmployeeType");
//                String gradId=person.getString("GradeId");
//                String shfitId=person.getString("EmployeeShiftID");
//                String workhour=person.getString("WorkingHours");
                String shfitstart=person.getString("ShiftStart");
                String shiftend=person.getString("ShiftEnd");
                String gradval=person.getString("GradeValue");


                nick_name.setText(name);
                dob.setText(DOB);
                maritial.setText(maritalstatus);
                highqual.setText(highestqual);
                dlno.setText(dlnumber);
                dlexp.setText(dlexpiry);
                 email.setText(emailemp);
                empcode.setText(employecode);
                gender.setText(gend);
                desg.setText(designation);
                depart.setText(departmentName);
                country.setText(countryemp);
                comp.setText(companyId);
                ssn.setText(ssnemp);
                sin.setText(sinemp);
                milserv.setText(miltryserv);
//                grd.setText(gradId);
                sfstart.setText(shfitstart);
                sfend.setText(shiftend);
                jdate.setText(joinDate);
//                levl.setText(levlemp);
                emptyp.setText(employeTyp);
                gdval.setText(gradval);
//                whour.setText(workhour);
//                shift.setText(shfitId);





            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

public void InitalField()
{
    nick_name=(TextView)rootView.findViewById(R.id.nick_name);
    dob=(TextView)rootView.findViewById(R.id.dob);
    maritial=(TextView)rootView.findViewById(R.id.marital);
    otherdetail=(TextView)rootView.findViewById(R.id.othdetail);
    dlno=(TextView)rootView.findViewById(R.id.dlno);
    dlexp=(TextView)rootView.findViewById(R.id.dlexp);
    highqual=(TextView)rootView.findViewById(R.id.highqual);
    email=(TextView)rootView.findViewById(R.id.email);
    empcode=(TextView)rootView.findViewById(R.id.employecode);
    gender=(TextView)rootView.findViewById(R.id.gend);
    comp=(TextView)rootView.findViewById(R.id.compId);
    depart=(TextView)rootView.findViewById(R.id.dept);
    desg=(TextView)rootView.findViewById(R.id.designation);
    country=(TextView)rootView.findViewById(R.id.country);
    ssn=(TextView)rootView.findViewById(R.id.ssn);
    sin=(TextView)rootView.findViewById(R.id.sin);
    milserv=(TextView)rootView.findViewById(R.id.mservice);
    jdate=(TextView)rootView.findViewById(R.id.jdate);
    grd=(TextView)rootView.findViewById(R.id.grde);
    sfstart=(TextView)rootView.findViewById(R.id.sfstart);
    sfend=(TextView)rootView.findViewById(R.id.sfend);
    levl=(TextView)rootView.findViewById(R.id.level);
    whour=(TextView)rootView.findViewById(R.id.whour);
    emptyp=(TextView)rootView.findViewById(R.id.emptyp);
    shift=(TextView)rootView.findViewById(R.id.shfit);
    gdval=(TextView)rootView.findViewById(R.id.grdval);

}


}
