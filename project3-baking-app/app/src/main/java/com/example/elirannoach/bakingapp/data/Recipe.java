package com.example.elirannoach.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String nName;
    @SerializedName("ingredients")
    private List<Ingredient> mIngredientList;
    @SerializedName("steps")
    private List<RecipeStep> mRecipleStepList;
    @SerializedName("image")
    private String mImageSrc;
    @SerializedName("servings")
    private int mServingNumber;

    public Recipe(int mId, String nName, List<Ingredient> mIngredientList, List<RecipeStep> mRecipleStepList) {
        this.mId = mId;
        this.nName = nName;
        this.mIngredientList = mIngredientList;
        this.mRecipleStepList = mRecipleStepList;
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        nName = in.readString();
        mIngredientList = new ArrayList<>();
        mRecipleStepList = new ArrayList<>();
        in.readTypedList(mIngredientList,Ingredient.CREATOR);
        in.readTypedList(mRecipleStepList,RecipeStep.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public String getnName() {
        return nName;
    }

    public List<Ingredient> getmIngredientList() {
        return mIngredientList;
    }

    public List<RecipeStep> getmRecipleStepList() {
        return mRecipleStepList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(nName);
        dest.writeTypedList(mIngredientList);
        dest.writeTypedList(mRecipleStepList);
    }

    public String getmImageSrc() {
        return mImageSrc;
    }

    public int getmServingNumber() {
        return mServingNumber;
    }
}
