package com.juliakrause.myomdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Julia on 13.11.2016.
 */

public class FavoritesArrayAdapter extends ArrayAdapter<com.juliakrause.greendao.generated.Movie> {

    private DaoSession daoSession;

    private FavoritesFragment ff;

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public FavoritesArrayAdapter(Context context, List<com.juliakrause.greendao.generated.Movie> objects, FavoritesFragment ff) {
        super(context, 0, objects);
        this.ff = ff;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favorites_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
            viewHolder.movieType = (TextView) convertView.findViewById(R.id.movieType);
            viewHolder.movieYear = (TextView) convertView.findViewById(R.id.movieYear);
            viewHolder.deleteButton = (Button) convertView.findViewById(R.id.deleteFromFavorites);
            viewHolder.addButton = (Button) convertView.findViewById(R.id.addToWatchList);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = getItem(position);
        if (movie != null) {
            viewHolder.movieTitle.setText(movie.getTitle());
            viewHolder.movieType.setText('(' + movie.getType() + ')');
            viewHolder.movieYear.setText(movie.getYear());

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDao movieDao = daoSession.getMovieDao();
                    movie.setFavorite(0);
                    movieDao.update(movie);
                    ff.updateMovies();
                }
            });

            if(movie.getToWatch() == 0) {
                viewHolder.addButton.setText("+ WATCH");
            } else {
                viewHolder.addButton.setText("- WATCH");
            }

            viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDao movieDao = daoSession.getMovieDao();
                    if(movie.getToWatch() == 0) {
                        movie.setToWatch(1);
                    } else {
                        movie.setToWatch(0);
                    }
                    movieDao.update(movie);
                    ff.updateMovies();

                    Toast watchListToast = new Toast(getContext()).makeText(getContext(), "OK", LENGTH_SHORT);
                    watchListToast.show();

                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView movieTitle;
        TextView movieYear;
        TextView movieType;
        Button deleteButton;
        Button addButton;
    }

}
