package ru.ssau.sanya.timetracker.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.R;

public class RecordListAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    //private Context context;
    private List<Record> records;
    private List<Category> categories;

    public RecordListAdapter( List<Record> records,List<Category> categories) {
       // this.context = context;
        this.records = records;
        this.categories = categories;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.records_list_layout, parent, false);

        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        Record record = records.get(position);

        holder.recordName.setText(record.getName());

        if (record.getIdCategory()!=null){
            for (Category category:categories){
                if (category.getId()==record.getIdCategory())
                    holder.recordIcon.setImageResource(category.getIcon());
            }
        }


        //todo fixIcons for category
        //holder.categoryIcon.setImageIcon(getResources(category.getIcon());

    }
    public Record getItem(int i) {
        return records.get(i);
    }
    @Override
    public int getItemCount() {
        return records.size();
    }

    public void updateRecordList(List<Record> recordList){
        this.records = recordList;
        notifyDataSetChanged();
    }
    public void updateCategoryList(List<Category> categoryList){
        this.categories = categoryList;
        notifyDataSetChanged();
    }
}
