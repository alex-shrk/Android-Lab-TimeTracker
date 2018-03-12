package ru.ssau.sanya.timetracker.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.AsyncTasks.CategoryAsyncTasks;
import ru.ssau.sanya.timetracker.AsyncTasks.RecordAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.ViewModel.CategoryListViewModel;
import ru.ssau.sanya.timetracker.ViewModel.RecordListViewModel;

public class StatisticActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private RecordAsyncTasks recordAsyncTasks;
    private CategoryAsyncTasks categoryAsyncTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        setupUI();
        setupDB();
    }
    private void setupUI() {




    }

    private void setupDB() {
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        recordAsyncTasks = new RecordAsyncTasks(appDatabase);
        categoryAsyncTasks = new CategoryAsyncTasks(appDatabase);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            List<Record> recordList = recordAsyncTasks.getAll();
            List<Category> categoryList = categoryAsyncTasks.
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
