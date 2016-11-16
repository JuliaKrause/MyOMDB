package com.juliakrause.myomdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Julia on 13.11.2016.
 */

public class FavoritesArrayAdapter extends ArrayAdapter<Movie> {

    public FavoritesArrayAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movie movie = getItem(position);
        if (movie != null) {
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvType.setText('(' + movie.getType() + ')');
            viewHolder.tvYear.setText(movie.getYear());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvYear;
        TextView tvType;
    }

}
