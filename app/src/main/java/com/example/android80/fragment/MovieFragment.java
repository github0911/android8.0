package com.example.android80.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyRecyclerView;
import com.example.android80.R;
import com.example.android80.activity.movie.adapter.MovieController;
import com.example.android80.api.common.HttpMethod;
import com.example.android80.entity.MovieEntity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements MovieController.Listener {

    private OnFragmentInteractionListener mListener;

    private EpoxyRecyclerView recyclerView;
    private MovieController controller;

    public MovieFragment() {
    }

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        initView(view);
        getMovie();
        return view;
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        controller = new MovieController(this);
        recyclerView.setController(controller);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getMovie() {

        Observer<MovieEntity> observer = new Observer<MovieEntity>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable  = d;
                if (!d.isDisposed()) {
                    Logger.d("method %s", "onSubscribe");
                }
            }

            @Override
            public void onNext(MovieEntity movieEntity) {
                Logger.d("method %s", "onNext");
                List list = new ArrayList();
                list.add(movieEntity);
                controller.setData(list);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d(e.getMessage());
            }

            @Override
            public void onComplete() {

                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                Logger.d("method %s", "onComplete");
            }
        };

        HttpMethod.getInstance().getMovie(observer, 0, 1);

    }

    @Override
    public void onItemClick(MovieEntity.SubjectsBean subjectsBean) {
        Logger.d("onItemClick");
        if (mListener != null) {
            mListener.onItemClick(subjectsBean);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onItemClick(MovieEntity.SubjectsBean subjectsBean);
    }
}
