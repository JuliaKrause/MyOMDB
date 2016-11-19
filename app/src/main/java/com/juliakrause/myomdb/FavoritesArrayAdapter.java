package com.juliakrause.myomdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Julia on 13.11.2016.
 */

public class FavoritesArrayAdapter extends ArrayAdapter<com.juliakrause.greendao.generated.Movie> {

    private DaoSession daoSession;

    private FavoritesFragment ff;

    public DaoSession getDaoSession() {
        return daoSession;
    }

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
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            viewHolder.tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            viewHolder.deleteButton = (Button) convertView.findViewById(R.id.deleteFromFavorites);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = getItem(position);
        if (movie != null) {
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvType.setText('(' + movie.getType() + ')');
            viewHolder.tvYear.setText(movie.getYear());

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDao movieDao = daoSession.getMovieDao();
                    movie.setFavorite(0);
                    movieDao.update(movie);

                    QueryBuilder<Movie> builder = movieDao.queryBuilder();
                    WhereCondition condition1 = builder.or(MovieDao.Properties.Favorite.eq(null),
                            MovieDao.Properties.Favorite.eq("0"));
                    WhereCondition condition2 = builder.or(MovieDao.Properties.ToWatch.eq(null),
                            MovieDao.Properties.ToWatch.eq("0"));
                    builder.where(condition1, condition2);
                    DeleteQuery<Movie> deleteQuery = builder.buildDelete();
                    deleteQuery.executeDeleteWithoutDetachingEntities();

                    ff.updateMovies();
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvYear;
        TextView tvType;
        Button deleteButton;
    }

}
