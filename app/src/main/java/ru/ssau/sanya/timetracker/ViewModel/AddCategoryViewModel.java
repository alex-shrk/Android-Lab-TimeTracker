package ru.ssau.sanya.timetracker.ViewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;

public class AddCategoryViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    public AddCategoryViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }


}
