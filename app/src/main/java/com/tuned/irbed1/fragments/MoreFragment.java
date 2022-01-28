package com.tuned.irbed1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuned.irbed1.R;
import com.tuned.irbed1.activites.LoginActivity;
import com.tuned.irbed1.activites.NotificationActivity;
import com.tuned.irbed1.activites.ProfileActivity;
import com.tuned.irbed1.activites.SplashActivity;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;


public class MoreFragment extends Fragment
{

    TextView profile;
    TextView notification;
    TextView logout;


    public MoreFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        profile = view.findViewById(R.id.profile);
        notification = view.findViewById(R.id.notification);
        logout = view.findViewById(R.id.logout);


        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (General.getPreferenceValue(getActivity(), ConstantValue.ID, "").equals(""))
                {
                    General.goToActivity(getActivity(), LoginActivity.class);
                } else
                {
                    General.goToActivity(getActivity(), ProfileActivity.class);
                }
            }
        });
        notification.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                General.goToActivity(getActivity(), NotificationActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                General.addToSharedPreference(getActivity(), ConstantValue.ID, "");
                getActivity().finishAffinity();
                General.goToActivity(getActivity(), SplashActivity.class);
            }
        });


        return view;
    }
}