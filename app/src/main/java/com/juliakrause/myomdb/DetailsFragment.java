package com.juliakrause.myomdb;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        }
        return view;
    }
}






