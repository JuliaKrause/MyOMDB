package com.juliakrause.myomdb;

import android.app.DownloadManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Julia on 13.11.2016.
 */

public class ToWatchArrayAdapter extends ArrayAdapter<com.juliakrause.greendao.generated.Movie> {

    private DaoSession daoSession;

    private ToWatchListFragment wf;

    public ToWatchArrayAdapter(Context context, List<com.juliakrause.greendao.generated.Movie> objects, ToWatchListFragment wf) {

        super(context, 0, objects);
        this.wf = wf;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_towatch_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            viewHolder.tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            viewHolder.deleteButton = (Button) convertView.findViewById(R.id.deleteFromWatchList);

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
                    movie.setToWatch(0);
                    movieDao.update(movie);

                    QueryBuilder<Movie> builder = movieDao.queryBuilder();
                    WhereCondition condition1 = builder.or(MovieDao.Properties.Favorite.eq(null),
                            MovieDao.Properties.Favorite.eq("0"));
                    WhereCondition condition2 = builder.or(MovieDao.Properties.ToWatch.eq(null),
                        MovieDao.Properties.ToWatch.eq("0"));
                    builder.where(condition1, condition2);
                    DeleteQuery<Movie> deleteQuery = builder.buildDelete();
                    deleteQuery.executeDeleteWithoutDetachingEntities();
                    wf.updateMovies();
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
