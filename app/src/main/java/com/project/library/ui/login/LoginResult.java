package com.project.library.ui.login;

import androidx.annotation.Nullable;

import com.project.library.data.model.LoggedInUser;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private LoggedInUser loggedInUser;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    LoginResult(@Nullable LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}