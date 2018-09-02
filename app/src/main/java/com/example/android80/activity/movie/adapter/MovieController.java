package com.example.android80.activity.movie.adapter;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.OnModelBoundListener;
import com.airbnb.epoxy.OnModelClickListener;
import com.example.android80.activity.movie.view.MovieEmptyModel;
import com.example.android80.activity.movie.view.MovieItemModel_;
import com.example.android80.common.ListUtils;
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
        List<MovieEntity.SubjectsBean> list = ((MovieEntity)ListUtils.getItem(mData, 0)).getSubjects();

        boolean hasList = ListUtils.isNotEmpty(list);
        if (hasList) {
            for (MovieEntity.SubjectsBean subjectsBean : list) {
                if (subjectsBean == null) {
                    continue;
                }
                itemModel.onClickListener(this)
                        .subjectsBean(subjectsBean)
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
        MovieEntity.SubjectsBean subjectsBean = model.subjectsBean();
        if (listener != null && subjectsBean != null) {
            listener.onItemClick(subjectsBean);
        }
    }

    public interface Listener {
        void onItemClick(MovieEntity.SubjectsBean subjectsBean);
    }
}
