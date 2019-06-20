package com.example.navigationdrawertaskassignment.Tasks;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

//task table to use for livedata
@Entity(tableName = "taskTable")
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name="points")
    private String points;

    @ColumnInfo(name="responsible")
    private String responsible;

    public Task(String title, String description, String points, String responsible) {
        this.title = title;
        this.description = description;
        this.points = points;
        this.responsible = responsible;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoints() {return points;}
    public void setPoints(String points){this.points = points;}

    public String getResponsible() {return responsible;}
    public void setResponsible(String responsible){this.responsible = responsible;}



    protected Task(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.points = in.readString();
        this.responsible = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.points);
        dest.writeString(this.responsible);

    }
}
