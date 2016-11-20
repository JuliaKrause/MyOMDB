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
import android.widget.Toast;

import com.juliakrause.greendao.generated.*;
import com.juliakrause.greendao.generated.Movie;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

import static android.widget.Toast.LENGTH_SHORT;

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
            viewHolder.addButton = (Button) convertView.findViewById(R.id.addToFavoriteList);

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
                    System.out.println(movieDao.getKey(movie));

                    wf.updateMovies();
                }
            });

            if(movie.getFavorite() == 0) {
                viewHolder.addButton.setText("+ LIKE");
            } else {
                viewHolder.addButton.setText("- LIKE");
            }

            viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDao movieDao = daoSession.getMovieDao();
                    if(movie.getFavorite() == 0) {
                        movie.setFavorite(1);
                    } else {
                        movie.setFavorite(0);
                    }
                    movieDao.update(movie);
                    wf.updateMovies();

                    Toast favToast = new Toast(getContext()).makeText(getContext(), "OK", LENGTH_SHORT);
                    favToast.show();
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
        Button addButton;
    }

}
