package com.popularmovies2.vignesh.popm2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.popularmovies2.vignesh.popm2.data.MovieContract;
import com.popularmovies2.vignesh.popm2.data.MovieData;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.popularmovies2.vignesh.popm2.MainActivity.MOVIE;


public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.original_title)

    TextView title;

    @BindView(R.id.release_date)

    TextView releaseDate;

    @BindView(R.id.user_rating)

    TextView userRating;

    @BindView(R.id.poster_image)

    ImageView poster;

    @BindView(R.id.star_image)

    ImageView starImage;

    @BindDrawable(R.drawable.heart_outline)

    Drawable emptyStar;

    @BindDrawable(R.drawable.heart_outline_filled)

    Drawable fillStar;

    private MovieData selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        selectedMovie = getIntent().getParcelableExtra(MOVIE);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            collapsingToolbarLayout.setTitle(selectedMovie.getOriginalTitle());
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        }

        title.setText(selectedMovie.getOriginalTitle());
        userRating.setText(selectedMovie.getVoteAverage() + "/10");
        String posterString = selectedMovie.getPosterPath();

        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + posterString).into(poster);

        String[] r_date = selectedMovie.getReleaseDate().split("-");

        releaseDate.setText(r_date[0]);

        if(isFavorite()){
            starImage.setImageDrawable(fillStar);
        }else{
            starImage.setImageDrawable(emptyStar);
        }
    }

    public void animate(View view) {
        if(isFavorite()){
            starImage.setImageDrawable(emptyStar);
            removeMovieFromFavorites();
            Snackbar.make(view, "Movie removed from Favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            starImage.setImageDrawable(fillStar);
            addMovieToFavorites();
            Snackbar.make(view, "Movie added to Favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent upIntent = getSupportParentActivityIntent();
                if (NavUtils.shouldUpRecreateTask(this, upIntent) && upIntent != null){
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                }else{
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeMovieFromFavorites() {
        String selection = MovieContract.MoviesEntry.COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(selectedMovie.getId())};
        getContentResolver().delete(MovieContract.buildMovieUriWithId(selectedMovie.getId()),
               selection, selectionArgs);
    }

    synchronized private void addMovieToFavorites() {
        getContentResolver().insert(MovieContract.buildMovieUriWithId(selectedMovie.getId()),
                DbUtils.getMovieDetails(selectedMovie));
    }

    private boolean isFavorite() {
        String[] projection = {MovieContract.MoviesEntry.COLUMN_ID};
        String selection = MovieContract.MoviesEntry.COLUMN_ID + " = " + selectedMovie.getId();

        Cursor cursor = getContentResolver().query(MovieContract.buildMovieUriWithId(selectedMovie.getId()),
                projection,
                selection,
                null,
                null,
                null);

        cursor.close();

        return cursor.getCount() > 0;
    }

}
