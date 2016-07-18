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
import info.androidhive.materialdesign.adapter.LeaveDataAdapter;
import info.androidhive.materialdesign.model.Attendence_history;
import info.androidhive.materialdesign.model.Leave_Model;

/**
 * Created by Arrowtec on 7/18/2016.
 */
public class MyLeaveList extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Leave_Model> leaveobject;
    LeaveDataAdapter adapter;
    public MyLeaveList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        leaveobject=new ArrayList<Leave_Model>() ;
        View rootView = inflater.inflate(R.layout.fragment_leave_list, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view2);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        leaveobject.add(new Leave_Model("A","V","D","D","D","D"));
        adapter=new LeaveDataAdapter(getActivity(),leaveobject);
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
