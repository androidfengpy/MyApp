package com.fengpy.myapp.dbdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fengpy.myapp.R;

import java.util.List;

/**
 * @ description:
 * @ time: 2017/8/23.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class TaskAdapter extends ArrayAdapter<TaskInfo> {

    private int resourceId;

    public TaskAdapter(Context context, int resource, List<TaskInfo> objects) {
        super(context, resource, objects);

        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskInfo taskInfo = getItem(position);
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.taskNameTV = (TextView) view.findViewById(R.id.tv_task_name);
            viewHolder.startTimeTV = (TextView) view.findViewById(R.id.tv_task_start_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.taskNameTV.setText(taskInfo.getTaskName());
        viewHolder.startTimeTV.setText(taskInfo.getStartTime());

        return view;
    }

    class ViewHolder{
        TextView taskNameTV;
        TextView startTimeTV;
    }
}
