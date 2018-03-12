package ru.ssau.sanya.timetracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Adapter.CategoryListAdapter;
import ru.ssau.sanya.timetracker.AsyncTasks.CategoryAsyncTasks;
import ru.ssau.sanya.timetracker.AsyncTasks.PhotoAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Photo;
import ru.ssau.sanya.timetracker.R;

public class AboutPhotoActivity extends AppCompatActivity {
    private TextView descr;

    private ImageView photoImageView;
    private Integer idPhoto;



    private PhotoAsyncTasks photoAsyncTasks;

    private AppDatabase appDatabase;

    private Photo photo = null;


    private void setupUI() {
        Toolbar toolbar = findViewById(R.id.toolbar_about_photo);
        setSupportActionBar(toolbar);
        descr = findViewById(R.id.about_photoDescr);
        photoImageView = findViewById(R.id.about_photoImageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_photo);

        setupUI();


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.get("photo_id") != null) {
                idPhoto = (Integer) b.get("photo_id");
            }
        }

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        photoAsyncTasks = new PhotoAsyncTasks(appDatabase);


        try {
            photo = photoAsyncTasks.get(idPhoto);

            descr.setText(photo.getDescription());
            Picasso.with(this).load(photo.getPath()).into(photoImageView);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_photo) {

            photoAsyncTasks.delete(photo);
            finish();
        }
        if (id == R.id.action_update_photo) {
            Intent intent = new Intent(this, CreateUpdatePhotoActivity.class);
            intent.putExtra("photo_id", photo.getId());
            intent.putExtra("UPDATE_FLAG", true);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
