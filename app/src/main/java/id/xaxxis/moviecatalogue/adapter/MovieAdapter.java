package id.xaxxis.moviecatalogue.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.xaxxis.moviecatalogue.R;
import id.xaxxis.moviecatalogue.api.ApiModule;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.view.movie.DetailItemActivity;

import static id.xaxxis.moviecatalogue.utils.constant.KEY_DATA;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final Context mContext;
    private List<Data> dataList;

    public MovieAdapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateOutput = new SimpleDateFormat("dd MMMM yyyy");
        final Data movie = dataList.get(i);
        if(movie.getTitle() != null ){
            viewHolder.tvTitle.setText(movie.getTitle());
            try {
                Date movieDate = dateInput.parse(movie.getReleaseDate());
                viewHolder.tvRelease.setText(String.valueOf(dateOutput.format(movieDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            viewHolder.tvTitle.setText(movie.getName());
            try {
                Date airDate = dateInput.parse(movie.getLastAirDate());
                viewHolder.tvRelease.setText(String.valueOf(dateOutput.format(airDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        viewHolder.tvSimpleDesc.setText(movie.getOverview());
        Glide.with(mContext).load(ApiModule.IMAGE_BASE_URL + movie.getPosterPath()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if(movie.getTitle() != null){
                    viewHolder.movieProgressBar.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.movieProgressBar.setVisibility(View.VISIBLE);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if(movie.getTitle() != null){
                    viewHolder.movieProgressBar.setVisibility(View.GONE);
                } else {
                    viewHolder.movieProgressBar.setVisibility(View.GONE);
                }
                return false;
            }
        }).apply(new RequestOptions().placeholder(R.drawable.ic_image_grey_24dp).error(R.drawable.ic_image_grey_24dp)).into(viewHolder.imgCover);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieDetail = new Intent(mContext, DetailItemActivity.class);
                movieDetail.putExtra(KEY_DATA, movie);
                view.getContext().startActivity(movieDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSimpleDesc, tvRelease;
        ImageView imgCover;
        ProgressBar movieProgressBar, detailTvProgressBar, detailMovieProgressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_movie_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSimpleDesc = itemView.findViewById(R.id.tv_simple_desc);
            tvRelease = itemView.findViewById(R.id.tv_release);
            movieProgressBar = itemView.findViewById(R.id.pb_load_image);
            detailTvProgressBar = itemView.findViewById(R.id.pb_list_tv_loading);
            detailMovieProgressBar = itemView.findViewById(R.id.pb_list_movie_loading);
        }
    }
}
