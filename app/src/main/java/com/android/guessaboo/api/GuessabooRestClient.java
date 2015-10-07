package com.android.guessaboo.api;

import retrofit.RestAdapter;

/**
 * Created by gspl on 07-10-2015.
 */
public class GuessabooRestClient {

    private final static String API_URL = " http://j2infotech.in/guessaboo";
    private static GuessabooApiInterface guessabooApiInterface;
    private static RestAdapter restAdapter;

    static {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new GuessabooApiErrorHandler())
                .build();
    }

    public static GuessabooApiInterface getGuessabooApi() {
        if (guessabooApiInterface == null)
            guessabooApiInterface = restAdapter.create(GuessabooApiInterface.class);
        return guessabooApiInterface;
    }
}
