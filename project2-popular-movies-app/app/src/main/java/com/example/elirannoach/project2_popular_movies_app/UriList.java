package com.example.elirannoach.project2_popular_movies_app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class UriList extends ArrayList<Uri> implements Parcelable {
    protected UriList(Parcel in) {
        List<String> uriStringList = new ArrayList<String>();
        in.readStringList(uriStringList);
        for (String uriString : uriStringList){
            this.add(Uri.parse(uriString));
        }
    }

    public UriList(ArrayList<Uri> list){
        super(list);
    }

    public UriList(){
        super();
    }


    public static final Creator<UriList> CREATOR = new Creator<UriList>() {
        @Override
        public UriList createFromParcel(Parcel in) {
            return new UriList(in);
        }

        @Override
        public UriList[] newArray(int size) {
            return new UriList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        for(Uri uri : this){
            dest.writeString(uri.toString());
        }
    }

    public List<URL> convertToUrlList() throws MalformedURLException {
        List<URL> urlList = new ArrayList<URL>();
        for (Uri uri : this)
            try {
                urlList.add(new URL(uri.toString()));
            } catch (MalformedURLException e) {
                throw e;
            }
            return urlList;
    }
}
