package com.trex.musicplus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebsiteLaunch extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website_launch, container, false);

        WebView webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://janakdevkota.com.np/");

        // Set WebView to full-screen mode
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);

        return view;
    }
}
