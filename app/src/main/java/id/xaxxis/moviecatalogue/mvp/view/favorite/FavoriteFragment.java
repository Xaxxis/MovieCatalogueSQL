package id.xaxxis.moviecatalogue.mvp.view.favorite;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.xaxxis.moviecatalogue.R;
import id.xaxxis.moviecatalogue.adapter.SectionPagerAdapter;
import id.xaxxis.moviecatalogue.contract.LoadFavoriteCallback;
import id.xaxxis.moviecatalogue.db.FavoriteHelpler;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.view.movie.MovieListFragment;
import id.xaxxis.moviecatalogue.mvp.view.movie.TvListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment  {

    private SectionPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;
    private FavoriteHelpler favoriteHelpler;
    private ArrayList<Data> favorites = new ArrayList<>();


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDb();
        if (savedInstanceState != null) {
            favorites = savedInstanceState.getParcelableArrayList("extra_key");
        }
        bindView(view);
        favoriteHelpler.getAllFavorites();

        showFragment();
    }

    private void loadDb() {
        favoriteHelpler = FavoriteHelpler.getInstance(getActivity());
        favoriteHelpler.open();
    }


    private void bindView(View view){
        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tl_tabs);
    }

    private void showFragment(){
        sectionPagerAdapter = new SectionPagerAdapter( getChildFragmentManager(), this);
        addFragments();
        viewPager.setAdapter(sectionPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void addFragments(){
        MovieListFragment movieListFragment = new MovieListFragment();
        TvListFragment tvListFragment = new TvListFragment();
        sectionPagerAdapter.addFragment(movieListFragment.newInstance(favorites), R.string.title_movie);
        sectionPagerAdapter.addFragment(tvListFragment.newInstance(favorites), R.string.title_tv);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<Data> datas) {
        favorites.addAll(datas);
    }


    private static class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<Data>> {

        private final WeakReference<FavoriteHelpler> weakFavoriteHelper;
        private final WeakReference<LoadFavoriteCallback.Model> weakCallback;

        private LoadFavoriteAsync(FavoriteHelpler favoriteHelpler, LoadFavoriteCallback.Model callback) {
            weakFavoriteHelper = new WeakReference<>(favoriteHelpler);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Data> doInBackground(Void... voids) {
            return weakFavoriteHelper.get().getAllFavorites();
        }

        @Override
        protected void onPostExecute(ArrayList<Data> dataArrayList) {
            super.onPostExecute(dataArrayList);
            weakCallback.get().postExecute(dataArrayList);
        }
    }



}
