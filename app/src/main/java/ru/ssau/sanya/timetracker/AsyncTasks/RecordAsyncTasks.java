package ru.ssau.sanya.timetracker.AsyncTasks;


import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.ViewModel.RecordListViewModel;

public class RecordAsyncTasks {
    private AppDatabase db;

    public RecordAsyncTasks(AppDatabase db) {
        this.db = db;
    }

    public void add(final Record record) {
        new AddRecord(db).execute(record);
    }

    public Record get(Integer id) throws ExecutionException, InterruptedException {
        return new GetRecord(db).execute(id).get();
    }
    public List<Record> getAll() throws ExecutionException, InterruptedException {
        return new GetAllRecord(db).execute().get();
    }


    public void update(Record record) {
        new UpdateRecord(db).execute(record);
    }

    public void delete(Record record) {
        new DeleteRecord(db).execute(record);
    }

    private static class AddRecord extends AsyncTask<Record, Void, Void> {

        private AppDatabase db;

        AddRecord(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Record... records) {
            db.getRecordDao().insert(records[0]);
            return null;
        }
    }

    private static class GetRecord extends AsyncTask<Integer, Void, Record> {

        private AppDatabase db;

        GetRecord(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Record doInBackground(Integer... integers) {
            return db.getRecordDao().getRecordById(integers[0]);
        }


    }
    private static class GetAllRecord extends AsyncTask<Void, Void, List<Record>> {

        private AppDatabase db;

        GetAllRecord(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected List<Record> doInBackground(Void... voids) {
            return db.getRecordDao().getAllRecord();
        }

    }

    private static class DeleteRecord extends AsyncTask<Record, Void, Void> {

        private AppDatabase db;

        DeleteRecord(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Record... records) {
            db.getRecordDao().delete(records[0]);
            return null;
        }
    }

    private static class UpdateRecord extends AsyncTask<Record, Void, Void> {

        private AppDatabase db;

        UpdateRecord(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(Record... records) {
            db.getRecordDao().update(records[0]);
            return null;
        }
    }


}
