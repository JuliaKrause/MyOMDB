package com.juliakrause.myomdb;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Julia on 13.11.2016.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
            viewHolder.movieType = (TextView) convertView.findViewById(R.id.movieType);
            viewHolder.movieYear = (TextView) convertView.findViewById(R.id.movieYear);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);
        if (movie != null) {
            viewHolder.movieTitle.setText(movie.getTitle());
            viewHolder.movieType.setText('(' + movie.getType() + ')');
            viewHolder.movieYear.setText(movie.getYear());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView movieTitle;
        TextView movieYear;
        TextView movieType;
    }

}
