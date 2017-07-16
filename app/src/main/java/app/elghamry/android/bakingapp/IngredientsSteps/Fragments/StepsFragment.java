package app.elghamry.android.bakingapp.IngredientsSteps.Fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static app.elghamry.android.bakingapp.R.id.playerView;
import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public  class StepsFragment extends Fragment  implements ExoPlayer.EventListener{
    private static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private SimpleExoPlayer exoPlayer;
    int index;
    private static long position = 0;
@BindView(R.id.description)
    TextView tvDescription;
    @BindView(R.id.prev)
    Button btPrev;
    @BindView(R.id.next)
    Button btNext;
    @BindView(playerView)
    SimpleExoPlayerView exPlayerView;
    @BindView(R.id.iv_step_image)
    ImageView ivStep;
    List<Step> steps;
    boolean hasVid = false;
    boolean exoInit=false;
    public static StepsFragment newInstance() {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initFields();
        doInLast();
        return rootView;
    }

    public void doInLast()
    {
        if(!(steps.get(index).getVideoURL()).equals(""))
        {
        initializeMediaSession();
            initializePlayer(Uri.parse(steps.get(index).getVideoURL()));
            hasVid=true;
            exPlayerView.setVisibility(View.VISIBLE);
        }
        else
        {
           hasVid=false;
            exPlayerView.setVisibility(View.GONE);
        }
        int lastDot = steps.get(index).getThumbnailURL().lastIndexOf('.');
        String extension = (steps.get(index).getThumbnailURL()).substring(lastDot+1);
        Log.d(TAG, "doInLast: "+extension);
        if(!(steps.get(index).getThumbnailURL()).equals("")) {
            if (steps.get(index).getThumbnailURL().matches("(.*).mp4")) {
                if(!hasVid) {
                    initializeMediaSession();
                    initializePlayer(Uri.parse(steps.get(index).getThumbnailURL()));
                    ivStep.setVisibility(View.GONE);
                    exPlayerView.setVisibility(View.VISIBLE);
                }
                else
                {
                    exPlayerView.setVisibility(View.GONE);
                }
            } else {

                Picasso.with(ivStep.getContext()).load(steps.get(index).getThumbnailURL()).into(ivStep);
            }
        }
        else {
            ivStep.setVisibility(View.GONE);
        }
        getActivity().setTitle(steps.get(index).getShortDescription());
        tvDescription.setText(steps.get(index).getDescription());
        if(index==0){
            btPrev.setVisibility(View.INVISIBLE);
        }
        else if(index==steps.size()-1){
            btNext.setVisibility(View.INVISIBLE);
        }
        else
        {
            btNext.setVisibility(View.VISIBLE);
            btPrev.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.prev)
   void onPrevClicked(){
        index--;
        if(exoInit){
        restExoPlayer(0, false);}
        doInLast();
    }

    @OnClick(R.id.next)
    void onNextClicked(){
        index++;
        if(exoInit){
        restExoPlayer(0, false);}
        doInLast();
    }

    private void initializePlayer(Uri parse) {
//        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
           exPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "StepsFragment");
            MediaSource mediaSource = new ExtractorMediaSource(parse, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            restExoPlayer(position, false);
        exoInit=true;
//        }
    }

    private void restExoPlayer(long position, boolean playWhenReady) {
        this.position = position;
        exoPlayer.seekTo(position);
        exoPlayer.setPlayWhenReady(playWhenReady);
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MySessionCallback());
        mediaSession.setActive(true);
    }


    private void initFields() {
        steps = (ArrayList<Step>)getActivity().getIntent().getSerializableExtra("Steps");
        index=getArguments().getInt("position");


    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
//        exoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(exoInit){
        exoPlayer.setPlayWhenReady(false);
        mediaSession.setActive(false);}
    }

    @Override
    public void onResume() {
        super.onResume();
        if(exoInit){
        mediaSession.setActive(true);}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(exoInit){
        releasePlayer();
        mediaSession.setActive(false);}
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == PlaybackStateCompat.STATE_PLAYING) {
            position = exoPlayer.getCurrentPosition();
        }
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            restExoPlayer(0, false);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
            exPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvDescription.setVisibility(View.GONE);
            btPrev.setVisibility(View.GONE);
            btNext.setVisibility(View.GONE);

        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            showSystemUI();
            exPlayerView.getLayoutParams().height = 250;
            tvDescription.setVisibility(View.VISIBLE);
            btPrev.setVisibility(View.INVISIBLE);
            btNext.setVisibility(View.INVISIBLE);
            if(index!=0)
            btPrev.setVisibility(View.VISIBLE);
            if (index!=steps.size()-1)
            btNext.setVisibility(View.VISIBLE);
        }
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    private void showSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        );
    }


}
