package id.xaxxis.moviecatalogue.mvp.view.movie;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TvListFragment extends Fragment implements MovieListContract.view {

    private final static String TAG = TvListFragment.class.getSimpleName();

    private ProgressBar progressBar;
    private MovieListContract.Presenter presenter;
    private ArrayList<Data> dataTvList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private RecyclerView mRecycleView;


    public TvListFragment() {
        // Required empty public constructor
    }

    public void bindView(View view){
        progressBar = view.findViewById(R.id.pb_list_tv_loading);
        mRecycleView = view.findViewById(R.id.rv_tv_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_list, container, false);
        bindView(view);
        if (savedInstanceState != null) {
            dataTvList = savedInstanceState.getParcelableArrayList("data_key");
            this.setDataToAdapter(dataTvList);
            this.hideProgressBar();
        } else {
            presenter = new MovieListPresenter(this);
            presenter.requestDataTvApi(getActivity());
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("data_key", dataTvList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setDataToAdapter(List<Data> dataArrayList) {
        dataTvList.addAll(dataArrayList);
        movieAdapter = new MovieAdapter(getContext(), dataTvList);
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
        Log.d(TAG, throwable.toString());
        Toast.makeText(getActivity(), getResources().getString(R.string.api_communication_error), Toast.LENGTH_LONG).show();
    }
}
