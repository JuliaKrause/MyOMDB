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

            Button watchButton = (Button) (view.findViewById(R.id.addToWatchList));
            View.OnClickListener watchListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDao movieDao = daoSession.getMovieDao();
                    com.juliakrause.greendao.generated.Movie dbMovie = new com.juliakrause.greendao.generated.Movie();
                    dbMovie.setImdbId(movie.getImdbID());
                    dbMovie.setTitle(movie.getTitle());
                    dbMovie.setType(movie.getType());
                    dbMovie.setYear(movie.getYear());
                    dbMovie.setToWatch(1);
                    try {
                        QueryBuilder<com.juliakrause.greendao.generated.Movie> builder = movieDao.queryBuilder();
                        builder.where(MovieDao.Properties.ImdbId.eq(dbMovie.getImdbId()));
                        List<com.juliakrause.greendao.generated.Movie> list = builder.build().list();
                        if(list.size() == 0) {
                            movieDao.insert(dbMovie);
                        } else if(list.size() == 1) {
                            //movie already exists in local db
                            long pk = list.get(0).getId();
                            dbMovie.setId(pk);
                            movieDao.update(dbMovie);
                        }
                        Toast watchToast = new Toast(getContext()).makeText(getContext(), "OK", LENGTH_SHORT);
                        watchToast.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            watchButton.setOnClickListener(watchListener);

            Button favoritesButton = (Button) (view.findViewById(R.id.addToFavorites));
            View.OnClickListener favoritesListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDao movieDao = daoSession.getMovieDao();
                    com.juliakrause.greendao.generated.Movie dbMovie = new com.juliakrause.greendao.generated.Movie();
                    dbMovie.setImdbId(movie.getImdbID());
                    dbMovie.setTitle(movie.getTitle());
                    dbMovie.setType(movie.getType());
                    dbMovie.setYear(movie.getYear());
                    dbMovie.setFavorite(1);
                    try {
                        QueryBuilder<com.juliakrause.greendao.generated.Movie> builder = movieDao.queryBuilder();
                        builder.where(MovieDao.Properties.ImdbId.eq(dbMovie.getImdbId()));
                        List<com.juliakrause.greendao.generated.Movie> list = builder.build().list();
                        if(list.size() == 0) {
                            movieDao.insert(dbMovie);
                        } else if(list.size() == 1) {
                            //movie already exists in local db
                            long pk = list.get(0).getId();
                            dbMovie.setId(pk);
                            movieDao.update(dbMovie);
                        }
                        Toast watchToast = new Toast(getContext()).makeText(getContext(), "OK", LENGTH_SHORT);
                        watchToast.show();
                    } catch (Exception e) {
                        Toast favToast = new Toast(getContext()).makeText(getContext(), "added to Favorites", LENGTH_SHORT);
                        favToast.show();
                    }
                }
            };
            favoritesButton.setOnClickListener(favoritesListener);
        }

        return view;
    }
}