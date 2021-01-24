package com.project.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.R;
import com.project.library.data.model.LoggedInUser;
import com.project.library.ui.login.LoginActivity;

public class MainMenuActivity extends AppCompatActivity {
    private Spinner spinnerLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.support_simple_spinner_dropdown_item);

        spinnerLanguage.setAdapter(adapter);
        spinnerLanguage.setSelection(0);
        final Intent intent = getIntent();
        Button buttonToMyLibrary = findViewById(R.id.buttonMainPage);
        buttonToMyLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoggedInUser loggedInUser = (LoggedInUser) intent.getSerializableExtra(LoginActivity.LOGGED_IN_USER_TAG);
                Intent intent1 = new Intent(getApplicationContext(), MainPage.class);
                intent1.putExtra(LoginActivity.LOGGED_IN_USER_TAG, loggedInUser);
                startActivity(intent1);
            }
        });

        Button buttonToNewestBooks = findViewById(R.id.buttonToNewestBooks);
        buttonToNewestBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), NewestBooksActivity.class);
                startActivity(intent2);
            }
        });

        Button buttonToNearestLibs = findViewById(R.id.buttonToNearestLibs);
        buttonToNearestLibs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent3);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sh
                = getSharedPreferences(LoginActivity.USER_PREFERENCES,
                0);
        int language = sh.getInt("language", 0);
        spinnerLanguage.setSelection(language);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPreferences
                = getSharedPreferences(LoginActivity.USER_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putInt("language",
                spinnerLanguage.getSelectedItemPosition());
        myEdit.commit();
    }
}