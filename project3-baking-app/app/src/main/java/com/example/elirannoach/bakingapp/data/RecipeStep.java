package com.example.elirannoach.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeStep implements Parcelable {
    @SerializedName("id")
    int mStepId;
    @SerializedName("shortDescription")
    String mShortDescription;
    @SerializedName("description")
    String mDescription;
    @SerializedName("videoURL")
    String mVideoUrl;
    @SerializedName("thumbnailURL")
    String mThubnailUrl;

    public int getmStepId() {
        return mStepId;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public String getmThubnailUrl() {
        return mThubnailUrl;
    }

    protected RecipeStep(Parcel in) {
        mStepId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();

        mThubnailUrl = in.readString();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mStepId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
        dest.writeString(mThubnailUrl);
    }
}