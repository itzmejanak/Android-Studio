package com.trex.musicplus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.button.MaterialButton;

public class PageOne extends Fragment implements View.OnClickListener {

    MaterialButton getStarted;

    PageTwo secondFragment = new PageTwo();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_one, container, false);


        //Add your code here
        getStarted = view.findViewById(R.id.next);
        getStarted.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.FrameLayoutOne, new PageTwo());
            transaction.addToBackStack(null); // Add PageOne to the back stack
            transaction.commit();
        }
    }

}