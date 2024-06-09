package com.trex.markdown;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentSwipe extends FragmentStateAdapter {

    private int currentPosition = 0; // Initialize with a default position

    public FragmentSwipe(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Edit();
            default:
                return new Preview();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
