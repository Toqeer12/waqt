package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.DataAdapter;
import info.androidhive.materialdesign.model.Attendence_history;

/**
 * Created by Arrowtec on 7/13/2016.
 */
public class Attendence extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Attendence_history> attendence;
    DataAdapter adapter;
    public Attendence() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        attendence=new ArrayList<Attendence_history>();
        View rootView = inflater.inflate(R.layout.fragment_attendence, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        attendence.add(new Attendence_history("A","V","D"));
        adapter=new DataAdapter(getActivity(),attendence);
        recyclerView.setAdapter(adapter);



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