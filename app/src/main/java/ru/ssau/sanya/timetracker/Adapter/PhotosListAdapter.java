package ru.ssau.sanya.timetracker.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Photo;
import ru.ssau.sanya.timetracker.R;

public class PhotosListAdapter extends RecyclerView.Adapter<PhotosViewHolder> {
    private List<Photo> photos;

    public PhotosListAdapter(List<Photo> photos) {
        this.photos = photos;
    }


    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photos_list, parent, false);

        return new PhotosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        Picasso.with(holder.photo.getContext()).load(photos.get(position).getPath()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
    public Photo getItem(int i){
        return photos.get(i);
    }

    public void updatePhotosList(List<Photo> photoList){
        this.photos = photoList;
        notifyDataSetChanged();
    }



}
