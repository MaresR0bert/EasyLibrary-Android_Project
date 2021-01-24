package com.project.library;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.library.data.model.LoggedInUser;
import com.project.library.ui.login.LoginActivity;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//useful link https://developer.android.com/reference/android/widget/RatingBar.html#setIsIndicator(boolean)

public class MainPage extends AppCompatActivity {
    private static final int REQUEST_CODE = 200;
    public static final int REQUEST_CODE_EDIT = 300;
    public static final String EDIT_BOOK = "editBook";
    private Intent intent;
    private FloatingActionButton floatingActionButton;
    private ListView listView;
    List<Book> books = new ArrayList<Book>();
    public int poz;
    private LoggedInUser loggedInUser;

    public static final int TO_MAP_OPTION = 0;
    public static final int TO_NEWEST_OPTION = 1;
    public static final int TO_STATISTICS = 2;
    public static final int TO_CHART = 3;
    public static final int TO_FAVOURITE_AUTHORS = 4;

    private void howMuchToRead(){
        TextView textViewNrPagTot = findViewById(R.id.textViewNrPagTot);
        int i = 0;
        for(Book b : books){
            if(!b.isRead()){
                i+=b.getNoPages();
            }
        }
        textViewNrPagTot.setText(getString(R.string.left_to_read) + i + getString(R.string.pages));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent createdIntent = getIntent();
        loggedInUser = (LoggedInUser) createdIntent.getSerializableExtra(LoginActivity.LOGGED_IN_USER_TAG);
        final BookDB bookDB = BookDB.getInstance(getApplicationContext());
        getSupportActionBar().setTitle(R.string.my_library);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        if(loggedInUser != null)
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), AddBookActivity.class);
                intent.putExtra(LoginActivity.LOGGED_IN_USER_TAG, loggedInUser);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                poz = position;
                intent = new Intent(getApplicationContext(), AddBookActivity.class);
                intent.putExtra(EDIT_BOOK, books.get(position));
                intent.putExtra(LoginActivity.LOGGED_IN_USER_TAG, loggedInUser);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                final Book book = books.get(position);
                final BookAdapterToListView adapter = (BookAdapterToListView) listView.getAdapter();
                AlertDialog dialog = new AlertDialog.Builder(MainPage.this)
                        .setTitle(getString(R.string.delete_book_dialog_title))
                        .setMessage(getString(R.string.delete_book_dialog_message))
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), getString(R.string.delete_book_dialog_message_negative),
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bookDB.getBookDao().delete(book);
                                books.remove(book);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), getString(R.string.delete_book_dialog_message_positive) + book.toString(),
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                                howMuchToRead();
                            }
                        }).create();

                dialog.show();
                return true;
            }
        });

        ExtractBooks extractBooks = new ExtractBooks(){

            @Override
            protected void onPostExecute(InputStream inputStream) {
                List<Book> booksFromDb = bookDB.getBookDao().getLoggedInUserBooks(loggedInUser.getUsername());
                if(booksFromDb == null){
                bookDB.getBookDao().insert(books);
                books.addAll(ExtractBooks.booksList);
                for(Book book : books){
                    book.setUsername(loggedInUser.getUsername());
                }}

                books.addAll(booksFromDb);

                BookAdapterToListView adapter = new BookAdapterToListView(getApplicationContext(), R.layout.list_view_book_custom_adapter,
                        books, getLayoutInflater()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view =  super.getView(position, convertView, parent);
                        Book book = books.get(position);
                        TextView textViewRead = view.findViewById(R.id.read);
                        textViewRead.setTextColor(book.isRead()?Color.GREEN:Color.RED);
                        return view;
                    }
                };
                listView.setAdapter(adapter);
                howMuchToRead();
            }
        };
        try {
            extractBooks.execute(new URL("https://pastebin.com/raw/m6nhz1xB"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,TO_MAP_OPTION,0,getString(R.string.nearest_libraries));
        menu.add(0,TO_NEWEST_OPTION,1,getString(R.string.see_the_newest_books));
        menu.add(0,TO_STATISTICS,2,getString(R.string.statitics));
        menu.add(0,TO_CHART,3,getString(R.string.chart_statistics));
        menu.add(0,TO_FAVOURITE_AUTHORS,4,getString(R.string.favourite_authors));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case TO_MAP_OPTION:
                Intent intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
                break;
            case TO_NEWEST_OPTION:
                Intent intent1 = new Intent(this,NewestBooksActivity.class);
                startActivity(intent1);
                break;
            case TO_STATISTICS:
                ArrayList<Book> listBookNoRead = new ArrayList<>();
                ArrayList<Book> listBookRead = new ArrayList<>();
                for(Book b: books){
                    if(b.isRead())
                        listBookRead.add(b);
                    else
                        listBookNoRead.add(b);
                }
                Intent intent2 = new Intent(this,Statistics.class);
                intent2.putExtra("listBookNoRead", listBookNoRead);
                intent2.putExtra("listBookRead", listBookRead);
                startActivity(intent2);
                break;
            case TO_CHART:
                ArrayList<Book> booklist = new ArrayList<>();
                for(Book b:books) {
                    booklist.add(b);
                    System.out.println("done");
                }
                Intent intent3 = new Intent(this,ChartStatistics.class);
                intent3.putExtra("booklist",booklist);
                startActivity(intent3);
                break;
            case TO_FAVOURITE_AUTHORS:
                Intent intent4 = new Intent(this, FavouriteAuthors.class);
                intent4.putExtra(LoginActivity.LOGGED_IN_USER_TAG, loggedInUser);
                startActivity(intent4);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            Book book = (Book) data.getSerializableExtra(AddBookActivity.ADD_BOOK);
            if(book != null){
                books.add(book);

                BookAdapterToListView adapter = new BookAdapterToListView(getApplicationContext(), R.layout.list_view_book_custom_adapter,books, getLayoutInflater()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view =  super.getView(position, convertView, parent);
                        Book book = books.get(position);
                        TextView textViewRead = view.findViewById(R.id.read);
                        textViewRead.setTextColor(book.isRead()?Color.GREEN:Color.RED);
                        return view;
                    }
                };
                listView.setAdapter(adapter);
                howMuchToRead();
            }

        }
        else
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            Book book = (Book) data.getSerializableExtra(AddBookActivity.ADD_BOOK);
            {
                if (book != null) {
                    books.get(poz).setAuthor(book.getAuthor());
                    books.get(poz).setTitle(book.getTitle());
                    books.get(poz).setBookGenre(book.getBookGenre());
                    books.get(poz).setNoPages(book.getNoPages());
                    books.get(poz).setPublishDate(book.getPublishDate());
                    books.get(poz).setRating(book.getRating());
                    books.get(poz).setRead(book.isRead());
                    books.get(poz).setCoverImageUri(book.getCoverImageUri());


                    BookAdapterToListView adapter = new BookAdapterToListView(getApplicationContext(), R.layout.list_view_book_custom_adapter,
                            books, getLayoutInflater()) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view =  super.getView(position, convertView, parent);
                            Book book = books.get(position);
                            TextView textViewRead = view.findViewById(R.id.read);
                            textViewRead.setTextColor(book.isRead()?Color.GREEN:Color.RED);
                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                    howMuchToRead();
                }
            }
        }
    }
}