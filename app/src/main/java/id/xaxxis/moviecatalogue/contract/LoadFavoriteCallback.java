package id.xaxxis.moviecatalogue.contract;

import java.util.ArrayList;

import id.xaxxis.moviecatalogue.mvp.model.Data;

public interface LoadFavoriteCallback {

    interface Model {
        void preExecute();
        void postExecute(ArrayList<Data> datas);

    }

//    interface view {
//
//        void setDataToAdapter(List<Data> dataArrayList);
//
//        void showProgressBar();
//
//        void hideProgressBar();
//
//        void onResponseFailure(Throwable throwable);
//    }
//
//    interface Presenter {
//        void requestDataMovieApi(Context mContext);
//        void requestDataTvApi(Context mContext);
//
//    }
}
