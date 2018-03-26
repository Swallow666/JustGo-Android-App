package com.example.liyuan.justgo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class EventListAdapter extends ArrayAdapter {

    private Context context;

    private List<Event> eventList;


    public EventListAdapter(@NonNull Context context, List<Event> eventList) {
        super(context, R.layout.event_list_layout);
        this.eventList = eventList;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.event_list_layout, parent, false);

        Event eventItem = eventList.get(position);


        TextView eventName = (TextView) rowView.findViewById(R.id.event_name);
        TextView eventDate = (TextView) rowView.findViewById(R.id.event_date);
        TextView eventTime = (TextView) rowView.findViewById(R.id.event_time);


        eventName.setText(eventItem.getName());
        eventDate.setText(eventItem.getDate());
        eventTime.setText(eventItem.getTime());

        return rowView;
    }



    @Override
    public int getCount(){
        return eventList !=null ? eventList.size() : 0;
    }


    public void setData(List<Event> events) {
        this.eventList = events;
    }
}
