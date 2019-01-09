package com.zennow.zennow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;


public class TabSchedule extends Fragment {

    private CalendarView calendarView;
    private Toolbar toolbar;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_sheduling, container, false);
        calendarView=(CalendarView)view.findViewById(R.id.CalendarView);
        try {
            calendarView.setMinDate(System.currentTimeMillis()-2000);
        }catch(Exception e){}
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
