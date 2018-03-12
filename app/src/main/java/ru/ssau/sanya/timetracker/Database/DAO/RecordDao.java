package ru.ssau.sanya.timetracker.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;

@Dao
public interface RecordDao {
    @Insert
    void insert(Record... records);

    @Update
    void update(Record... records);

    @Delete
    void delete(Record... records);

    @Query("SELECT * FROM Record")
    List<Record> getAllRecord();

    @Query("SELECT * FROM Record WHERE id = :idRecord")
    Record getRecordById(int idRecord);

    @Query("SELECT * FROM Record WHERE idCategory IS :idCategory")
    LiveData<List<Record>> getRecordsForCategory(int idCategory);


}
