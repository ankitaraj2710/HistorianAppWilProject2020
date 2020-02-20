package com.example.historian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainMenu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fisrt, container, false);

        ImageButton profile = (ImageButton) view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), profile.class);
                startActivity(in);
            }

        });
        ImageButton payment = (ImageButton) view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), payment.class);
                startActivity(in);
            }

        });
        ImageButton museum = (ImageButton) view.findViewById(R.id.museum);
        museum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(getActivity(), Demo.class);
                startActivity(in);
            }

        });




        return view;

    }
}