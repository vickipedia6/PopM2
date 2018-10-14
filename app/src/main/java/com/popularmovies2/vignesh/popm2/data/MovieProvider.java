package com.popularmovies2.vignesh.popm2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.popularmovies2.vignesh.popm2.data.MovieContract.CONTENT_AUTHORITY;
import static com.popularmovies2.vignesh.popm2.data.MovieContract.MOVIES_CONTENT_URI;
import static com.popularmovies2.vignesh.popm2.data.MovieContract.PATH_MOVIE;

public class MovieProvider extends ContentProvider {
    private static final int CODE_MOVIE = 100;
    private static final int CODE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE, CODE_MOVIE);
        matcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE + "/#", CODE_MOVIE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                cursor = db.query(MovieContract.MoviesEntry.MOVIE_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case CODE_MOVIE_WITH_ID:
                cursor = db.query(MovieContract.MoviesEntry.MOVIE_TABLE,
                        projection,
                        selection,
                        null,
                        null,
                        null,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long id;

        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE_WITH_ID:
                id = db.insert(MovieContract.MoviesEntry.MOVIE_TABLE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MOVIES_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numberOfDeletedFavorites;
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE_WITH_ID:
                numberOfDeletedFavorites = db.delete(MovieContract.MoviesEntry.MOVIE_TABLE,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }

        if (numberOfDeletedFavorites != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numberOfDeletedFavorites;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
