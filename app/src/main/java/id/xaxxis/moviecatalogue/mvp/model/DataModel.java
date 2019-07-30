package id.xaxxis.moviecatalogue.mvp.model;

import android.util.Log;

import java.util.List;

import id.xaxxis.moviecatalogue.api.ApiModule;
import id.xaxxis.moviecatalogue.api.service.ApiService;
import id.xaxxis.moviecatalogue.contract.MovieListContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.xaxxis.moviecatalogue.api.ApiModule.API_KEY;


public class DataModel implements MovieListContract.Model {
    private final String TAG = DataModel.class.getSimpleName();

    @Override
    public void getMovieList(final OnFinishListener onFinishListener, String locale) {

        ApiService movieApi = ApiModule.getClient().create(ApiService.class);

        Call<DataListResponse> call = movieApi.getMovies(API_KEY, locale);
        call.enqueue(new Callback<DataListResponse>() {
            @Override
            public void onResponse(Call<DataListResponse> call, Response<DataListResponse> response) {
                List<Data> movies = response.body().getResults();
                Log.d(TAG, "onResponse: Number Movies Recieved " + movies.size());
                onFinishListener.onFinished(movies);

            }

            @Override
            public void onFailure(Call<DataListResponse> call, Throwable t) {
                Log.e(TAG, "FAILURE : " +t.toString());
                onFinishListener.onFailure(t);

            }
        });

    }

    @Override
    public void getTvList(final OnFinishListener onFinishListener, String locale) {
        ApiService tvApi = ApiModule.getClient().create(ApiService.class);


        Call<DataListResponse> call = tvApi.getTvs(API_KEY, locale);
        call.enqueue(new Callback<DataListResponse>() {
            @Override
            public void onResponse(Call<DataListResponse> call, Response<DataListResponse> response) {
                List<Data> tvs = response.body().getResults();
                Log.d(TAG, "Number TVs Recieved " +tvs.size());
                onFinishListener.onFinished(tvs);
            }

            @Override
            public void onFailure(Call<DataListResponse> call, Throwable t) {
                Log.e(TAG, "FAILURE : " +t.toString());
                onFinishListener.onFailure(t);
            }
        });

    }


}
