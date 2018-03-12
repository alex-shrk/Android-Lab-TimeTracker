package ru.ssau.sanya.timetracker.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ru.ssau.sanya.timetracker.Adapter.RecordListAdapter;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.RecyclerTouchListener;
import ru.ssau.sanya.timetracker.ViewModel.CategoryListViewModel;
import ru.ssau.sanya.timetracker.ViewModel.RecordListViewModel;

public class RecordsActivity extends AppCompatActivity {
    private static String TAG = "RecordActivity";

    private RecordListViewModel recordListViewModel;
    private CategoryListViewModel categoryListViewModel;
    private RecordListAdapter recordListAdapter;
    private RecyclerView recordRecyclerView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateRecord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecordsActivity.this, CreateUpdateRecordActivity.class));
            }
        });
        recordRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_records);
        recordListAdapter = new RecordListAdapter(new ArrayList<Record>(),new ArrayList<Category>());
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        recordListViewModel = ViewModelProviders.of(this).get(RecordListViewModel.class);
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        categoryListViewModel.getCategoryList().observe(RecordsActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categoryList) {
                recordListAdapter.updateCategoryList(categoryList);
            }
        });

        recordListViewModel.getRecordList().observe(RecordsActivity.this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> recordList) {
                recordListAdapter.updateRecordList(recordList);
            }
        });

        recordRecyclerView.setAdapter(recordListAdapter);


        recordRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recordRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(RecordsActivity.this, AboutRecordActivity.class);
                intent.putExtra("record_id", recordListAdapter.getItem(position).getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





    }
}
