package com.example.elirannoach.bakingapp.ui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elirannoach.bakingapp.BakingAppWidget;
import com.example.elirannoach.bakingapp.R;
import com.example.elirannoach.bakingapp.data.Ingredient;
import com.example.elirannoach.bakingapp.data.Recipe;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeInstructionFragment extends Fragment {
    private SimpleExoPlayerView mPlayerView;
    private Recipe mRecipe;
    private int mStepNumber;
    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.ingredient_list_id)
    TextView mRecipeIngredientListTextView;
    @BindView(R.id.recipe_step_text_id)
    TextView mRecipeStepTextView;
    private long mPlaybackPosition;
    private int mCurrentWindow;
    private boolean mIsPlayWhenReady;

    public static final String AUTOPLAY = "autoplay";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String PLAYBACK_POSITION = "playback_position";
    public static final String RECIPE = "recipe";
    public static final String STEP = "step";
    public static final String PLAY_STATE = "playstate";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_instruction_fragment, container, false);
        ButterKnife.bind(this,rootView);
        mIsPlayWhenReady = true;
        if(savedInstanceState!=null) {
            mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, C.TIME_UNSET);
            mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
            mRecipe = savedInstanceState.getParcelable(RECIPE);
            mStepNumber = savedInstanceState.getInt(STEP,0);
            mIsPlayWhenReady = savedInstanceState.getBoolean(PLAY_STATE,true);
        }
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
        mRecipeIngredientListTextView.setText(getIngredientListString());
        mRecipeStepTextView.setText(mRecipe.getmRecipleStepList().get(mStepNumber).getmDescription());
        BakingAppWidget.updateRecipeWidget(getContext(),mRecipe);
        return rootView;
    }

    private String getIngredientListString() {
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : mRecipe.getmIngredientList()){
            builder.append(ingredient);
        }
        return builder.toString();
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer(Uri.parse(mRecipe.getmRecipleStepList().get(mStepNumber).getmVideoUrl()));
    }

    public void setRecipe(final Recipe recipe, int step) {
        mRecipe = recipe;
        mStepNumber = step;
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String thubnailUrl = mRecipe.getmRecipleStepList().get(mStepNumber).getmThubnailUrl();
            if(!thubnailUrl.isEmpty() && !thubnailUrl.endsWith(".mp4")) {
                Picasso.with(getContext()).load(thubnailUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mPlayerView.setDefaultArtwork(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingRecipe");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mPlaybackPosition);
            mExoPlayer.setPlayWhenReady(mIsPlayWhenReady);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!=null)
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer==null)
            initializePlayer(Uri.parse(mRecipe.getmRecipleStepList().get(mStepNumber).getmVideoUrl()));
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            //mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mExoPlayer != null) {
            outState.putParcelable(RECIPE,mRecipe);
            outState.putLong(PLAYBACK_POSITION, mExoPlayer.getCurrentPosition());
            outState.putInt(CURRENT_WINDOW_INDEX, mExoPlayer.getCurrentWindowIndex());
            outState.putInt(STEP,mStepNumber);
            outState.putBoolean(PLAY_STATE, mExoPlayer.getPlayWhenReady());
        }
    }
}
