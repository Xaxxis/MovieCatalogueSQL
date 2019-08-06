package id.xaxxis.moviecatalogue.mvp.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Locale;

import id.xaxxis.moviecatalogue.R;
import id.xaxxis.moviecatalogue.adapter.SectionPagerAdapter;
import id.xaxxis.moviecatalogue.mvp.view.movie.MovieListFragment;
import id.xaxxis.moviecatalogue.mvp.view.movie.TvListFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;
    private SectionPagerAdapter sectionPagerAdapter;
    private Context mContext;

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
        bindView();
        showFragment();
    }

    private void bindView(){
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tl_tabs);
    }


    private void showFragment(){
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), this);
        addFragments();
        viewPager.setAdapter(sectionPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void addFragments(){
        sectionPagerAdapter.addFragment(new MovieListFragment(), R.string.title_movie);
        sectionPagerAdapter.addFragment(new TvListFragment(), R.string.title_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.lang_english :
                changeLang("en");
                reloadContent();
                break;

            case R.id.lang_bahasa :
                changeLang("in");
                reloadContent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void reloadContent(){
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
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("Pref",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

}
