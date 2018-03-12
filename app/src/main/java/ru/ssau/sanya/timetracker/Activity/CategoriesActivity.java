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

import ru.ssau.sanya.timetracker.Adapter.CategoryListAdapter;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.RecyclerTouchListener;
import ru.ssau.sanya.timetracker.ViewModel.CategoryListViewModel;

public class CategoriesActivity extends AppCompatActivity {
    private static String TAG = "CategoriesActivity";

    private CategoryListViewModel viewModel;
    private CategoryListAdapter categoryListAdapter;
    private RecyclerView categoryRecyclerView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoriesActivity.this, CreateUpdateCategoryActivity.class));
            }
        });
        categoryRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_categories);
        categoryListAdapter = new CategoryListAdapter(new ArrayList<Category>());
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryRecyclerView.setAdapter(categoryListAdapter);

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        viewModel.getCategoryList().observe(CategoriesActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categoryList) {
                categoryListAdapter.updateCategoryList(categoryList);
            }
        });

        categoryRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, categoryRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(CategoriesActivity.this, AboutCategoryActivity.class);
                intent.putExtra("category_id", categoryListAdapter.getItem(position).getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}
