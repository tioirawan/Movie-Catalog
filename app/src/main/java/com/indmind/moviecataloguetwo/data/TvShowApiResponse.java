package com.indmind.moviecataloguetwo.data;

import java.util.ArrayList;

public class TvShowApiResponse {
    private final ArrayList<TvShow> results;

    @SuppressWarnings("unused")
    public TvShowApiResponse(ArrayList<TvShow> results) {
        this.results = results;
    }

    public ArrayList<TvShow> getResults() {
        return results;
    }
}
