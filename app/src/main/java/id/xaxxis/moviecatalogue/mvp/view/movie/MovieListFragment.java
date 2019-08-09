package id.xaxxis.moviecatalogue.mvp.view.movie;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.xaxxis.moviecatalogue.R;
import id.xaxxis.moviecatalogue.adapter.MovieAdapter;
import id.xaxxis.moviecatalogue.contract.MovieListContract;
import id.xaxxis.moviecatalogue.mvp.model.Data;
import id.xaxxis.moviecatalogue.mvp.presenter.MovieListPresenter;

import static id.xaxxis.moviecatalogue.utils.constant.KEY_DATA;
import static id.xaxxis.moviecatalogue.utils.constant.KEY_SAVE_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements MovieListContract.view {
    private final String TAG = MovieListFragment.class.getSimpleName();

    private ArrayList<Data> movieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private MovieListPresenter movieListPresenter;
    private RecyclerView mRecycleView;
    private ProgressBar progressBar;

    public MovieListFragment newInstance(ArrayList<Data> movieList){
        MovieListFragment movieFragment = new MovieListFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_DATA, movieList);

        movieFragment.setArguments(bundle);

        return movieFragment;
    }


    public MovieListFragment() {
        // Required empty public constructor
    }

    public void bindView(View view){
        progressBar = view.findViewById(R.id.pb_list_movie_loading);
        mRecycleView = view.findViewById(R.id.rv_item_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        bindView(view);
        Bundle bundle = getArguments();
        if (bundle != null){
            movieList = getArguments().getParcelableArrayList(KEY_DATA);
            this.setData(movieList);
            this.hideProgressBar();
        } else if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(KEY_SAVE_DATA);
            this.setData(movieList);
            this.hideProgressBar();
        } else {
            movieListPresenter = new MovieListPresenter(this);
            movieListPresenter.requestDataMovieApi(getActivity());
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_SAVE_DATA, movieList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData(List<Data> dataArrayList) {
        movieList.addAll(dataArrayList);
        movieAdapter = new MovieAdapter(getContext(), movieList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(movieAdapter);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(getActivity(), getResources().getString(R.string.api_communication_error), Toast.LENGTH_LONG).show();
    }
}
