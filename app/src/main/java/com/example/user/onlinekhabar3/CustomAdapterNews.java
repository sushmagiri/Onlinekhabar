package com.example.user.onlinekhabar3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class CustomAdapterNews extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;
    ArrayList<Entity1> centityArrayList;

    CustomAdapterNews(Context context, ArrayList<Entity1> centityArrayList){
        this.context = context;
        this.centityArrayList = centityArrayList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return centityArrayList.size();
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

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.layout_design, null);
        holder.tv=(TextView) rowView.findViewById(R.id.text1);
        holder.tv1=(TextView) rowView.findViewById(R.id.text2);
        holder.tv.setText(centityArrayList.get(position).getName());
        holder.tv1.setText(centityArrayList.get(position).getComment());
        return rowView;
    }

}
