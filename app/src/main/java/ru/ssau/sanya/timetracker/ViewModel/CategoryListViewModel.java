package ru.ssau.sanya.timetracker.ViewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;

public class CategoryListViewModel extends AndroidViewModel {
    private final LiveData<List<Category>> categoryList;

    private AppDatabase appDatabase;
    public CategoryListViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        categoryList = appDatabase.getCategoryDao().getAllCategory();
    }

    public LiveData<List<Category>> getCategoryList(){
        return categoryList;
    }


}
