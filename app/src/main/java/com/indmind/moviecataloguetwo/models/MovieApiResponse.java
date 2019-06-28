package com.indmind.moviecataloguetwo.models;

import java.util.ArrayList;

public class MovieApiResponse {
    private final int page;
    private final long total_results;
    private final long total_pages;
    private final ArrayList<Movie> results;

    public ArrayList<Movie> getResults() {
        return results;
    }
}
