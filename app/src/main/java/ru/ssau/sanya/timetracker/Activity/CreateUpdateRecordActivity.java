package ru.ssau.sanya.timetracker.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import ru.ssau.sanya.timetracker.Adapter.CategoryListAdapter;
import ru.ssau.sanya.timetracker.AsyncTasks.RecordAsyncTasks;
import ru.ssau.sanya.timetracker.Database.AppDatabase;
import ru.ssau.sanya.timetracker.Database.Model.Category;
import ru.ssau.sanya.timetracker.Database.Model.Record;
import ru.ssau.sanya.timetracker.DateTimeHelper;
import ru.ssau.sanya.timetracker.R;
import ru.ssau.sanya.timetracker.RecyclerTouchListener;
import ru.ssau.sanya.timetracker.ViewModel.CategoryListViewModel;

public class CreateUpdateRecordActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private TextView startDate;
    private TextView startTime;
    private TextView endDate;
    private TextView endTime;
    private Button btnAccept;

    private LocalTime startTimeLocal;
    private LocalTime endTimeLocal;
    private DateTime startDateTime;
    private DateTime endDateTime;

    private CategoryListViewModel viewModel;
    private CategoryListAdapter recyclerViewAdapter;
    private RecyclerView categoriesRecyclerView;

    private boolean updateFlag = false;


    private Integer idRecord;
    private RecordAsyncTasks recordAsyncTasks;
    private AppDatabase appDatabase;
    private int selectedCategory = -1;

    private void setupUI() {
        name = findViewById(R.id.recordNameEdit);
        description = findViewById(R.id.recordDescrEdit);
        startTime = findViewById(R.id.startTimeRecord);
        startDate = findViewById(R.id.startDateRecord);
        endTime = findViewById(R.id.endTimeRecord);
        endDate = findViewById(R.id.endDateRecord);
        btnAccept = findViewById(R.id.btnCreateNewRecord);
        categoriesRecyclerView = findViewById(R.id.list_records);
    }

    private void setupDB() {
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        recordAsyncTasks = new RecordAsyncTasks(appDatabase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update_record);

        setupUI();
        setupDB();
        JodaTimeAndroid.init(this);

    }

    private void fillUIFieldsFromEntity(Record record) {
        name.setText(record.getName());
        description.setText(record.getDescription());
        startTime.setText(record.getStartTime());
        endTime.setText(record.getEndTime());
        selectedCategory = record.getIdCategory();
    }

    private Record fillEntityFieldsFromUI(Record record) {
        record.setName(name.getText().toString());
        record.setDescription(description.getText().toString());
        Log.i("start time local", startTimeLocal.toString());
        Log.i("start date local", startDate.getText().toString());
        Log.i("start datetime", startDate.getText().toString() + " " + startTimeLocal.toString());
        record.setStartTime(startDateTime.toString());
        record.setEndTime(endDateTime.toString());


        record.setTimestamp(DateTimeHelper.getInterval(startDateTime, endDateTime));

        record.setIdCategory(selectedCategory);
        return record;
    }


    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if (b.get("record_id") != null) {
                idRecord = (Integer) b.get("record_id");
            }
            if (b.get("UPDATE_FLAG") != null) {
                updateFlag = (Boolean) b.get("UPDATE_FLAG");
            }
        }
        Record record = null;
        if (updateFlag) {
            try {
                record = recordAsyncTasks.get(idRecord);
                fillUIFieldsFromEntity(record);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                startDate.setText(DateTimeHelper.convertDateToString(calendar));

            }
        };
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateUpdateRecordActivity.this,
                        startDatePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        final DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                endDate.setText(DateTimeHelper.convertDateToString(calendar));


            }
        };
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateUpdateRecordActivity.this,
                        endDatePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();

                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(CreateUpdateRecordActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        startTimeLocal = new LocalTime(timePicker.getHour(), timePicker.getMinute());
                                        startTime.setText(DateTimeHelper.convertTimeToString(startTimeLocal));
                                    }
                                }, hour, minute, true);//true - 24 format
                timePickerDialog.setTitle(R.string.select_time);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(CreateUpdateRecordActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        endTimeLocal = new LocalTime(timePicker.getHour(), timePicker.getMinute());
                                        endTime.setText(DateTimeHelper.convertTimeToString(endTimeLocal));

                                    }
                                }, hour, minute, true);//true - 24 format
                timePickerDialog.setTitle(R.string.select_time);
                timePickerDialog.show();
            }
        });


        recyclerViewAdapter = new CategoryListAdapter(new ArrayList<Category>());
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setAdapter(recyclerViewAdapter);


        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        viewModel.getCategoryList().observe(CreateUpdateRecordActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categoryList) {
                recyclerViewAdapter.updateCategoryList(categoryList);
            }
        });

        categoriesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, categoriesRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(CreateUpdateRecordActivity.this, getString(R.string.select_category), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Category category = recyclerViewAdapter.getItem(position);
                selectedCategory = category.getId();
                Toast.makeText(CreateUpdateRecordActivity.this, getString(R.string.category_selected) + " " + category.getName(), Toast.LENGTH_LONG).show();
            }

        }));


        final Record finalRecord = record;
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isCorrectFields()) {
                   // Toast.makeText(CreateUpdateRecordActivity.this, R.string.missing_fields, Toast.LENGTH_SHORT).show();
                } else {
                    startDateTime = DateTimeHelper.convertStringToDateTime(startDate.getText().toString(), startTime.getText().toString());
                    endDateTime = DateTimeHelper.convertStringToDateTime(endDate.getText().toString(), endTime.getText().toString());
                    if (!isCorrectTime()) {
                        if (!CreateUpdateRecordActivity.this.isFinishing()) {
                            //Toast.makeText(CreateUpdateRecordActivity.this, R.string.incorrect_date_or_time, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (updateFlag) {
                            recordAsyncTasks.update(fillEntityFieldsFromUI(finalRecord));

                        } else {
                            Record r = new Record();
                            recordAsyncTasks.add(fillEntityFieldsFromUI(r));
                        }

                        Intent intent = new Intent(CreateUpdateRecordActivity.this, RecordsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }


                }
            }
        });
    }

    private boolean isCorrectFields() {
        boolean isCorrect = true;
        if (name.getText() == null || description.getText() == null || selectedCategory == -1) {

            isCorrect = false;
        }
        if (startDate.getText() == null || startTime.getText() == null || endDate.getText() == null || endTime.getText() == null) {
            isCorrect = false;
        }
        return isCorrect;
    }

    private boolean isCorrectTime() {
        boolean isCorrect = true;
        if (!startDateTime.isBefore(endDateTime)) {

            isCorrect = false;
        }
        return isCorrect;
    }


}



