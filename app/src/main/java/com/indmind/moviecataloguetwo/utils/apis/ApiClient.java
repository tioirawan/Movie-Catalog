package com.indmind.moviecataloguetwo.utils.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w92";
    public static final String POSTER_BASE_URL_185 = "https://image.tmdb.org/t/p/w185";
    private static final String BASE_URL;
    private static Retrofit retrofit = null;
    private static ApiService service = null;

    static {
        BASE_URL = "https://api.themoviedb.org/3/";
    }

    private ApiClient() {
    }

    public static ApiService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if (service == null) {
            service = retrofit.create(ApiService.class);
        }

        return service;
    }
}
