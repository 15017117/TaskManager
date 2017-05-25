package com.myapplicationdev.android.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 15017117 on 25/5/2017.
 */

public class taskAdapter extends ArrayAdapter<Task>{
    Context context;
    ArrayList<Task> tasks;
    int resource;

    public taskAdapter(Context context,int resource,ArrayList<Task>tasks){
      super(context,resource,tasks);
        this.context = context;
        this.tasks = tasks;
        this.resource = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource,parent,false);
        TextView tvId = (TextView)rowView.findViewById(R.id.tvId);

        TextView tvName = (TextView)rowView.findViewById(R.id.tvName);
        TextView tvDesc = (TextView)rowView.findViewById(R.id.tvDesc);
        Task task = tasks.get(position);

        tvId.setText(String.valueOf(task.getId()));

        tvName.setText(task.getTaskName());
        tvDesc.setText(task.getTaskDesc());
        return rowView;
    }

}
