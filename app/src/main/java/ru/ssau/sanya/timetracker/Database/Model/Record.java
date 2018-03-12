package ru.ssau.sanya.timetracker.Database.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.icu.util.DateInterval;
import android.os.Build;
import android.support.annotation.RequiresApi;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "idCategory",
        onDelete = CASCADE),
        indices = @Index(value = "idCategory"))
public class Record {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "start_time")
    private String startTime;
    @ColumnInfo(name = "end_time")
    private String endTime;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String timestamp;

    private Integer idCategory;

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public Record(String name, String description, String startTime, String endTime, String timestamp, Integer idCategory) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
        this.idCategory = idCategory;
        this.timestamp = timestamp;
    }

    @Ignore
    public Record() {
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
        public void updateTimestamp()  {
            try {

                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Date start = formatter.parse(startTime);
                Date end = formatter.parse(endTime);
                DateInterval dateInterval = new DateInterval(start.getTime(),end.getTime());
                timestamp = dateInterval.toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
