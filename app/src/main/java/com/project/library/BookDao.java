package com.project.library;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    long insert(Book book);

    @Insert
    void insert(List<Book> books);

    @Query("select * from books where username= :username")
    List<Book> getLoggedInUserBooks(String username);

    @Update
    void updateBook(Book book);

    @Delete
    void delete(Book book);

}
