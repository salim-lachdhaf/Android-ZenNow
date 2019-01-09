package com.zennow.zennow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class TabMore extends Fragment {

    private TextView profile;
    private TextView rating;
    private TextView logout;
    private TextView invoice;
    private Button issue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_more, container, false);
        profile=(TextView)view.findViewById(R.id.MenuEditProfile);
        rating=(TextView)view.findViewById(R.id.MenuRating);
        logout=(TextView)view.findViewById(R.id.MenuLogOut);
        invoice=(TextView)view.findViewById(R.id.MenuInvoice);
        issue=(Button)view.findViewById(R.id.MenuRapportIssue);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
