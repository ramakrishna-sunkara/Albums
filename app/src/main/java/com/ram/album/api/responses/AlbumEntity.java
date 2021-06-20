package com.ram.album.data.api.responses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class AlbumEntity {

    @ColumnInfo
    @SerializedName("userId")
    @Expose
    public int userId;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    public int id;

    @ColumnInfo
    @SerializedName("title")
    @Expose
    public String title;

    public AlbumEntity(int userId, int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
