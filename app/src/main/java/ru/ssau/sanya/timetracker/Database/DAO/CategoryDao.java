package ru.ssau.sanya.timetracker.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.ssau.sanya.timetracker.Database.Model.Category;

@Dao
public interface CategoryDao {
    //add category to db
    @Insert
    void insert(Category... categories);

    @Update
    void update(Category... categories);

    //delete category
    @Delete
    void delete(Category... categories);

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllCategory();

    @Query("SELECT * FROM Category WHERE id = :idCategory")
    Category getCategoryById(int idCategory);
}
