package ru.ssau.sanya.timetracker.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ssau.sanya.timetracker.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryName;
    public ImageView categoryIcon;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.list_category_name);
        categoryIcon = itemView.findViewById(R.id.list_category_icon);
    }
}
