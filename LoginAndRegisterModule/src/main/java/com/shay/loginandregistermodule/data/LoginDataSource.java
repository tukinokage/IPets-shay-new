package com.shay.loginandregistermodule.data;

import com.shay.loginandregistermodule.data.model.LoggedInUser;

import java.io.IOException;

import retrofit2.Retrofit;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {


            // TODO: handle loggedInUser authentication

            return new Result.Error();
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    class MyException extends Exception{

        public MyException(String message) {
            super(message);
        }
    }
}
