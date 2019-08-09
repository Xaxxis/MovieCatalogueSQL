package id.xaxxis.moviecatalogue.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import id.xaxxis.moviecatalogue.mvp.view.favorite.FavoriteFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final FavoriteFragment mContext;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Integer> titles = new ArrayList<>();


    public SectionPagerAdapter(FragmentManager fm, FavoriteFragment mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(titles.get(position));
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, Integer title){
        fragments.add(fragment);
        titles.add(title);

    }

}
