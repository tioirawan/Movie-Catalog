package com.indmind.moviecataloguetwo.models;

import java.util.ArrayList;

public class MovieApiResponse {
    private final int page;
    private final long total_results;
    private final long total_pages;
    private final ArrayList<Movie> results;

    public MovieApiResponse(int page, long total_results, long total_pages, ArrayList<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}
