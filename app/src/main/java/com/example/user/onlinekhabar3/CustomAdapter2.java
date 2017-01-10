package com.example.user.onlinekhabar3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 7/8/2016.
 */
public class CustomAdapter2 extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;
    String [] data;
    String [] date;
    String[] comment;
    int [] image;


    CustomAdapter2(Context context, String [] data, int [] image, String [] date,String[] comment){
        this.context = context;
        this.data = data;
        this.image = image;
        this.date = date;
        this.comment=comment;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*CustomAdapter(Context context, String [] data, int [] image){
        this.context = context;
        this.data = data;
        this.image = image;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv, tv1;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.event_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv1=(TextView) rowView.findViewById(R.id.textView2);
        holder.tv.setText(data[position]);
        holder.img.setImageResource(image[position]);
        holder.tv1.setText(date[position]);

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }

}

