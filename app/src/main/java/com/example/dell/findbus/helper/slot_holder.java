package com.example.dell.findbus.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.findbus.R;


/**
 * Created by root on 11/11/17.
 */
public class slot_holder extends RecyclerView.ViewHolder{
    TextView bus_no,driver_name,strt_time,from,to,contact,note,driver_email;


    slot_holder(View v) {
        super(v);
        bus_no = (TextView) v.findViewById(R.id.bus_no);
        driver_name = (TextView) v.findViewById(R.id.driver_name);
        strt_time=(TextView)v.findViewById(R.id.start_time);
        from=(TextView)v.findViewById(R.id.from);
        to=(TextView)v.findViewById(R.id.to);
        note=(TextView)v.findViewById(R.id.note);
        contact=(TextView)v.findViewById(R.id.contact);
        driver_email=(TextView)v.findViewById(R.id.driver_email);
    }
}

