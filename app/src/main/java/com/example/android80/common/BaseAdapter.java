package com.example.android80.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private LayoutInflater mInflater;

    public BaseAdapter(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public LayoutInflater getInflater() {
        return this.mInflater;
    }

}
