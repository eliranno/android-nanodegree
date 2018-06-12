package com.example.elirannoach.project2_popular_movies_app.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Movie implements Parcelable {

    private int mId;
    private int mVoteCount;
    private boolean mIsVideo;
    private double mAverageVote;
    private String mTitle;
    private double mPopularity;
    private String mPosterPath;
    private String mOriginalLang;
    private String mOriginalTitle;
    private Map<Integer,Integer> mGenreIdMap;
    private String mBackdropPath;
    private boolean mIsAdult;
    private String mOverview;
    private String mReleaseDate;
    private List<MovieTrailerLink> mTrailerList;

    public Movie(int id, int voteCount, boolean isVideo,double averageVote,String title,
                 double popularity,String posterPath,String originalLanguage,String originalTitle,
                 Map<Integer,Integer> genereIdMap,String backdropPath,boolean isAdult,String overview,
                 String releaseDate,List<MovieTrailerLink> trailerList){
        mId = id;
        mVoteCount = voteCount;
        mIsAdult = isVideo;
        mAverageVote =averageVote;
        mTitle = title;
        mPopularity = popularity;
        mPosterPath = posterPath;
        mOriginalLang = originalLanguage;
        mGenreIdMap = genereIdMap;
        mBackdropPath = backdropPath;
        mIsAdult = isAdult;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mTrailerList = trailerList;
    }

    public Movie(Parcel in){
        List<String> stringList = new ArrayList();
        in.readStringList(stringList);
        mTitle = stringList.get(0);
        mReleaseDate = stringList.get(1);
        mOverview = stringList.get(2);
        mPosterPath = stringList.get(3);
        mAverageVote = in.readDouble();
        mId = in.readInt();
    }

    public String getTitle(){
        return this.mTitle;
    }

    public String getImageRelativePath(){
        return mPosterPath;
    }

    public String getReleaseDate() { return mReleaseDate; }

    public double getAverageVote() {return mAverageVote;}

    public String getOverview() {return mOverview;}

    public int getMovieId() {return mId;}

    // TODO : finish this method
    public MovieBuilder buildUpon(){
        return null;
    }

    //Note that order of variable writing and reading is important, you should read and write variable in same order.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(new ArrayList<String>(Arrays.asList(mTitle,mReleaseDate,mOverview,mPosterPath)));
        dest.writeDouble(mAverageVote);
        dest.writeInt(mId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static class MovieBuilder{
        private int mId = -1;
        private int mVoteCount = 0;
        private boolean mIsVideo = false;
        private double mAverageVote = 0.0;
        private String mTitle = "";
        private double mPopularity = 0.0;
        private String mPosterPath = "";
        private String mOriginalLang = "";
        private String mOriginalTitle = "";
        private Map<Integer,Integer> mGenreIdMap = null;
        private String mBackdropPath = "";
        private boolean mIsAdult = false;
        private String mOverview = "";
        private String mReleaseDate = "";
        private List<MovieTrailerLink> mTrailersList = new ArrayList<>();


        private MovieBuilder(int mId, int mVoteCount, boolean mIsVideo, double mAverageVote, String mTitle, double mPopularity, String mPosterPath, String mOriginalLang, String mOriginalTitle, Map<Integer, Integer> mGenreIdMap, String mBackdropPath, boolean mIsAdult, String mOverview, String mReleaseDate, List<MovieTrailerLink> mTrailersList) {
            this.mId = mId;
            this.mVoteCount = mVoteCount;
            this.mIsVideo = mIsVideo;
            this.mAverageVote = mAverageVote;
            this.mTitle = mTitle;
            this.mPopularity = mPopularity;
            this.mPosterPath = mPosterPath;
            this.mOriginalLang = mOriginalLang;
            this.mOriginalTitle = mOriginalTitle;
            this.mGenreIdMap = mGenreIdMap;
            this.mBackdropPath = mBackdropPath;
            this.mIsAdult = mIsAdult;
            this.mOverview = mOverview;
            this.mReleaseDate = mReleaseDate;
            this.mTrailersList = mTrailersList;
        }

        public MovieBuilder (){

        }

        public MovieBuilder setMovieId(int id){
            mId = id;
            return this;
        }

        public MovieBuilder setVoteCount(int count){
            mVoteCount = count;
            return this;
        }

        public MovieBuilder setIsVideoAvailable(boolean isVideoAvailable){
            mIsVideo = isVideoAvailable;
            return this;
        }

        public MovieBuilder setAverageVote(double avg){
            mAverageVote = avg;
            return this;
        }

        public MovieBuilder setTitle(String title){
            mTitle = title;
            return this;
        }

        public MovieBuilder setPopularity(double popularity){
            mPopularity =popularity;
            return this;
        }

        public MovieBuilder setPosterPath(String path){
            mPosterPath = path;
            return this;
        }

        public MovieBuilder setOriginalLanguage(String originalLanguage){
            mOriginalLang = originalLanguage;
            return this;
        }

        public MovieBuilder setOriginalTitle(String originalTitle){
            mOriginalTitle = originalTitle;
            return this;
        }

        public MovieBuilder setGenreMap(Map<Integer,Integer> map){
            mGenreIdMap = map;
            return this;
        }

        public MovieBuilder setBackDropPath(String path){
            mBackdropPath = path;
            return this;
        }

        public MovieBuilder setIsAdultMovie(boolean isAdultMovie){
            mIsAdult = isAdultMovie;
            return this;
        }

        public MovieBuilder setOverview(String overview){
            mOverview = overview;
            return this;
        }

        public MovieBuilder setReleaseDate(String releaseDate){
            mReleaseDate = releaseDate;
            return this;
        }

        public MovieBuilder setTrailerList(List<MovieTrailerLink> list){
            mTrailersList = list;
            return this;
        }

        public MovieBuilder addLinkToTrailerList(MovieTrailerLink link){
            mTrailersList.add(link);
            return this;
        }

        public Movie build(){
            return new Movie(mId,mVoteCount,mIsVideo,mAverageVote,mTitle,mPopularity,
                    mPosterPath,mOriginalLang,mOriginalTitle,mGenreIdMap,mBackdropPath,mIsAdult,
                    mOverview,mReleaseDate,mTrailersList);
        }


    }

}
