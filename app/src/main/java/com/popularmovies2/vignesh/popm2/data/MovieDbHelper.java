package com.popularmovies2.vignesh.popm2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MovieDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MoviesEntry.MOVIE_TABLE + " (" +
            MovieContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MovieContract.MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            MovieContract.MoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
            MovieContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            MovieContract.MoviesEntry.COLUMN_ID + " INTEGER NOT NULL" +
           ");";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesEntry.MOVIE_TABLE);
        onCreate(db);
    }
}
