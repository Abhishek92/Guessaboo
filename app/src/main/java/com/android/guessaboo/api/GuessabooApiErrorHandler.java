package com.android.guessaboo.api;

import com.android.guessaboo.model.CommonResponse;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hp pc on 07-10-2015.
 */
public class GuessabooApiErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause != null) {
            try {
                Response response = cause.getResponse();
                CommonResponse commonResponse = (CommonResponse) cause.getBodyAs(CommonResponse.class);
                if (response != null)
                    return new Exception(commonResponse != null ? commonResponse.getMsg() : "Unknown error, please try again.");
            } catch (Exception ex) {
                return new Exception("Unknown error, please try again.");
            }
        }
        return new Exception("Server Timeout error");
    }
}
