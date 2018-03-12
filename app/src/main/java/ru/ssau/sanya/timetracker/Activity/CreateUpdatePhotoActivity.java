package ru.ssau.sanya.timetracker.Activity;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Adapter.CategoryListAdapter;
import ru.ssau.sanya.timetracker.Adapter.IconsListAdapter;
import ru.ssau.sanya.timetracker.Adapter.PhotosListAdapter;
import ru.ssau.sanya.timetracker.AsyncTasks.PhotoAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Photo;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.RecyclerTouchListener;
import ru.ssau.sanya.timetracker.ViewModel.CategoryListViewModel;

public class CreateUpdatePhotoActivity extends AppCompatActivity {

    private EditText description;
    private ImageView imageViewPhoto;
    private Button btnAccept;
    private String path;

    List<Photo> assetsPhotos = new ArrayList<>();
    private Integer idRecord;

    private void initAssetsPhoto(){
        assetsPhotos.add(new Photo("file:///android_asset/images/cat.jpg","",-1));
        assetsPhotos.add(new Photo("file:///android_asset/images/dog.jpg","",-1));
        assetsPhotos.add(new Photo("file:///android_asset/images/walle.jpg","",-1));
    }
    /*String[] photoPaths = {

            "file:///android_asset/images/cat.jpg",
            "file:///android_asset/images/dog.jpg",
            "file:///android_asset/images/walle.jpg"
    };*/

    private boolean updateFlag = false;
    private Photo selectedPhoto;
    private Integer idPhoto;
    private PhotoAsyncTasks photoAsyncTasks;
    private AppDatabase appDatabase;

    private RecyclerView recyclerViewPhotos;
    private PhotosListAdapter photosListAdapter;

    private void setupUI() {
        description = findViewById(R.id.photoDescrEdit);
        imageViewPhoto = findViewById(R.id.photoPreviewImageView);
        btnAccept = findViewById(R.id.btnCreateNewPhoto);
        recyclerViewPhotos = findViewById(R.id.list_photos);

        photosListAdapter = new PhotosListAdapter(assetsPhotos);
        recyclerViewPhotos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPhotos.setAdapter(photosListAdapter);
    }

    private void setupDB() {
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        photoAsyncTasks = new PhotoAsyncTasks(appDatabase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_photo);

        setupUI();
        setupDB();
        initAssetsPhoto();

    }

    private void fillUIFieldsFromEntity(Photo photo) {
        description.setText(photo.getDescription());
        selectedPhoto = photo;
        Picasso.with(this).load(photo.getPath()).into(imageViewPhoto);
    }

    private Photo fillEntityFieldsFromUI(Photo photo) {
        photo.setDescription(description.getText().toString());
        photo.setPath(selectedPhoto.getPath());
        photo.setIdRecord(idRecord);
        return photo;
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.get("photo_id") != null) {
                idPhoto = (Integer) b.get("photo_id");
            }
            if (b.get("record_id") != null) {
                idRecord = (Integer) b.get("record_id");
            }
            if (b.get("UPDATE_FLAG") != null) {
                updateFlag = (Boolean) b.get("UPDATE_FLAG");
            }
        }
        Photo photo = null;
        if (updateFlag) {
            try {
                photo = photoAsyncTasks.get(idPhoto);
                fillUIFieldsFromEntity(photo);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        recyclerViewPhotos.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewPhotos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(CreateUpdatePhotoActivity.this, getString(R.string.long_touch), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                selectedPhoto = photosListAdapter.getItem(position);
                Picasso.with(CreateUpdatePhotoActivity.this).load(selectedPhoto.getPath()).into(imageViewPhoto);
                Toast.makeText(CreateUpdatePhotoActivity.this, getString(R.string.photo_selected), Toast.LENGTH_LONG).show();
            }
        }));




        final Photo finalPhoto = photo;
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPhoto == null || description.getText() == null)
                    Toast.makeText(CreateUpdatePhotoActivity.this, R.string.missing_fields, Toast.LENGTH_SHORT).show();
                else {


                    if (updateFlag) {
                        photoAsyncTasks.update(fillEntityFieldsFromUI(finalPhoto));

                    } else {
                        Photo r = new Photo();
                        photoAsyncTasks.add(fillEntityFieldsFromUI(r));
                    }

                    Intent intent = new Intent(CreateUpdatePhotoActivity.this, RecordsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });
    }


}
