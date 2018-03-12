package ru.ssau.sanya.timetracker.AsyncTasks;


import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;

public class CategoryAsyncTasks {
    private AppDatabase db;

    public CategoryAsyncTasks(AppDatabase db) {
        this.db = db;
    }

    public void add(final Category category) {
        new AddCategory(db).execute(category);
    }

    public Category get(Integer id) throws ExecutionException, InterruptedException {
        return new GetCategory(db).execute(id).get();
    }


    public void update(Category category) {
        new UpdateCategory(db).execute(category);
    }

    public void delete(Category category) {
        new DeleteCategory(db).execute(category);
    }

    private static class AddCategory extends AsyncTask<Category, Void, Void> {

        private AppDatabase db;

        AddCategory(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            db.getCategoryDao().insert(categories[0]);
            return null;
        }
    }

    private static class GetCategory extends AsyncTask<Integer, Void, Category> {

        private AppDatabase db;

        GetCategory(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Category doInBackground(Integer... integers) {
            return db.getCategoryDao().getCategoryById(integers[0]);
        }


    }

    private static class DeleteCategory extends AsyncTask<Category, Void, Void> {

        private AppDatabase db;

        DeleteCategory(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            db.getCategoryDao().delete(categories[0]);
            return null;
        }
    }

    private static class UpdateCategory extends AsyncTask<Category, Void, Void> {

        private AppDatabase db;

        UpdateCategory(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            db.getCategoryDao().update(categories[0]);
            return null;
        }
    }


}
