package ru.ssau.sanya.timetracker.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Adapter.IconsListAdapter;
import ru.ssau.sanya.timetracker.AsyncTasks.CategoryAsyncTasks;
import ru.ssau.sanya.timetracker.AsyncTasks.RecordAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.RecyclerTouchListener;
import ru.ssau.sanya.timetracker.ViewModel.AddCategoryViewModel;

public class CreateUpdateCategoryActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private Button btnAccept;
    private boolean updateFlag = false;
    private AddCategoryViewModel addCategoryViewModel;
    private RecyclerView recyclerViewIcons;
    private IconsListAdapter iconsListAdapter;


    private Integer idCategory;
    private CategoryAsyncTasks categoryAsyncTasks;
    private AppDatabase appDatabase;
    /*String[] iconName = {
            "Airport",
            "Cake",
            "Casino",
            "Filter",
            "Fitness",
            "Kitchen",
            "TV",
            "Florist",
            "Hotel",
            "City",
            "Pool",
            "School",
            "Spa",
            "Leave"
    };*/
    Integer[] iconId = {
            R.drawable.ic_airport_shuttle_black_48dp,
            R.drawable.ic_cake_black_48dp,
            R.drawable.ic_casino_black_48dp,
            R.drawable.ic_filter_vintage_black_48dp,
            R.drawable.ic_kitchen_black_48dp,
            R.drawable.ic_live_tv_black_48dp,
            R.drawable.ic_local_florist_black_48dp,
            R.drawable.ic_local_hotel_black_48dp,
            R.drawable.ic_location_city_black_48dp,
            R.drawable.ic_pool_black_48dp,
            R.drawable.ic_school_black_48dp,
            R.drawable.ic_spa_black_48dp,
            R.drawable.ic_time_to_leave_black_48dp
    };
    private int selectedIcon = iconId[0];

    private void setupUI() {
        name = findViewById(R.id.categoryNameEdit);
        description = findViewById(R.id.categoryDescrEdit);
        btnAccept = findViewById(R.id.btnCreateNewCategory);
        recyclerViewIcons = findViewById(R.id.listIcons);

        iconsListAdapter = new IconsListAdapter(iconId);
        recyclerViewIcons.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIcons.setAdapter(iconsListAdapter);


    }

    private void setupDB() {
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        categoryAsyncTasks = new CategoryAsyncTasks(appDatabase);
    }

    private void fillUIFieldsFromEntity(Category category){
        name.setText(category.getName());
        description.setText(category.getDescription());
        selectedIcon=category.getIcon();
    }
    private Category fillEntityFieldsFromUI(Category category){
        category.setName(name.getText().toString());
        category.setDescription(description.getText().toString());
        category.setIcon(selectedIcon);
        return category;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_category);

        setupUI();
        setupDB();

    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.get("category_id")!=null) {
                idCategory = (Integer) b.get("category_id");
            }
            if (b.get("UPDATE_FLAG") != null) {
                updateFlag = (Boolean) b.get("UPDATE_FLAG");
            }
        }
        Category category = null;
        if (updateFlag) {
            try{
                category = categoryAsyncTasks.get(idCategory);
                fillUIFieldsFromEntity(category);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        recyclerViewIcons.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewIcons, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(CreateUpdateCategoryActivity.this, getString(R.string.select_icon), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                selectedIcon = iconsListAdapter.getItem(position);
                Toast.makeText(CreateUpdateCategoryActivity.this, getString(R.string.icon_selected), Toast.LENGTH_LONG).show();
            }
        }));


        addCategoryViewModel = ViewModelProviders.of(this).get(AddCategoryViewModel.class);

        final Category finalCategory = category;
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText() == null || description.getText() == null)
                    Toast.makeText(CreateUpdateCategoryActivity.this, R.string.missing_fields, Toast.LENGTH_SHORT).show();
                else {
                    if (updateFlag){
                        categoryAsyncTasks.update(fillEntityFieldsFromUI(finalCategory));

                    }
                    else {
                        Category c = new Category();
                        categoryAsyncTasks.add(fillEntityFieldsFromUI(c));
                        finish();
                    }
                    Intent intent = new Intent(CreateUpdateCategoryActivity.this, CategoriesActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                   // finish();
                }
            }
        });

    }


}
