package com.trex.musicplus;

import static android.text.TextUtils.replace;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class PageThree extends Fragment implements View.OnClickListener{
    Button buttonNext;
    TextView buttonBack;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_three, container, false);

        buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        buttonBack = view.findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(this);
        imageView = view.findViewById(R.id.imageView4);
        imageView.setOnClickListener(this);
//        buttonNext = view.findViewById(R.id.backButton);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BackButton) {
            getParentFragmentManager().popBackStack();
        } else if (v.getId() == R.id.buttonNext) {
            Intent intent = new Intent(getActivity(), MainActivityTwo.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageView4) {
            ImageView imageView = (ImageView) v; // Cast the View to ImageView
            Integer tag = (Integer) imageView.getTag(); // Get the current tag
            if (tag != null && tag == R.drawable.switch_off) {
                imageView.setImageResource(R.drawable.switch_on);
                imageView.setTag(R.drawable.switch_on); // Update the tag to the new resource
            } else {
                imageView.setImageResource(R.drawable.switch_off);
                imageView.setTag(R.drawable.switch_off); // Update the tag to the new resource
            }
        }
    }

}