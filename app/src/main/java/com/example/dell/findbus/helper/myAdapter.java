package com.example.dell.findbus.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.findbus.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by root on 11/11/17.
 */
public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<bus_slot> slots;
    Context context;
    LayoutInflater l;

    public myAdapter(Context c, ArrayList<bus_slot> t) {
        this.context = c;
        this.slots = t;
        l=LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = l.inflate(R.layout.bus_slot, parent,false);
        slot_holder vh = new slot_holder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        slot_holder holder=(slot_holder) holder1;
        holder.bus_no.setText(slots.get(position).bus_no);
        holder.driver_email.setText(slots.get(position).driver_email);

        holder.driver_name.setText(slots.get(position).driver_name);
        holder.strt_time.setText(slots.get(position).time);
        holder.from.setText(slots.get(position).from);
        holder.to.setText(slots.get(position).to);
        holder.contact.setText(slots.get(position).driver_contact);
        holder.note.setText(slots.get(position).note);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 120;
        int targetHeight = 120;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }


}
