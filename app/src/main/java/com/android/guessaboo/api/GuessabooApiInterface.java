package com.android.guessaboo.api;

import com.android.guessaboo.model.CommonResponse;
import com.android.guessaboo.model.LoginModel;
import com.squareup.okhttp.Call;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by gspl on 07-10-2015.
 */
public interface GuessabooApiInterface {

    @FormUrlEncoded
    @POST("/api.php")
    void doLogin(@Query("rquest") String rquest , @FieldMap Map<String, String> request,Callback<LoginModel> loginModelCallback);

    @FormUrlEncoded
    @POST("/api.php")
    void doShareImage(@Query("rquest") String rquest , @FieldMap Map<String, Object> request,Callback<CommonResponse> commonResponseCallback);
}
