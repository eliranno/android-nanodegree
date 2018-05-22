package com.example.elirannoach.project2_popular_movies_app;

import java.util.Map;

public class Movie {

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

    public Movie(int id, int voteCount, boolean isVideo,double averageVote,String title,
                 double popularity,String posterPath,String originalLanguage,String originalTitle,
                 Map<Integer,Integer> genereIdMap,String mBackdropPath,boolean isAdult,String overview,
                 String releaseDate){
        mId = id;
        mVoteCount = voteCount;
        mIsAdult = isVideo;
        mAverageVote =averageVote;
        mTitle = title;
        mPopularity = popularity;
        mPosterPath = posterPath;
        mOriginalLang = originalLanguage;
        mGenreIdMap = genereIdMap;
        mBackdropPath = mBackdropPath;
        mIsAdult = isAdult;
        mOverview = overview;
        mReleaseDate = releaseDate;
    }

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

        public MovieBuilder(){

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

        public Movie build(){
            return new Movie(mId,mVoteCount,mIsVideo,mAverageVote,mTitle,mPopularity,
                    mPosterPath,mOriginalLang,mOriginalTitle,mGenreIdMap,mBackdropPath,mIsAdult,
                    mOverview,mReleaseDate);
        }


    }

}
