package com.project.library;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.library.data.model.LoggedInUser;

import java.util.List;

@Dao
public interface LoggedInUserDao {
    @Insert
    void insert(LoggedInUser user);
    @Query("select * from users where username= :username")
   LoggedInUser getLoggedInUser(String username);
}
