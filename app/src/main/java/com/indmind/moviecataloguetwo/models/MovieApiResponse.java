package com.indmind.moviecataloguetwo.models;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MovieApiResponse {
    private final ArrayList<Movie> results;

    public MovieApiResponse(ArrayList<Movie> results) {
        this.results = results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}
