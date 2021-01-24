package com.project.library;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.R;
import com.project.library.data.model.LoggedInUser;
import com.project.library.ui.login.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddBookActivity extends AppCompatActivity {
    public static final int GALLERY_REQUEST_CODE = 105;
    final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String ADD_BOOK = "addBook";
    public Uri bookCoverUri = Uri.EMPTY;
    ImageView bookCover;
    private LoggedInUser loggedInUser;
    private int editBookId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setTitle(R.string.addbook);

        final EditText editTextAuthor = findViewById(R.id.editTextAuthor);
        final EditText editTextTitle = findViewById(R.id.editTextTitle);
        final EditText editTextDate = findViewById(R.id.editTextPublishDate);
        final Spinner spinnerGenre = findViewById(R.id.spinnerGenre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.add_book_genre, R.layout.support_simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);
        final EditText editTextNoPages = findViewById(R.id.editTextNoPages);
        final RatingBar rating = findViewById(R.id.ratingBar2);
        final Switch readSwitch = findViewById(R.id.switchRead);
        final Button buttonSave = findViewById(R.id.buttonSave);
        final Button buttonCancel = findViewById(R.id.buttonCancel);
        final Button buttonAddImage = findViewById(R.id.buttonAddImage);
        bookCover = findViewById(R.id.bookCover);
        final Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra(LoginActivity.LOGGED_IN_USER_TAG);
        rating.setIsIndicator(true);

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        readSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!readSwitch.isChecked()) {
                    rating.setIsIndicator(true);
                    rating.setRating(0);
                } else{
                    rating.setIsIndicator(false);
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextAuthor.getText().toString().isEmpty())
                    editTextAuthor.setError(getString(R.string.et_author_error));
                else if (editTextTitle.getText().toString().isEmpty())
                    editTextTitle.setError(getString(R.string.et_title_error));
                else if (editTextDate.getText().toString().isEmpty())
                    editTextDate.setError(getString(R.string.et_date_error));
                else if (editTextNoPages.getText().toString().isEmpty())
                    editTextNoPages.setError(getString(R.string.et_no_pages));
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
                    try {
                        String author = editTextAuthor.getText().toString();
                        String title = editTextTitle.getText().toString();
                        sdf.parse(editTextDate.getText().toString());
                        Date date = new Date(editTextDate.getText().toString());
                        String noPages = editTextNoPages.getText().toString();
                        Genre bookGenre = Genre.valueOf(spinnerGenre.getSelectedItem().toString().toUpperCase());
                        Float bookRating = rating.getRating();
                        boolean isRead = readSwitch.isChecked();
                        Book book = new Book(title, author, date, bookRating, bookGenre, Integer.parseInt(noPages), bookCoverUri.toString(), isRead);

                        BookDB bookDB = BookDB.getInstance(getApplicationContext());
                        book.setUsername(loggedInUser.getUsername());
                        if(editBookId != 0){
                            book.setId(editBookId);
                            bookDB.getBookDao().updateBook(book);
                        }
                        else {
                            long bookId = bookDB.getBookDao().insert(book);
                            book.setId(Integer.parseInt(String.valueOf(bookId)));
                        }

                        intent.putExtra(ADD_BOOK, book);
                        setResult(RESULT_OK, intent);
                        finish();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(intent.hasExtra(MainPage.EDIT_BOOK))
        {
            Book book = (Book) intent.getSerializableExtra(MainPage.EDIT_BOOK);
            editBookId = book.getId();
            editTextTitle.setText(book.getTitle());
            editTextDate.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(book.getPublishDate()));
            editTextAuthor.setText(book.getAuthor());
            editTextNoPages.setText(String.valueOf(book.getNoPages()));
            ArrayAdapter<String> adaptor = (ArrayAdapter<String>)spinnerGenre.getAdapter();
            for(int i=0;i<adaptor.getCount();i++)
                if(adaptor.getItem(i).equals(book.getBookGenre()))
                {
                    spinnerGenre.setSelection(i);
                    break;
                }
            rating.setRating(book.getRating());
            readSwitch.setChecked(book.isRead());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri contentUri = data.getData();
            this.bookCoverUri = contentUri;
            this.bookCover.setImageURI(contentUri);
        }
    }
}