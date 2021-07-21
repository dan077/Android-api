package com.example.api_oxford_dic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class definicionAdapter extends ArrayAdapter<Definicion> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private String titulo;


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView Titulo;
        TextView desc;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public definicionAdapter(Context context, int resource, ArrayList<Definicion> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String titulo = getItem(position).getTitulo();
        String desc = getItem(position).getDefinicion();


        //Create the person object with the information
        //Mercado estudiante = new Mercado(cedula,nombre,barrio);
        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.Titulo = (TextView) convertView.findViewById(R.id.Titulo);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.Titulo.setText(titulo);
        holder.desc.setText(desc);


        return convertView;
    }



}