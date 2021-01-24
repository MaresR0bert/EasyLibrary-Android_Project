package com.project.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library.R;

import java.util.List;

public class BookAdapterToGridView extends ArrayAdapter<Book> {
    private Context context;
    private int resource;
    private List<Book> booksList;
    private LayoutInflater layoutInflater;

    public BookAdapterToGridView(@NonNull Context context, int resource, List<Book> booksList, LayoutInflater layoutInflater) {
        super(context, resource, booksList);
        this.context = context;
        this.resource = resource;
        this.booksList = booksList;
        this.layoutInflater = layoutInflater;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Book book = booksList.get(position);
        if (book != null) {

            TextView tvTitle = view.findViewById(R.id.gvTitle);
            tvTitle.setText(context.getString(R.string.label_title) + book.getTitle());
            TextView tvAuthor = view.findViewById(R.id.gvAuthor);
            tvAuthor.setText(context.getString(R.string.label_author) + book.getAuthor());
            TextView tvGenre = view.findViewById(R.id.gvGenre);
            tvGenre.setText(context.getString(R.string.label_genre) + book.getBookGenre().toString());
            TextView tvNoPages = view.findViewById(R.id.gvPages);
            tvNoPages.setText(context.getString(R.string.label_no_pages) + book.getNoPages());
            RatingBar ratingBar = view.findViewById(R.id.gvRatingBar);
            ratingBar.setIsIndicator(true);
            ratingBar.setRating(book.getRating());
        }
        return view;
    }
}
