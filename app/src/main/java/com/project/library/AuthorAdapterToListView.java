package com.project.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library.R;

import java.util.List;

public class AuthorAdapterToListView extends ArrayAdapter<Author> {
    private Context context;
    private int resource;
    private List<Author> authorsList;
    private LayoutInflater layoutInflater;

    public AuthorAdapterToListView(@NonNull Context context, int resource, List<Author> authorsList, LayoutInflater layoutInflater) {
        super(context, resource, authorsList);
        this.context = context;
        this.resource = resource;
        this.authorsList = authorsList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Author author = authorsList.get(position);
        if (author != null) {

            TextView tvName = view.findViewById(R.id.name);
            tvName.setText(context.getString(R.string.label_name) + author.getName());

            TextView tvNumberOfPublishedBooks = view.findViewById(R.id.numberOfPublishedBooks);
            tvNumberOfPublishedBooks.setText(context.getString(R.string.label_no_published_books) + author.getNumberOfPublishedBooks());

            TextView tvBestSellingBook = view.findViewById(R.id.bestSellingBookName);
            tvBestSellingBook.setText(context.getString(R.string.label_best_selling_book) + author.getBestSellingBookName());
        }
        return view;
    }
}
