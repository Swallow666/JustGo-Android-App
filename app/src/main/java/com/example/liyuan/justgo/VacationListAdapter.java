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


public class VacationListAdapter extends ArrayAdapter {

    private Context context;

    private List<Vacation> vacationList;


    public VacationListAdapter(@NonNull Context context, List<Vacation> vacationList) {
        super(context, R.layout.vacation_list_layout);
        this.vacationList = vacationList;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.vacation_list_layout, parent, false);

        Vacation vacationItem = vacationList.get(position);

        TextView vacation = (TextView) rowView.findViewById(R.id.item_vacationName);
        TextView startDate = (TextView) rowView.findViewById(R.id.item_startdate);
        TextView endDate = (TextView) rowView.findViewById(R.id.item_enddate);

        vacation.setText(vacationItem.getName());
        startDate.setText(vacationItem.getStartDate());
        endDate.setText(vacationItem.getEndDate());

        return rowView;
    }

    @Override
    public int getCount(){
        return vacationList !=null ? vacationList.size() : 0;
    }

    public void setData(List<Vacation> vacationItems) {
        this.vacationList = vacationItems;
    }
}
