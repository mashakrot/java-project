package com.example.deletethisshit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


// TabPagerAdapter controls switching between tabs
public class TabPagerAdapter extends FragmentStateAdapter {
    private String cityChoice;

    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity, String city) {
        super(fragmentActivity);
        this.cityChoice = city;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CityInfoFragment(cityChoice);
            case 1:
                return new StatisticsFragment(cityChoice);
//            case 2:
//                return new PicsFragment(cityChoice);
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Number of tabs
    }
}
