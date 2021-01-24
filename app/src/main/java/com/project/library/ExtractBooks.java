package com.project.library;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtractBooks extends AsyncTask<URL, Void, InputStream> {

    InputStream ist = null;

    public static List<Book> booksList = new ArrayList<>();

    @Override
    protected InputStream doInBackground(URL... urls) {

        HttpURLConnection conn = null;
        String sbuf = "";

        try{
            conn = (HttpURLConnection)urls[0].openConnection();
            conn.setRequestMethod("GET");
            ist = conn.getInputStream();

            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine())!=null)
            {
                sbuf +=line;
            }
            booksList = extractBooksFromJson(sbuf);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ist;
    }

    private List<Book> extractBooksFromJson(String booksJson){
        List<Book> books = new ArrayList<Book>();
        if(booksJson != null) {
            try {
                JSONArray booksJsonArray =
                        new JSONArray(booksJson);
                for (int i = 0; i < booksJsonArray.length(); i++) {
                    JSONObject bookJsonObject = booksJsonArray.getJSONObject(i);
                    String title = bookJsonObject.getString("title");
                    String author = bookJsonObject.getString("author");
                    Date publishDate = new Date(bookJsonObject.getString("publishDate"));
                    Float rating = Float.parseFloat(bookJsonObject.getString("rating"));
                    Genre bookGenre = Genre.valueOf(bookJsonObject.getString("bookGenre"));
                    int noPages = bookJsonObject.getInt("noPages");
                    boolean isRead = bookJsonObject.getBoolean("isRead");
                    String coverImageUri =  bookJsonObject.getString("coverImageUri");
                    Book book = new Book(title, author, publishDate, rating, bookGenre, noPages, coverImageUri, isRead);
                    books.add(book);
                }
                return books;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
