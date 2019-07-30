package id.xaxxis.moviecatalogue.contract;

import android.content.Context;

import java.util.List;

import id.xaxxis.moviecatalogue.mvp.model.Data;

public interface MovieListContract {

    interface Model {
        interface OnFinishListener{
            void onFinished(List<Data> dataList);
            void onFailure(Throwable throwable);
        }

        void getMovieList(OnFinishListener onFinishListener, String locale);
        void getTvList(OnFinishListener onFinishListener, String locale);
    }

    interface view {

        void setDataToAdapter(List<Data> dataArrayList);

        void showProgressBar();

        void hideProgressBar();

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void requestDataMovieApi(Context mContext);
        void requestDataTvApi(Context mContext);

    }
}
