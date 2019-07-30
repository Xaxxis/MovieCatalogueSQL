package id.xaxxis.moviecatalogue.api.service;

import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.model.DataListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("discover/movie")
    Call<DataListResponse> getMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("discover/tv")
    Call<DataListResponse> getTvs(@Query("api_key")String apiKey, @Query("language") String language);

    @GET("movie/{data_id}")
    Call<Data> getMovieDetail(@Path("data_id") int dataId, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("tv/{data_id}")
    Call<Data> getTvDetail(@Path("data_id") int dataId, @Query("api_key") String apiKey, @Query("language") String language);

}
