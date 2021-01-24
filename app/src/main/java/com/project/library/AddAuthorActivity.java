package com.project.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.library.data.model.LoggedInUser;
import com.project.library.ui.login.LoginActivity;


public class AddAuthorActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private LoggedInUser loggedInUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        getSupportActionBar().setTitle(R.string.addAuthor);

        final EditText editTextName = findViewById(R.id.editTextName);
        final EditText editTextNumberOfPublishedBooks = findViewById(R.id.editTextNumberOfPublishedBooks);
        final EditText editTextBestSellingBook = findViewById(R.id.editTextBestSellingBookName);
        final Button buttonSave = findViewById(R.id.buttonSave);
        final Button buttonCancel = findViewById(R.id.buttonCancel);

        final Intent intent = getIntent();
        database = FirebaseDatabase.getInstance();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra(LoginActivity.LOGGED_IN_USER_TAG);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().isEmpty())
                    editTextName.setError(getString(R.string.et_name_error));
                else if (editTextNumberOfPublishedBooks.getText().toString().isEmpty())
                    editTextNumberOfPublishedBooks.setError(getString(R.string.et_nr_published_books_error));
                else if (editTextBestSellingBook.getText().toString().isEmpty())
                    editTextBestSellingBook.setError(getString(R.string.et_best_selling_book_error));
                else {
                    String name = editTextName.getText().toString();
                    int numberOfPublishedBooks = Integer.parseInt(editTextNumberOfPublishedBooks.getText().toString());
                    String bestSellingBookName = editTextBestSellingBook.getText().toString();
                    Author author = new Author(name, numberOfPublishedBooks, bestSellingBookName, loggedInUser.getUsername());
                    writeAuthorInFirebase(author);
                    finish();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void writeAuthorInFirebase(final Author author)
    {
        final DatabaseReference myRef = database.getReference(FavouriteAuthors.EASY_LIBRARY_APP_DEFAULT_RTDB);
        myRef.keepSynced(true);

        myRef.child(FavouriteAuthors.EASY_LIBRARY_APP_DEFAULT_RTDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                author.setUid(myRef.child(FavouriteAuthors.EASY_LIBRARY_APP_DEFAULT_RTDB).push().getKey());
                myRef.child(FavouriteAuthors.EASY_LIBRARY_APP_DEFAULT_RTDB).child(author.getUid()).setValue(author);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}