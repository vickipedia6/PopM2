package com.popularmovies2.vignesh.popm2.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.popularmovies2.vignesh.popm2";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final Uri MOVIES_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

    public static Uri buildMovieUriWithId(int id) {
        return MOVIES_CONTENT_URI.buildUpon().appendPath(Integer.toString(id))
                .build();
    }

    public static final class MoviesEntry implements BaseColumns {

        public static final String MOVIE_TABLE = "movies";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ID = "movie_id";
    }

}
