package com.juliakrause.myomdb;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class DetailsFragment extends Fragment {

    private static final String ARG_MOVIE = "com.juliakrause.myomdb.arg.MOVIE";
    private Movie movie;

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
                    Toast watchToast = new Toast(getContext()).makeText(getContext(), "movie will be added to watch list", LENGTH_SHORT);
                    watchToast.show();
                }
            };
            watchButton.setOnClickListener(watchListener);
            Button favoritesButton = (Button) (view.findViewById(R.id.addToFavorites));
            View.OnClickListener favoritesListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast favToast = new Toast(getContext()).makeText(getContext(), "movie will be added to favorites list", LENGTH_SHORT);
                    favToast.show();
                }
            };
            favoritesButton.setOnClickListener(favoritesListener);

        }
        return view;
    }
}