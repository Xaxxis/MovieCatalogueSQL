package id.xaxxis.moviecatalogue.mvp.model;

import android.util.Log;

import id.xaxxis.moviecatalogue.api.ApiModule;
import id.xaxxis.moviecatalogue.api.service.ApiService;
import id.xaxxis.moviecatalogue.contract.MovieDetailContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.xaxxis.moviecatalogue.api.ApiModule.API_KEY;

public class DataDetailModel implements MovieDetailContract.Model {

    private final static String TAG = DataDetailModel.class.getSimpleName();
    @Override
    public void getMovieDetail(final OnFinishDetailListener onFinishDetailListener, int dataId, String locale) {
        ApiService detailApi = ApiModule.getClient().create(ApiService.class);
        Call<Data> call = detailApi.getMovieDetail(dataId, API_KEY, locale);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data data = response.body();
                Log.d(TAG, "Movie detail" +data.toString());
                onFinishDetailListener.onFinished(data);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "FAILURE : " +t.toString());
                onFinishDetailListener.onFailure(t);
            }
        });

    }

    @Override
    public void getTvDetail(final OnFinishDetailListener onFinishDetailListener, int dataId, String locale) {
        ApiService detailApi = ApiModule.getClient().create(ApiService.class);
        Call<Data> call = detailApi.getTvDetail(dataId, API_KEY, locale);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data data = response.body();
                Log.d(TAG, "Tv Detail " +data.toString());
                onFinishDetailListener.onFinished(data);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "FAILURE : " +t.toString());
                onFinishDetailListener.onFailure(t);
            }
        });

    }
}
