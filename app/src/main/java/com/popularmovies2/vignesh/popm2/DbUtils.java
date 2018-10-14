package com.popularmovies2.vignesh.popm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.popularmovies2.vignesh.popm2.data.MovieContract;
import com.popularmovies2.vignesh.popm2.data.MovieData;


class DbUtils {

    public static ContentValues getMovieDetails(MovieData movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MoviesEntry.COLUMN_TITLE, movie.getOriginalTitle());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_OVERVIEW, movie.getOverView());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_ID, movie.getId());
        return contentValues;
    }

    public static MovieData[] getFavoriteMovies(Context context) {
        Cursor cursor = context.getContentResolver().query(MovieContract.MOVIES_CONTENT_URI,
                null,
                null,
                null,
                null);

        MovieData[] moviesList = new MovieData[cursor.getCount()];
        int count = 0;

        try {
            while (cursor != null && cursor.moveToNext() && count < cursor.getCount()) {
                MovieData movie = new MovieData();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_ID)));
                movie.setOverView(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_POSTER_PATH)));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_TITLE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE)));
                moviesList[count] = movie;
                count++;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return moviesList;
    }
}
