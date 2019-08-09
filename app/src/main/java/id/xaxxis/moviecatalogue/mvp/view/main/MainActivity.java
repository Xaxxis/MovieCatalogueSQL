package id.xaxxis.moviecatalogue.mvp.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.xaxxis.moviecatalogue.R;
import id.xaxxis.moviecatalogue.contract.MovieListContract;
import id.xaxxis.moviecatalogue.customView.CustomBottomNavigationView;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.presenter.MovieListPresenter;
import id.xaxxis.moviecatalogue.mvp.view.favorite.FavoriteFragment;
import id.xaxxis.moviecatalogue.mvp.view.movie.MovieListFragment;
import id.xaxxis.moviecatalogue.mvp.view.movie.TvListFragment;

import static id.xaxxis.moviecatalogue.utils.constant.KEY_MOVIE;
import static id.xaxxis.moviecatalogue.utils.constant.KEY_TV;

public class MainActivity extends AppCompatActivity implements CustomBottomNavigationView.OnNavigationItemSelectedListener, MovieListContract.view{

    private Context mContext;
    private ArrayList<Data> movieList = new ArrayList<>();
    private ArrayList<Data> tvList = new ArrayList<>();
    MovieListFragment movieListFragment = new MovieListFragment();




    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareActionBar();
        if(savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(KEY_MOVIE);
            tvList = savedInstanceState.getParcelableArrayList(KEY_TV);
            loadFragment(movieListFragment.newInstance(movieList));
        } else {
            MovieListPresenter presenter = new MovieListPresenter(this);
            presenter.requestDataMovieApi(this);
            presenter.requestDataTvApi(this);
            loadFragment(movieListFragment);
        }
        CustomBottomNavigationView customBottomNavigationView = findViewById(R.id.custom_nav);
        customBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE, movieList);
        outState.putParcelableArrayList(KEY_TV, tvList);
        super.onSaveInstanceState(outState);
    }

    @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        TvListFragment tvListFragment = new TvListFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        switch (menuItem.getItemId()) {
                case R.id.navigation_movie:
                    loadFragment(movieListFragment.newInstance(movieList));
                    return true;
                case R.id.navigation_favorite:
//                    Intent favorite = new Intent(this, FavoriteActivity.class);
//                    startActivity(favorite);
                    loadFragment(new FavoriteFragment());
                    return true;
                case R.id.navigation_tv:
                    loadFragment(tvListFragment.newInstance(tvList));
                    return true;
            }
            return false;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lang_english:
                changeLang("en");
                reloadContent();
                break;

            case R.id.lang_bahasa:
                changeLang("in");
                reloadContent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void reloadContent() {
        Intent reload = new Intent(this, MainActivity.class);
        startActivity(reload);
        finish();
    }


    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("Pref",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("Pref",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    @Override
    public void setData(List<Data> dataArrayList) {
        for (int i=0; i<dataArrayList.size(); i++){
            if (dataArrayList.get(i).getName() == null){
                movieList.addAll(dataArrayList);
                break;
            } else {
                tvList.addAll(dataArrayList);
                break;
            }
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

}
