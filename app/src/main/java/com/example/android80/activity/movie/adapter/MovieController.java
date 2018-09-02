package com.example.android80.activity.movie.adapter;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.OnModelBoundListener;
import com.airbnb.epoxy.OnModelClickListener;
import com.example.android80.activity.movie.view.MovieEmptyModel;
import com.example.android80.activity.movie.view.MovieItemModel;
import com.example.android80.activity.movie.view.MovieItemModel_;
import com.example.android80.entity.MovieEntity;

import java.util.List;

public class MovieController extends EpoxyController
        implements OnModelClickListener<MovieItemModel_, MovieItemModel_.Holder>,
        OnModelBoundListener<MovieItemModel_, MovieItemModel_.Holder>{

    @AutoModel
    MovieEmptyModel emptyModel;

    @AutoModel
    MovieItemModel_ itemModel;

    private List<MovieEntity> mData;

    Listener listener;

    public MovieController(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void buildModels() {
        boolean hasList = mData != null && !mData.isEmpty();
        if (hasList) {
            for (MovieEntity entity : mData) {
                if (entity == null) {
                    continue;
                }
                itemModel.onClickListener(this)
                        .movieEntity(entity)
                        .addTo(this);
            }
        }

        emptyModel.addIf(!hasList, this);
    }

    public void setData(List<MovieEntity> mData) {
        this.mData = mData;
        requestModelBuild();
    }

    @Override
    public void onModelBound(MovieItemModel_ model, MovieItemModel_.Holder view, int position) {

    }

    @Override
    public void onClick(MovieItemModel_ model, MovieItemModel_.Holder parentView, View clickedView, int position) {
        MovieEntity entity = model.movieEntity();
        if (listener != null && entity != null) {
            listener.onItemClick(entity);
        }
    }

    public interface Listener {
        void onItemClick(MovieEntity entity);
    }
}
