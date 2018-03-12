package ru.ssau.sanya.timetracker.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Photo;

@Dao
public interface PhotoDao {
    @Insert
    void insert(Photo... photos);

    @Update
    void update(Photo... photos);
    @Delete
    void delete(Photo... photos);

    @Query("SELECT * FROM Photo")
    List<Photo> getAllPhoto();

    @Query("SELECT * FROM Photo WHERE id = :idPhoto")
    Photo getPhotoById(int idPhoto);


    @Query("SELECT * FROM Photo WHERE idRecord IS :idRecord")
    List<Photo> getPhotosForRecord(int idRecord);
}
