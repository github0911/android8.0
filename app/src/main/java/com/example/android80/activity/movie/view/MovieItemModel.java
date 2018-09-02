package com.example.android80.activity.movie.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.example.android80.R;
import com.example.android80.common.ListUtils;
import com.example.android80.entity.MovieEntity;
import com.google.gson.Gson;

@EpoxyModelClass
public abstract class MovieItemModel extends EpoxyModelWithHolder<MovieItemModel.Holder> {

    @EpoxyAttribute
    @Nullable
    MovieEntity.SubjectsBean subjectsBean;

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener onClickListener;

    @Override
    protected int getDefaultLayout() {
        return R.layout.layout_movie_item;
    }

    @Override
    public void bind(@NonNull Holder holder) {
        if (subjectsBean == null) {
            return;
        }

        holder.tvChineseTitle.setText(subjectsBean.getTitle());
        holder.tvOriginalTitle.setText(subjectsBean.getOriginal_title());
        holder.tvCollectCount.setText(subjectsBean.getCollect_count() + "");
        holder.tvGenres.setText(new Gson().toJson(subjectsBean.getGenres()));
        holder.tvYear.setText(subjectsBean.getYear());

        if (subjectsBean.getImages() != null) {
            Glide.with(holder.ivImage)
                    .load(subjectsBean.getImages().getLarge())
                    .into(holder.ivImage);
        }
        holder.ivImage.setOnClickListener(onClickListener);
        holder.tvLink.setText(subjectsBean.getAlt());
        holder.tvLink.setOnClickListener(onClickListener);

    }

    @Override
    public void unbind(@NonNull Holder holder) {
        super.unbind(holder);
        holder.ivImage.setOnClickListener(null);
        holder.tvLink.setOnClickListener(null);
    }

    public static class Holder extends EpoxyHolder {

        TextView tvChineseTitle;
        TextView tvOriginalTitle;
        TextView tvCollectCount;
        TextView tvGenres;
        TextView tvSubtype;
        TextView tvYear;
        ImageView ivImage;
        TextView tvLink;

        @Override
        protected void bindView(View itemView) {
            tvChineseTitle = itemView.findViewById(R.id.tv_chinese_title);
            tvOriginalTitle = itemView.findViewById(R.id.tv_original_title);
            tvCollectCount = itemView.findViewById(R.id.tv_collect_count);
            tvGenres = itemView.findViewById(R.id.tv_genres);
            tvSubtype = itemView.findViewById(R.id.tv_subtype);
            tvYear = itemView.findViewById(R.id.tv_year);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvLink = itemView.findViewById(R.id.tv_link);
        }
    }
}
