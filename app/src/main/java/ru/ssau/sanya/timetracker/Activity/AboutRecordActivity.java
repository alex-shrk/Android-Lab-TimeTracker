package ru.ssau.sanya.timetracker.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Adapter.CategoryListAdapter;
import ru.ssau.sanya.timetracker.Adapter.IconsListAdapter;
import ru.ssau.sanya.timetracker.Adapter.PhotosListAdapter;
import ru.ssau.sanya.timetracker.AsyncTasks.CategoryAsyncTasks;
import ru.ssau.sanya.timetracker.AsyncTasks.PhotoAsyncTasks;
import ru.ssau.sanya.timetracker.AsyncTasks.RecordAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Photo;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.DateTimeHelper;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.RecyclerTouchListener;

public class AboutRecordActivity extends AppCompatActivity {
    private TextView descr;
    private TextView startTime;
    private TextView endTime;
    private TextView timestamp;
    private RecyclerView categoryRecyclerView;
    private RecyclerView photosRecyclerView;
    private Integer idRecord;
    private FloatingActionButton fabAddPhoto;

    private CategoryListAdapter categoryListAdapter;
    private PhotosListAdapter photosListAdapter;
    private RecordAsyncTasks recordAsyncTasks;
    private CategoryAsyncTasks categoryAsyncTasks;
    private PhotoAsyncTasks photoAsyncTasks;
    private AppDatabase appDatabase;

    private Record record = null;
    private Category category = null;

    private void setupUI() {
        Toolbar toolbar = findViewById(R.id.toolbar_about_record);
        setSupportActionBar(toolbar);

        descr = findViewById(R.id.about_recordDescr);
        startTime = findViewById(R.id.about_startTimeRecord);
        endTime = findViewById(R.id.about_endTimeRecord);
        timestamp = findViewById(R.id.about_timestampRecord);
        categoryRecyclerView = findViewById(R.id.about_recycleView_category);
        photosRecyclerView = findViewById(R.id.about_recycleView_photos);
        fabAddPhoto = findViewById(R.id.fab_add_photo);


    }

    private void setupDB() {
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        recordAsyncTasks = new RecordAsyncTasks(appDatabase);
        categoryAsyncTasks = new CategoryAsyncTasks(appDatabase);
        photoAsyncTasks = new PhotoAsyncTasks(appDatabase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_record);

        setupUI();
        setupDB();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.get("record_id") != null) {
                idRecord = (Integer) b.get("record_id");
            }
        }


        categoryListAdapter = new CategoryListAdapter(new ArrayList<Category>());
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryRecyclerView.setAdapter(categoryListAdapter);

        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutRecordActivity.this, CreateUpdatePhotoActivity.class);
                intent.putExtra("record_id", record.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        try {
            record = recordAsyncTasks.get(idRecord);
            category = categoryAsyncTasks.get(record.getIdCategory());

            setTitle(record.getName());
            descr.setText(record.getDescription());
            startTime.setText(DateTimeHelper.prepareDatetimeStringForView(record.getStartTime()));
            endTime.setText(DateTimeHelper.prepareDatetimeStringForView(record.getEndTime()));
            timestamp.setText(DateTimeHelper.prepareDatetimeIntervalStringForView(record.getTimestamp()));

            List<Category> categories = new ArrayList<>();
            categories.add(category);
            categoryListAdapter.updateCategoryList(categories);

            List<Photo> photos = photoAsyncTasks.getByIdRecord(idRecord);

            photosListAdapter = new PhotosListAdapter(photos);
            photosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            photosRecyclerView.setAdapter(photosListAdapter);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        photosRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, photosRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AboutRecordActivity.this, AboutPhotoActivity.class);
                intent.putExtra("photo_id", photosListAdapter.getItem(position).getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_record, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_record) {

            recordAsyncTasks.delete(record);
            finish();
        }
        if (id == R.id.action_update_record) {
            Intent intent = new Intent(this, CreateUpdateRecordActivity.class);
            intent.putExtra("record_id", record.getId());
            intent.putExtra("UPDATE_FLAG", true);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
