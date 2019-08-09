package id.xaxxis.moviecatalogue.mvp.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import id.xaxxis.moviecatalogue.contract.MovieListContract;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.model.DataModel;

public class MovieListPresenter implements MovieListContract.Presenter, MovieListContract.Model.OnFinishListener {

    private final String TAG = MovieListPresenter.class.getSimpleName();

    private MovieListContract.view movieListView;
    private MovieListContract.Model movieListModel;

    public MovieListPresenter(MovieListContract.view movieListView) {
        this.movieListView = movieListView;
        movieListModel = new DataModel();
    }

    @Override
    public void requestDataMovieApi(Context mContext) {
        if (movieListView !=null){
            Log.d(TAG, "proses req data movie to server");
            movieListView.showProgressBar();
        }
        Locale locale = mContext.getResources().getConfiguration().locale;
        movieListModel.getMovieList(this, locale.toLanguageTag());
    }

    @Override
    public void requestDataTvApi(Context mContext) {
        if(movieListView != null){
            Log.d(TAG, "Proses req data tv to server");
            movieListView.showProgressBar();
        }
        Locale locale = mContext.getResources().getConfiguration().locale;
        movieListModel.getTvList(this, locale.toLanguageTag());
    }


    @Override
    public void onFinished(List<Data> dataList) {
        Log.d(TAG, "Data size : " + dataList.size());
            movieListView.hideProgressBar();
        movieListView.setData(dataList);
    }


    @Override
    public void onFailure(Throwable throwable) {
        movieListView.onResponseFailure(throwable);
        if (movieListView != null) {
            movieListView.hideProgressBar();
        }
    }
}
