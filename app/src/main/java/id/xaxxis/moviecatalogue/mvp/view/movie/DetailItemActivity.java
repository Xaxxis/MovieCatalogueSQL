package id.xaxxis.moviecatalogue.mvp.view.movie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.xaxxis.moviecatalogue.R;
import id.xaxxis.moviecatalogue.api.ApiModule;
import id.xaxxis.moviecatalogue.contract.MovieDetailContract;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.model.DataGenre;
import id.xaxxis.moviecatalogue.mvp.presenter.MovieDetailPresenter;

import static id.xaxxis.moviecatalogue.utils.constant.KEY_DATA;
import static id.xaxxis.moviecatalogue.utils.constant.KEY_SAVE_DATA;

public class DetailItemActivity extends AppCompatActivity implements MovieDetailContract.View {

    private final static String TAG = DetailItemActivity.class.getSimpleName();

    private ImageView imgBackdrop, imgPoster, imgShowCategory;
    private TextView tvRate, tvTitle, tvReleaseDate, tvDescription, tvGenre;
    private MovieDetailPresenter movieDetailPresenter;
    private ProgressBar detProgressBar;
    private Data dataShow = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        prepareActionBar();
        bindView();
        Data data = getIntent().getParcelableExtra(KEY_DATA);
        reqDataApi(data);
    }

    private void prepareActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_SAVE_DATA, dataShow);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        dataShow = savedInstanceState.getParcelable(KEY_SAVE_DATA);
        this.setDataToDetailView(dataShow);
        this.hideProgressBar();
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void reqDataApi(Data data) {
        if (data != null) {
            movieDetailPresenter = new MovieDetailPresenter(this);
            if (data.getTitle() != null) {
                movieDetailPresenter.requestDataMovieDetail(data.getId(), this);
            } else {
                movieDetailPresenter.requestDataTvDetail(data.getId(), this);
            }
        }
    }

    public void bindView() {
        imgBackdrop = findViewById(R.id.img_det_backdrop);
        imgPoster = findViewById(R.id.img_det_cover);
        imgShowCategory = findViewById(R.id.ico_cat_show);
        tvRate = findViewById(R.id.tv_det_rating);
        tvTitle = findViewById(R.id.tv_det_title);
        tvReleaseDate = findViewById(R.id.tv_det_release);
        tvDescription = findViewById(R.id.tv_det_desc);
        detProgressBar = findViewById(R.id.pb_det);
        tvGenre = findViewById(R.id.tv_det_genre);
    }


    @Override
    public void showProgressBar() {
        detProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        detProgressBar.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataToDetailView(Data data) {
        dataShow = data;
        if (dataShow != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateOutput = new SimpleDateFormat("dd MMMM yyyy");
            if (dataShow.getTitle() != null) {
                tvTitle.setText(dataShow.getTitle() != null ? dataShow.getTitle() : "N/A");
                imgShowCategory.setImageResource(R.drawable.ic_hd_movie);
                try {
                    Date tripDate = dateInput.parse(dataShow.getReleaseDate());
                    tvReleaseDate.setText(String.valueOf(dateOutput.format(tripDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                tvTitle.setText(dataShow.getName() != null ? dataShow.getName() : "N/A");
                imgShowCategory.setImageResource(R.drawable.ic_tv_series);
                try {
                    Date tripDate = dateInput.parse(dataShow.getLastAirDate());
                    tvReleaseDate.setText(String.valueOf(dateOutput.format(tripDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            tvRate.setText(String.valueOf(dataShow.getVoteAverage()) != null ? String.valueOf(dataShow.getVoteAverage()) : "N/A");
            tvDescription.setText(dataShow.getOverview() != null ? dataShow.getOverview() : "N/A");

            List<DataGenre> genres = dataShow.getGenres();
            if(genres == null) {
                tvGenre.setText("N/A");
            }
            for (int i = 0; i < genres.size(); i++) {
                tvGenre.setText(tvGenre.getText() + genres.get(i).getName().toUpperCase() + " ");
            }

            Glide.with(this).load(ApiModule.IMAGE_BASE_URL + dataShow.getPosterPath()).apply(new RequestOptions().placeholder(R.drawable.ic_image_grey_24dp).error(R.drawable.ic_image_grey_24dp)).into(imgPoster);

            Glide.with(this).load(ApiModule.BACKDROP_BASE_URL + dataShow.getBackdropPath()).apply(new RequestOptions().placeholder(R.drawable.ic_image_grey_24dp).error(R.drawable.ic_image_grey_24dp)).into(imgBackdrop);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(this, getResources().getString(R.string.api_communication_error), Toast.LENGTH_LONG).show();
    }
}
