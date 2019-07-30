package id.xaxxis.moviecatalogue.contract;

import android.content.Context;

import id.xaxxis.moviecatalogue.mvp.model.Data;

public interface MovieDetailContract {
    interface Model {
        interface OnFinishDetailListener{
            void onFinished(Data data);
            void onFailure(Throwable throwable);
        }
        void getMovieDetail(OnFinishDetailListener onFinishDetailListener, int dataId, String locale);
        void getTvDetail(OnFinishDetailListener onFinishDetailListener, int dataId, String locale);
    }

    interface View {

        void showProgressBar();
        void hideProgressBar();
        void setDataToDetailView(Data data);
        void onResponseFailure();

    }

    interface Presenter {
        void requestDataMovieDetail(int dataId, Context mContext);
        void requestDataTvDetail(int dataId, Context mContext);
    }
}
