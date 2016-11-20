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

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

import static android.widget.Toast.LENGTH_SHORT;

public class DetailsFragment extends Fragment {

    private static final String ARG_MOVIE = "com.juliakrause.myomdb.arg.MOVIE";

    private Movie movie;

    private DaoSession daoSession;

    private MovieDao movieDao;

    private List<com.juliakrause.greendao.generated.Movie> localMovieList;

    public List<com.juliakrause.greendao.generated.Movie> getLocalMovieList() {
        return localMovieList;
    }

    private Button watchButton;

    private Button favoritesButton;

    public void setLocalMovieList(List<com.juliakrause.greendao.generated.Movie> localMovieList) {
        this.localMovieList = localMovieList;
    }

    public MovieDao getMovieDao() {
        return movieDao;
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public DaoSession getDaoSession() {
        return daoSession;
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
        if(localMovieList != null) {
            localMovieList.clear();
        }
        QueryBuilder<com.juliakrause.greendao.generated.Movie> builder = movieDao.queryBuilder();
        builder.where(MovieDao.Properties.ImdbId.eq(movie.getImdbID()));
        localMovieList = builder.build().list();
        if(localMovieList.size() == 0) {
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

    private void updateButtons() {
        Boolean localMovie = localMovie();
        if(!localMovie || localMovieList.get(0).getToWatch() == 0) {
            watchButton.setText("+ WATCH");
        } else {
            watchButton.setText("- WATCH");
        }

        if(!localMovie || localMovieList.get(0).getFavorite() == 0) {
            favoritesButton.setText("+ LIKE");
        } else {
            favoritesButton.setText("- LIKE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        if (movie != null) {
            ((TextView) (view.findViewById(R.id.tvTitle))).setText(movie.getTitle());
            ((TextView) (view.findViewById(R.id.tvYear))).setText(movie.getYear());
            ((TextView) (view.findViewById(R.id.tvRated))).setText(movie.getRated());
            ((TextView) (view.findViewById(R.id.tvReleased))).setText(movie.getReleased());
            ((TextView) (view.findViewById(R.id.tvRuntime))).setText(movie.getRuntime());
            ((TextView) (view.findViewById(R.id.tvGenre))).setText(movie.getGenre());
            ((TextView) (view.findViewById(R.id.tvDirector))).setText(movie.getDirector());
            ((TextView) (view.findViewById(R.id.tvWriter))).setText(movie.getWriter());
            ((TextView) (view.findViewById(R.id.tvActors))).setText(movie.getActors());
            ((TextView) (view.findViewById(R.id.tvPlot))).setText(movie.getPlot());
            watchButton = (Button) (view.findViewById(R.id.addToWatchList));
            favoritesButton = (Button) (view.findViewById(R.id.addToFavorites));

            movieDao = daoSession.getMovieDao();
            final com.juliakrause.greendao.generated.Movie dbMovie = prepareMovie();

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
                            long pk = localMovieList.get(0).getId();
                            dbMovie.setId(pk);
                            if(localMovieList.get(0).getToWatch() == 0) {
                                dbMovie.setToWatch(1);
                            } else {
                                dbMovie.setToWatch(0);
                            }

                            movieDao.update(dbMovie);
                        }
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
                            long pk = localMovieList.get(0).getId();
                            dbMovie.setId(pk);
                            if(localMovieList.get(0).getFavorite() == 0) {
                                dbMovie.setFavorite(1);
                            } else {
                                dbMovie.setFavorite(0);
                            }
                            movieDao.update(dbMovie);
                        }
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