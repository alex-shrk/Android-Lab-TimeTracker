package ru.ssau.sanya.timetracker.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ssau.sanya.timetracker.R;

public class RecordViewHolder extends RecyclerView.ViewHolder {
    public TextView recordName;
    public TextView recordDescr;
    public TextView recordTimestamp;
    public ImageView recordIcon;

    public RecordViewHolder(View itemView) {
        super(itemView);
        recordName = itemView.findViewById(R.id.list_record_name);
        recordDescr = itemView.findViewById(R.id.list_record_descr);
        recordTimestamp = itemView.findViewById(R.id.list_record_timestamp);
        recordIcon = itemView.findViewById(R.id.list_record_icon);
    }
}
