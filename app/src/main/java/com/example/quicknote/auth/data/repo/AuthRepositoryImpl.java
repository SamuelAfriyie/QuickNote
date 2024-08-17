package com.example.quicknote.auth.data.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quicknote.auth.data.failures.DatabaseNotFound;
import com.example.quicknote.auth.data.failures.EmailAlreadyExist;
import com.example.quicknote.auth.data.failures.NoAuthenticatedUser;
import com.example.quicknote.auth.data.failures.UserNameOrPassword;
import com.example.quicknote.auth.data.failures.UserNotFound;
import com.example.quicknote.auth.data.success.LoginSuccess;
import com.example.quicknote.auth.data.success.SignUpSuccess;
import com.example.quicknote.auth.domain.User;
import com.example.quicknote.core.Utils.PwdUtility;
import com.example.quicknote.core.Utils.Response;
import com.example.quicknote.core.data.Dao;
import com.example.quicknote.core.failures.AuthResponse;

public class AuthRepositoryImpl implements AuthRepository {

    private final Dao userDao;

    public AuthRepositoryImpl(Context context) {
        userDao = new Dao(context);
    }

    public Response<LoginSuccess, AuthResponse> loginUser(User user) {
        try {
            SQLiteDatabase db = userDao.getReadableDatabase();
            String[] projection = {Dao.COLUMN_USER_PASSWORD};
            String selection = Dao.COLUMN_USER_EMAIL+" = ?";
            String[] selectionArgs = {user.getEmail()};

            Cursor cursor = db.query(Dao.TABLE_USERS, projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                String storedHashedPassword = cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_USER_PASSWORD));
                cursor.close();
                // Check the provided password with the stored hashed password
                if(!PwdUtility.checkPassword(user.getPassword(), storedHashedPassword)) {
                    return Response.failure(new UserNameOrPassword("EmailPasswordIncorrect"));
                }
                Response<Integer, AuthResponse> res =  updateLoginStatus(user.getEmail(), true);
                if(res.isFailure()) {
                    return  Response.failure(new DatabaseNotFound("StatusUpdateFailed","Unable to update login status"));
                }
                return Response.success(new LoginSuccess("LoginSuccess"));
            } else {
                cursor.close();
                return Response.failure(new UserNotFound("UserNotFound")); // User not found
            }
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened"));
        }
    }

    @Override
    public Response<SignUpSuccess, AuthResponse> createUser(User user) {
        try {
            if (emailAlreadyExist(user.getEmail())) {
                return Response.failure(new EmailAlreadyExist("UserCreationFailed"));
            }

            SQLiteDatabase db = userDao.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Dao.COLUMN_USER_NAME, user.getName());
            values.put(Dao.COLUMN_USER_EMAIL, user.getEmail());
            values.put(Dao.COLUMN_USER_IS_LOGIN, 0); //set it to false after creating an account
            values.put(Dao.COLUMN_USER_PASSWORD, PwdUtility.hashPassword(user.getPassword()));
            Log.d("DatabaseDebug", "Inserting user into database...");
            long result = db.insert(Dao.TABLE_USERS, null, values);
            Log.d("DatabaseDebug", "Insert result: " + result);
            db.close();
            return Response.success(new SignUpSuccess("SignUpSuccessful"));
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }
    }

    @Override
    public Response<User, AuthResponse> fetchCurrentLoggedInUser() {
        try {
            SQLiteDatabase db = userDao.getReadableDatabase();

            // Define the query to fetch the first user with login_status set to 1
            String[] projection = {Dao.COLUMN_USER_NAME, Dao.COLUMN_USER_ID, Dao.COLUMN_USER_EMAIL}; // Specify the columns to return
            String selection = Dao.COLUMN_USER_IS_LOGIN + " = ?";
            String[] selectionArgs = {"1"}; // 1 indicates logged-in status

            Cursor cursor = db.query(
                    Dao.TABLE_USERS, projection, selection, selectionArgs, null, null, null, "1");

            if (cursor.moveToFirst()) {
                User logInUser = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Dao.COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Dao.COLUMN_USER_EMAIL))
                );
                return Response.success(logInUser);
            }
            cursor.close();
            return Response.failure(new NoAuthenticatedUser("NoAuthenticatedUser"));
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened"));
        }
    }

    private boolean emailAlreadyExist(String email) {
        SQLiteDatabase db = userDao.getReadableDatabase();

        // Define the query to check for the existence of the email
        String[] projection = {Dao.COLUMN_USER_EMAIL};
        String selection = Dao.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(Dao.TABLE_USERS, projection, selection, selectionArgs, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Response<Integer, AuthResponse> updateLoginStatus(String email, boolean isLoggedIn) {
        try {
            SQLiteDatabase db = userDao.getWritableDatabase();

            // Prepare the content values to update
            ContentValues values = new ContentValues();
            values.put(Dao.COLUMN_USER_IS_LOGIN, isLoggedIn ? 1 : 0); // Assuming login_status is an integer (1 for true, 0 for false)

            // Define the where clause to update the correct record
            String whereClause = Dao.COLUMN_USER_EMAIL + " = ?";
            String[] whereArgs = {email};

            // Update the login status in the database
            int rowsUpdated = db.update(Dao.TABLE_USERS, values, whereClause, whereArgs);

            // Optional: Check if the update was successful
            if (rowsUpdated == 0) {
                // Handle the case where no rows were updated (e.g., user not found)
                return Response.failure(new UserNotFound("User not found"));
            }
            return Response.success(1);
        } catch (Exception e) {
            return Response.failure(new DatabaseNotFound(e.getMessage(), "Database cannot be opened for writing"));
        }

    }

}
