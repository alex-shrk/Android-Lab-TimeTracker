package ru.ssau.sanya.timetracker.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.R;

public class IconsListAdapter extends RecyclerView.Adapter<IconsViewHolder> {
    private  Integer[] ids;

    public IconsListAdapter(Integer[] ids) {
        this.ids = ids;
    }


    @Override
    public IconsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icons_list, parent, false);

        return new IconsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IconsViewHolder holder, int position) {
        holder.icon.setImageResource(ids[position]);
    }

    @Override
    public int getItemCount() {
        return ids.length;
    }
    public int getItem(int i){
        return ids[i];
    }


}
