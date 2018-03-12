package ru.ssau.sanya.timetracker.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ru.ssau.sanya.timetracker.R;

public class PhotosViewHolder extends RecyclerView.ViewHolder {
    public ImageView photo;
    public PhotosViewHolder(View itemView) {
        super(itemView);
        photo = itemView.findViewById(R.id.photo_imageView);
    }
}
