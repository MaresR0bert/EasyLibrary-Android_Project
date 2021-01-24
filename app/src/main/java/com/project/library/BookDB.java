package com.project.library;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.project.library.data.model.LoggedInUser;

@Database(entities = {Book.class, LoggedInUser.class}, version = 1, exportSchema = false)
@TypeConverters({DateConvertor.class, GenreConvertor.class})
public abstract class BookDB extends RoomDatabase {
    private final static String DB_NAME = "books.db";
    private static BookDB instance;

    public static BookDB getInstance(Context context){

        if(instance == null)
            instance = Room.databaseBuilder(context, BookDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract BookDao getBookDao();

    public abstract LoggedInUserDao getLoggedInUserDao();
}
