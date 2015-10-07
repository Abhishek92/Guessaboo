package com.android.guessaboo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gspl on 07-10-2015.
 */
public class LoginModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_fullname")
    @Expose
    private String userFullname;
    @SerializedName("user_email")
    @Expose
    private String userEmail;

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The userFullname
     */
    public String getUserFullname() {
        return userFullname;
    }

    /**
     *
     * @param userFullname
     * The user_fullname
     */
    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    /**
     *
     * @return
     * The userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     *
     * @param userEmail
     * The user_email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
