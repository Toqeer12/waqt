/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Apply_Leave;

/**
 * Created by Arrowtec on 8/14/2016.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;

    ArrayList<Apply_Leave> objects;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext,   ArrayList<Apply_Leave> objects) {
        this.context = applicationContext;

        this.objects = objects;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom, null);

        TextView names = (TextView) view.findViewById(R.id.textView);

        names.setText(objects.get(i).getLeaveType());
        return view;
    }
}
