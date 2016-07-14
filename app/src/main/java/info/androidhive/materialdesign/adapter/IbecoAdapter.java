package info.androidhive.materialdesign.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import info.androidhive.materialdesign.model.login_model_user;

/**
 * Created by Toqeer on 20/06/2016.
 */
public class IbecoAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    private ArrayList<login_model_user> ibeacon;

    public IbecoAdapter(ArrayList<login_model_user> ibeacon, Context context) {
        this.ibeacon=ibeacon;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return ibeacon.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }





}
