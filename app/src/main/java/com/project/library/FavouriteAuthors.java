package com.project.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.library.data.model.LoggedInUser;
import com.project.library.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAuthors extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;
    public static final String EASY_LIBRARY_APP_DEFAULT_RTDB = "";
    private Intent intent;
    private ListView listView;
    private List<Author> authors = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent createdIntent = getIntent();
        loggedInUser = (LoggedInUser) createdIntent.getSerializableExtra(LoginActivity.LOGGED_IN_USER_TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_authors);
        getSupportActionBar().setTitle(R.string.my_fav_authors);

        floatingActionButton = findViewById(R.id.fabAuthors);
        listView = findViewById(R.id.listViewFavouriteAuthors);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(EASY_LIBRARY_APP_DEFAULT_RTDB);
        myRef.keepSynced(true);

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    authors.clear();
                    for(DataSnapshot dn: snapshot.getChildren())
                    {
                        Author author = dn.getValue(Author.class);
                        if(author.getUsername().equals(loggedInUser.getUsername()))
                        authors.add(author);
                    }
                }

                AuthorAdapterToListView adapter = new AuthorAdapterToListView(getApplicationContext(), R.layout.list_view_author_custom_adapter,
                        authors, getLayoutInflater());
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.child(EASY_LIBRARY_APP_DEFAULT_RTDB).addValueEventListener(messageListener);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                Author author = authors.get(position);
                authors.remove(position);
                myRef.child(EASY_LIBRARY_APP_DEFAULT_RTDB).child(author.getUid()).removeValue();

                AuthorAdapterToListView adapter = new AuthorAdapterToListView(getApplicationContext(), R.layout.list_view_author_custom_adapter,
                        authors, getLayoutInflater());
                listView.setAdapter(adapter);

                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), AddAuthorActivity.class);
                intent.putExtra(LoginActivity.LOGGED_IN_USER_TAG, loggedInUser);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }
}
