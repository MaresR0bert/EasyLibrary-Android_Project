package com.project.library;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.R;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewestBooksActivity extends AppCompatActivity {
    private GridView gridView;
    List<Book> booksOnGridView = new ArrayList<Book>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(R.string.newest_books);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newest_books);
        gridView = findViewById(R.id.newestBooksGridView);

        ExtractBooks extractBooks = new ExtractBooks(){

            @Override
            protected void onPostExecute(InputStream inputStream) {
                booksOnGridView.addAll(ExtractBooks.booksList);

                BookAdapterToGridView adapter = new BookAdapterToGridView(getApplicationContext(), R.layout.grid_view_book_custom_adapter,
                        booksOnGridView, getLayoutInflater());
                gridView.setAdapter(adapter);
            }
        };
        try {
            extractBooks.execute(new URL("https://pastebin.com/raw/791UqqbU"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}