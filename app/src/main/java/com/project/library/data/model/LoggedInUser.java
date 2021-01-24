package com.project.library.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "users")
public class LoggedInUser implements Serializable {

    private String userId;
    private String displayName;
    @NonNull
    @PrimaryKey()
    private String username;

    @Ignore
    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser(String userId, String displayName, String username) {
        this.userId = userId;
        this.displayName = displayName;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}