package ru.ssau.sanya.timetracker.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.R;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    //private Context context;
    private List<Category> categories;

    public CategoryListAdapter(List<Category> categories) {

        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_list_layout, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.categoryName.setText(category.getName());
        holder.categoryIcon.setImageResource(category.getIcon());
        //todo fixIcons for category
        //holder.categoryIcon.setImageIcon(getResources(category.getIcon());

    }
    public Category getItem(int i) {
        return categories.get(i);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategoryList(List<Category> categoryList){
        this.categories = categoryList;
        notifyDataSetChanged();
    }
}
