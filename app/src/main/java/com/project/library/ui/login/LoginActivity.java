package com.project.library.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.library.R;
import com.project.library.BookDB;
import com.project.library.MainMenuActivity;
import com.project.library.data.model.LoggedInUser;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGGED_IN_USER_TAG = "loggedInUser";
    public static final String USER_PREFERENCES = "UserPreferences";
    private LoginViewModel loginViewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.activity_login_title));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getLoggedInUser() != null) {
                    updateUiWithUser(loginResult);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sh
                = getSharedPreferences(USER_PREFERENCES,
                0);

        String username = sh.getString("username", getString(R.string.placeholder));
        String password = sh.getString("password", getString(R.string.placeholder));
        usernameEditText.setText(username);
        passwordEditText.setText(password);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPreferences
                = getSharedPreferences(USER_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putString("username",
                usernameEditText.getText().toString());
        myEdit.putString("password",
                passwordEditText.getText().toString());
        myEdit.commit();
    }

    private void updateUiWithUser(LoginResult loginResult) {
        //LoggedInUserView model = loginResult.getSuccess();
        LoggedInUser loggedInUser = loginResult.getLoggedInUser();
        BookDB bookDB = BookDB.getInstance(getApplicationContext());
        LoggedInUser existingUser = bookDB.getLoggedInUserDao().getLoggedInUser(loggedInUser.getUsername());
        if(existingUser == null)
        bookDB.getLoggedInUserDao().insert(loggedInUser);
        String welcome = getString(R.string.welcome) + loggedInUser.getUsername();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        intent.putExtra(LOGGED_IN_USER_TAG, loggedInUser);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}