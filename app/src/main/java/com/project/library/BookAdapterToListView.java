package com.project.library;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookAdapterToListView extends ArrayAdapter<Book> {
    private Context context;
    private int resource;
    private List<Book> booksList;
    private LayoutInflater layoutInflater;

    public BookAdapterToListView(@NonNull Context context, int resource, List<Book> booksList, LayoutInflater layoutInflater) {
        super(context, resource, booksList);
        this.context = context;
        this.resource = resource;
        this.booksList = booksList;
        this.layoutInflater = layoutInflater;
    }

    /*
      WAS NOT ABLE TO SET STRINGS AS A PREDFINED ITEM IN STRINGS.XML
    */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Book book = booksList.get(position);
        if (book != null) {

            TextView tvTitle = view.findViewById(R.id.title);
            tvTitle.setText(context.getString(R.string.label_title) + book.getTitle());

            TextView tvAuthor = view.findViewById(R.id.author);
            tvAuthor.setText(context.getString(R.string.label_author) + book.getAuthor());

            final String DATE_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            String tempDate = sdf.format(book.getPublishDate());
            TextView tvPublishDate = view.findViewById(R.id.publishDate);
            tvPublishDate.setText(context.getString(R.string.label_published_date) + tempDate);

            TextView tvRating = view.findViewById(R.id.rating);
            tvRating.setText(context.getString(R.string.label_rating));

            TextView tvGenre = view.findViewById(R.id.genre);
            tvGenre.setText(context.getString(R.string.label_genre) + book.getBookGenre().toString());

            TextView tvNoPages = view.findViewById(R.id.noPages);
            tvNoPages.setText(context.getString(R.string.label_no_pages) + book.getNoPages());

            TextView tvRead = view.findViewById(R.id.read);
            tvRead.setText(context.getString(R.string.label_read) + (book.isRead() ? context.getString(R.string.read) : context.getString(R.string.not_read)));

            RatingBar ratingBar = view.findViewById(R.id.ratingBarViewed);
            ratingBar.setIsIndicator(true);
            ratingBar.setRating(book.getRating());

            ImageView cover = view.findViewById(R.id.imageView);
            cover.setImageURI(Uri.parse(book.getCoverImageUri()));
        }
        return view;
    }
}
