package com.juliakrause.myomdb;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.app.Activity;
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
            System.out.println(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvYear = (TextView) convertView.findViewById(R.id.tvYear);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            System.out.println(convertView.findViewById(R.id.tvTitle));

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);
        if (movie != null) {
            System.out.println(movie.getTitle());
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvYear.setText(movie.getYear());
            viewHolder.tvType.setText(movie.getType());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvYear;
        TextView tvType;
    }

}
