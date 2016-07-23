package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    TextView name;
    ImageView imageprofile;
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        name=(TextView)rootView.findViewById(R.id.name);
        imageprofile=(ImageView)rootView.findViewById(R.id.profile_image);
        //  Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
            name.setText(MainActivity.name);
        Glide.with(getActivity())
                .load(MainActivity.profile.replace("https", "http"))
                .asBitmap()
                .fitCenter()
                .into(imageprofile);
        // Inflate the layout for this fragment
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
}
