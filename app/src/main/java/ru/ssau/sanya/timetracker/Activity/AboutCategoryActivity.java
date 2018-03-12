package ru.ssau.sanya.timetracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Adapter.CategoryListAdapter;
import ru.ssau.sanya.timetracker.Adapter.IconsListAdapter;
import ru.ssau.sanya.timetracker.AsyncTasks.CategoryAsyncTasks;
import ru.ssau.sanya.timetracker.AsyncTasks.CategoryAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.R;

public class AboutCategoryActivity extends AppCompatActivity {
    private TextView descr;
    private TextView startTime;
    private TextView endTime;
    private TextView timestamp;
    private RecyclerView recyclerViewIcons;
    private Integer idCategory;
    private IconsListAdapter iconsListAdapter;
    private CategoryAsyncTasks categoryAsyncTasks;
    private AppDatabase appDatabase;

    private Category category = null;

    private void setupUI() {
        Toolbar toolbar = findViewById(R.id.toolbar_about_category);
        setSupportActionBar(toolbar);
        descr = findViewById(R.id.about_categoryDescr);
        recyclerViewIcons = findViewById(R.id.about_recycleView_iconsCategory);


    }
    private void setupDB() {
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        categoryAsyncTasks = new CategoryAsyncTasks(appDatabase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_category);

        setupUI();
        setupDB();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.get("category_id") != null) {
                idCategory = (Integer) b.get("category_id");
            }
        }





        try {
            category = categoryAsyncTasks.get(idCategory);

            setTitle(category.getName());
            descr.setText(category.getDescription());

            Integer[] icons = {category.getIcon()};
            iconsListAdapter = new IconsListAdapter(icons);


            recyclerViewIcons.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewIcons.setAdapter(iconsListAdapter);


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_category) {

            categoryAsyncTasks.delete(category);
            finish();
        }
        if (id == R.id.action_update_category) {
            Intent intent = new Intent(this, CreateUpdateCategoryActivity.class);
            intent.putExtra("category_id", category.getId());
            intent.putExtra("UPDATE_FLAG", true);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
