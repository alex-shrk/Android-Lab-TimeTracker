package ru.ssau.sanya.timetracker.Database.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Record.class,
        parentColumns = "id",
        childColumns = "idRecord",
        onDelete = CASCADE),
        indices = @Index(value = "idRecord"))
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo
    private String path;

    @ColumnInfo
    private String description;

    private Integer idRecord;

    public Photo(String path, String description, Integer idRecord) {
        this.path = path;
        this.description = description;
        this.idRecord = idRecord;
    }

    @Ignore
    public Photo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(Integer idRecord) {
        this.idRecord = idRecord;
    }
}
