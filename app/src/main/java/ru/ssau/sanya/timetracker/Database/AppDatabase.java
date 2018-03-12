package ru.ssau.sanya.timetracker.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ru.ssau.sanya.timetracker.Database.DAO.CategoryDao;
import ru.ssau.sanya.timetracker.Database.DAO.PhotoDao;
import ru.ssau.sanya.timetracker.Database.DAO.RecordDao;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Photo;
import ru.ssau.sanya.timetracker.Database.Model.Record;

@Database(entities = {Category.class, Photo.class, Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "timetracker_db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract CategoryDao getCategoryDao();

    public abstract PhotoDao getPhotoDao();

    public abstract RecordDao getRecordDao();
}
