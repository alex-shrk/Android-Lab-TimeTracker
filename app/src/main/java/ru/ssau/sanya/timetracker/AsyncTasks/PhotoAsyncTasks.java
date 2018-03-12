package ru.ssau.sanya.timetracker.AsyncTasks;


import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Photo;

public class PhotoAsyncTasks {
    private AppDatabase db;

    public PhotoAsyncTasks(AppDatabase db) {
        this.db = db;
    }

    public void add(final Photo photo) {
        new AddPhoto(db).execute(photo);
    }

    public Photo get(Integer id) throws ExecutionException, InterruptedException {
        return new GetPhoto(db).execute(id).get();
    }

    public List<Photo> getByIdRecord(Integer id) throws ExecutionException, InterruptedException {
        return new GetPhotosForRecord(db).execute(id).get();
    }




    public void update(Photo photo) {
        new UpdatePhoto(db).execute(photo);
    }

    public void delete(Photo photo) {
        new DeletePhoto(db).execute(photo);
    }





    private static class AddPhoto extends AsyncTask<Photo, Void, Void> {

        private AppDatabase db;

        AddPhoto(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            db.getPhotoDao().insert(photos[0]);
            return null;
        }
    }

    private static class GetPhoto extends AsyncTask<Integer, Void, Photo> {

        private AppDatabase db;

        GetPhoto(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Photo doInBackground(Integer... integers) {
            return db.getPhotoDao().getPhotoById(integers[0]);
        }


    }

    private static class GetPhotosForRecord extends AsyncTask<Integer, Void, List<Photo>> {

        private AppDatabase db;

        GetPhotosForRecord(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected List<Photo> doInBackground(Integer... integers) {
            return db.getPhotoDao().getPhotosForRecord(integers[0]);
        }


    }

    private static class DeletePhoto extends AsyncTask<Photo, Void, Void> {

        private AppDatabase db;

        DeletePhoto(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            db.getPhotoDao().delete(photos[0]);
            return null;
        }
    }

    private static class UpdatePhoto extends AsyncTask<Photo, Void, Void> {

        private AppDatabase db;

        UpdatePhoto(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            db.getPhotoDao().update(photos[0]);
            return null;
        }
    }


}
