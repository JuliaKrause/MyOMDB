package com.juliakrause.myomdb;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.juliakrause.greendao.generated.DaoSession;
import com.juliakrause.greendao.generated.MovieDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

import static android.widget.Toast.LENGTH_SHORT;

public class DetailsFragment extends Fragment {

    private static final String ARG_MOVIE = "com.juliakrause.myomdb.arg.MOVIE";

    private Movie movie;

    private DaoSession daoSession;

    private MovieDao movieDao;

    private com.juliakrause.greendao.generated.Movie localMovie;

    private Button watchButton;

    private Button favoritesButton;

    public void setLocalMovie() {
        QueryBuilder<com.juliakrause.greendao.generated.Movie> builder = movieDao.queryBuilder();
        builder.where(MovieDao.Properties.ImdbId.eq(movie.getImdbID()));
        List<com.juliakrause.greendao.generated.Movie> list = builder.build().list();
        if (list != null && list.size() > 0) {
            this.localMovie = list.get(0);
        }
    }

    public void setMovieDao() {

        this.movieDao = this.daoSession.getMovieDao();
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    // Required empty public constructor
    public DetailsFragment() {}

    public static DetailsFragment newInstance (Movie movie){
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    private Boolean localMovie() {
        if(localMovie == null) {
            return false;
        } else {
            return true;
        }
    }

    private com.juliakrause.greendao.generated.Movie prepareMovie() {
        com.juliakrause.greendao.generated.Movie dbMovie = new com.juliakrause.greendao.generated.Movie();
        dbMovie.setImdbId(movie.getImdbID());
        dbMovie.setTitle(movie.getTitle());
        dbMovie.setType(movie.getType());
        dbMovie.setYear(movie.getYear());
        return dbMovie;
    }

    private Boolean isFavorite() {
        if(!localMovie()) {
            return false;
        } else {
            if(localMovie.getFavorite() == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private Boolean isOnToWatchList() {
        if(!localMovie()) {
            return false;
        } else {
            if(localMovie.getToWatch() == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void updateButtons() {
        if(!isOnToWatchList()) {
            watchButton.setText("+ WATCH");
        } else {
            watchButton.setText("- WATCH");
        }

        if(!isFavorite()) {
            favoritesButton.setText("+ LIKE");
        } else {
            favoritesButton.setText("- LIKE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        if (movie != null) {
            ((TextView) (view.findViewById(R.id.movieTitle))).setText(movie.getTitle());
            ((TextView) (view.findViewById(R.id.movieYear))).setText(movie.getYear());
            ((TextView) (view.findViewById(R.id.movieRated))).setText(movie.getRated());
            ((TextView) (view.findViewById(R.id.movieReleased))).setText(movie.getReleased());
            ((TextView) (view.findViewById(R.id.movieRuntime))).setText(movie.getRuntime());
            ((TextView) (view.findViewById(R.id.movieGenre))).setText(movie.getGenre());
            ((TextView) (view.findViewById(R.id.movieDirector))).setText(movie.getDirector());
            ((TextView) (view.findViewById(R.id.movieWriter))).setText(movie.getWriter());
            ((TextView) (view.findViewById(R.id.movieActors))).setText(movie.getActors());
            ((TextView) (view.findViewById(R.id.moviePlot))).setText(movie.getPlot());
            watchButton = (Button) (view.findViewById(R.id.addToWatchList));
            favoritesButton = (Button) (view.findViewById(R.id.addToFavorites));

            movieDao = daoSession.getMovieDao();
            final com.juliakrause.greendao.generated.Movie dbMovie = prepareMovie();
            setLocalMovie();
            updateButtons();

            View.OnClickListener watchListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!localMovie()) {
                            dbMovie.setToWatch(1);
                            movieDao.insert(dbMovie);
                        } else {
                            //movie already exists in local db
                            long pk = localMovie.getId();
                            dbMovie.setId(pk);
                            if(!isOnToWatchList()) {
                                dbMovie.setToWatch(1);
                            } else {
                                dbMovie.setToWatch(0);
                            }

                            movieDao.update(dbMovie);
                        }
                        setLocalMovie();
                        updateButtons();
                        Toast watchToast = new Toast(getContext()).makeText(getContext(), "OK", LENGTH_SHORT);
                        watchToast.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            watchButton.setOnClickListener(watchListener);


            View.OnClickListener favoritesListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!localMovie()) {
                            dbMovie.setFavorite(1);
                            movieDao.insert(dbMovie);
                        } else {
                            //movie already exists in local db
                            long pk = localMovie.getId();
                            dbMovie.setId(pk);
                            if(!isFavorite()) {
                                dbMovie.setFavorite(1);
                            } else {
                                dbMovie.setFavorite(0);
                            }
                            movieDao.update(dbMovie);
                        }
                        setLocalMovie();
                        updateButtons();
                        Toast watchToast = new Toast(getContext()).makeText(getContext(), "OK", LENGTH_SHORT);
                        watchToast.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            favoritesButton.setOnClickListener(favoritesListener);
        }

        return view;
    }
}