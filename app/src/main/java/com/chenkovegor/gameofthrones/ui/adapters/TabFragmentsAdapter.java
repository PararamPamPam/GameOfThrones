package com.chenkovegor.gameofthrones.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.chenkovegor.gameofthrones.data.storage.models.House;
import com.chenkovegor.gameofthrones.ui.fragments.ListCharactersFragment;
import com.chenkovegor.gameofthrones.utils.ConstantManager;

import java.util.List;

public class TabFragmentsAdapter extends FragmentStatePagerAdapter {

    private final static String TAG = ConstantManager.TAG_PREFIX + "TabFragmentsAdapter";
    private List<House> mHouses;
    private Context mContext;

    public TabFragmentsAdapter(FragmentManager fm, List<House> houses, Context context) {
        super(fm);
        mContext = context;
        mHouses = houses;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mHouses.get(position).getShortName();
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem " + position);
        ListCharactersFragment fragment = new ListCharactersFragment();
        fragment.setData(mHouses.get(position), mContext);
        return fragment;
    }

    @Override
    public int getCount() {
        return mHouses.size();
    }
}
