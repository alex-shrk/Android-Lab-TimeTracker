package ru.ssau.sanya.timetracker.ViewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;

public class RecordListViewModel extends AndroidViewModel {
    private final LiveData<List<Record>> recordList;

    private AppDatabase appDatabase;
    public RecordListViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        recordList = appDatabase.getRecordDao().getAllRecord();
    }

    public LiveData<List<Record>> getRecordList(){
        return recordList;
    }


}
