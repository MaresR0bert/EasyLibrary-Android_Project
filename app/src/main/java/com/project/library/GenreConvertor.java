package com.project.library;

import androidx.room.TypeConverter;

import java.util.Date;

class GenreConvertor {
    @TypeConverter
    public static String fromGenreToString(Genre genre) {
        return genre != null
                ? genre.toString()
                : null;
    }

    @TypeConverter
    public static Genre fromStringToGenre(String genre) {
        return genre != null
                ? Genre.valueOf(genre)
                : null;
    }
}
