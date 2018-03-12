package ru.ssau.sanya.timetracker.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ru.ssau.sanya.timetracker.R;

public class IconsViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public IconsViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
    }
}
