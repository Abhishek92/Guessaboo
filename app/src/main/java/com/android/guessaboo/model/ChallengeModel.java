package com.android.guessaboo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gspl on 08-10-2015.
 */
public class ChallengeModel implements Parcelable {
    @SerializedName("songs")
    @Expose
    private String songs;
    @SerializedName("shareimage")
    @Expose
    private String shareimage;
    @SerializedName("touser")
    @Expose
    private String touser;
    @SerializedName("fromusername")
    @Expose
    private String fromusername;
    @SerializedName("songduration")
    @Expose
    private String songduration;
    @SerializedName("challengemessage")
    @Expose
    private String challengemessage;
    @SerializedName("congurationmessage")
    @Expose
    private String congurationmessage;
    @SerializedName("defeedmessage")
    @Expose
    private String defeedmessage;
    @SerializedName("decayname")
    @Expose
    private String decayname;

    /**
     *
     * @return
     * The songs
     */
    public String getSongs() {
        return songs;
    }

    /**
     *
     * @param songs
     * The songs
     */
    public void setSongs(String songs) {
        this.songs = songs;
    }

    /**
     *
     * @return
     * The shareimage
     */
    public String getShareimage() {
        return shareimage;
    }

    /**
     *
     * @param shareimage
     * The shareimage
     */
    public void setShareimage(String shareimage) {
        this.shareimage = shareimage;
    }

    /**
     *
     * @return
     * The touser
     */
    public String getTouser() {
        return touser;
    }

    /**
     *
     * @param touser
     * The touser
     */
    public void setTouser(String touser) {
        this.touser = touser;
    }

    /**
     *
     * @return
     * The fromusername
     */
    public String getFromusername() {
        return fromusername;
    }

    /**
     *
     * @param fromusername
     * The fromusername
     */
    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    /**
     *
     * @return
     * The songduration
     */
    public String getSongduration() {
        return songduration;
    }

    /**
     *
     * @param songduration
     * The songduration
     */
    public void setSongduration(String songduration) {
        this.songduration = songduration;
    }

    /**
     *
     * @return
     * The challengemessage
     */
    public String getChallengemessage() {
        return challengemessage;
    }

    /**
     *
     * @param challengemessage
     * The challengemessage
     */
    public void setChallengemessage(String challengemessage) {
        this.challengemessage = challengemessage;
    }

    /**
     *
     * @return
     * The congurationmessage
     */
    public String getCongurationmessage() {
        return congurationmessage;
    }

    /**
     *
     * @param congurationmessage
     * The congurationmessage
     */
    public void setCongurationmessage(String congurationmessage) {
        this.congurationmessage = congurationmessage;
    }

    /**
     *
     * @return
     * The defeedmessage
     */
    public String getDefeedmessage() {
        return defeedmessage;
    }

    /**
     *
     * @param defeedmessage
     * The defeedmessage
     */
    public void setDefeedmessage(String defeedmessage) {
        this.defeedmessage = defeedmessage;
    }

    /**
     *
     * @return
     * The decayname
     */
    public String getDecayname() {
        return decayname;
    }

    /**
     *
     * @param decayname
     * The decayname
     */
    public void setDecayname(String decayname) {
        this.decayname = decayname;
    }



    protected ChallengeModel(Parcel in) {
        songs = in.readString();
        shareimage = in.readString();
        touser = in.readString();
        fromusername = in.readString();
        songduration = in.readString();
        challengemessage = in.readString();
        congurationmessage = in.readString();
        defeedmessage = in.readString();
        decayname = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songs);
        dest.writeString(shareimage);
        dest.writeString(touser);
        dest.writeString(fromusername);
        dest.writeString(songduration);
        dest.writeString(challengemessage);
        dest.writeString(congurationmessage);
        dest.writeString(defeedmessage);
        dest.writeString(decayname);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChallengeModel> CREATOR = new Parcelable.Creator<ChallengeModel>() {
        @Override
        public ChallengeModel createFromParcel(Parcel in) {
            return new ChallengeModel(in);
        }

        @Override
        public ChallengeModel[] newArray(int size) {
            return new ChallengeModel[size];
        }
    };
}