package com.trex.musicplus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragmentThree extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_three, container, false);

        TextView feedbackSection = view.findViewById(R.id.feedbackSection);
        TextView donateSection = view.findViewById(R.id.donateSection);
        ImageView linkdin = view.findViewById(R.id.linkdin);
        ImageView insta = view.findViewById(R.id.insta);
        ImageView github = view.findViewById(R.id.github);
        TextView website = view.findViewById(R.id.website);

        website.setOnClickListener(this);
        feedbackSection.setOnClickListener(this);
        donateSection.setOnClickListener(this);
        linkdin.setOnClickListener(this);
        insta.setOnClickListener(this);
        github.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.feedbackSection) {
            sendFeedback();
        } else if (id == R.id.donateSection) {
            openDonationPage();
        } else if (id == R.id.linkdin) {
            openLinkdinProfile();
        } else if (id == R.id.insta) {
            openInstagramProfile();
        } else if (id == R.id.github) {
            openGithubProfile();
        }else if (id == R.id.website) {
            openWebsite();
        }
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:itzmejanak@gmail.com"));
        startActivity(intent);
    }

    private void openWebsite() {
        WebsiteLaunch fragment = new WebsiteLaunch();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutThree, fragment)
                .addToBackStack(null)
                .commit();
    }


    private void openDonationPage() {
        String url = "https://buymeacoffee.com/atokvedjanu";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void openLinkdinProfile() {
        String url = "https://np.linkedin.com/in/janak-devkota-5a7679236";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void openInstagramProfile() {
        String url = "https://www.instagram.com/t_rex.dev?igsh=cG1uNDNpcmR6cnR4";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void openGithubProfile() {
        String url = "https://github.com/itzmejanak";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
