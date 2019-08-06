package id.xaxxis.moviecatalogue.mvp.presenter;

import android.content.Context;

import java.util.Locale;

import id.xaxxis.moviecatalogue.contract.MovieDetailContract;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.model.DataDetailModel;

public class MovieDetailPresenter implements MovieDetailContract.Presenter, MovieDetailContract.Model.OnFinishDetailListener {

    private MovieDetailContract.Model detailModel;
    private MovieDetailContract.View detailView;

    public MovieDetailPresenter(MovieDetailContract.View detailView) {
        this.detailView = detailView;
        detailModel = new DataDetailModel();
    }

    @Override
    public void onFinished(Data data) {
        if(detailView != null){
            detailView.hideProgressBar();
        }
        detailView.setDataToDetailView(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        detailView.onResponseFailure(throwable);
        if(detailView != null){
            detailView.hideProgressBar();
        }
    }

    @Override
    public void requestDataMovieDetail(int dataId, Context mContext) {
        if(detailView != null){
            detailView.showProgressBar();
        }
        Locale locale = mContext.getResources().getConfiguration().locale;
        detailModel.getMovieDetail(this, dataId, locale.toLanguageTag());
    }

    @Override
    public void requestDataTvDetail(int dataId, Context mContext) {
        if(detailView != null){
            detailView.showProgressBar();
        }
        Locale locale = mContext.getResources().getConfiguration().locale;
        detailModel.getTvDetail(this, dataId, locale.toLanguageTag());

    }
}
