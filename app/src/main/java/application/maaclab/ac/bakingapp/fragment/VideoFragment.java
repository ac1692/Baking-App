package application.maaclab.ac.bakingapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.MainActivity;
import application.maaclab.ac.bakingapp.helper.DisplayMetricUtils;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;


/**
 * Created by Wipro on 20-09-2017.
 */

public class VideoFragment extends Fragment implements ExoPlayer.EventListener {

    private Button previous, next;
    private TextView text;
    private Context context;
    private String vidAddress;
    private static ProgressBar progressBar;
    //    private VideoView videoview;
    private int adapter_position;
    private int step_position;
    private int size;
    private SimpleExoPlayerView videoPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private long currentVideoPosition;
    private SimpleExoPlayer mExoPlayer;
    public static VideoFragment videoFragment;
    private static boolean tablet_bool;

    public static VideoFragment newInstance(Bundle bundle, boolean tablet) {
        videoFragment = new VideoFragment();
        tablet_bool = tablet;
        videoFragment.setArguments(bundle);

        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_layout, container, false);

        context = getActivity().getApplicationContext();
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relative);
        if(getArguments() != null) {
            vidAddress = getArguments().getString("link");
            adapter_position = getArguments().getInt("position_adapter");
            step_position = getArguments().getInt("position");
            size = recipesPojo.get(step_position).getSteps().size();

        } else {
            Toast.makeText(context, "Error Loading\nTry Again!!", Toast.LENGTH_SHORT).show();
        }

        text = (TextView) rootView.findViewById(R.id.text);
        previous = (Button) rootView.findViewById(R.id.previous);
        next = (Button) rootView.findViewById(R.id.next);


        text.setVisibility(TextView.GONE);
        next.setVisibility(Button.GONE);
        previous.setVisibility(Button.GONE);
        if(!getResources().getBoolean(R.bool.is_landscape) || tablet_bool) {
                relativeLayout.setVisibility(RelativeLayout.VISIBLE);

                text.setVisibility(TextView.VISIBLE);
                next.setVisibility(Button.VISIBLE);
                previous.setVisibility(Button.VISIBLE);

            if(tablet_bool) {
                next.setVisibility(Button.GONE);
                previous.setVisibility(Button.GONE);
            }

                if ((adapter_position + 1) >= size)
                    next.setEnabled(false);
                else
                    next.setEnabled(true);

                if (adapter_position == 0)
                    previous.setEnabled(false);
                else
                    previous.setEnabled(true);


                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("link", recipesPojo.get(step_position).getSteps().get(adapter_position + 1).getVideoURL());
                        bundle.putInt("position", step_position);
                        bundle.putInt("position_adapter", adapter_position + 1);
                        VideoFragment videoFragment = VideoFragment.newInstance(bundle, tablet_bool);

//                videoFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.recipes_container, videoFragment, VideoFragment.class.getSimpleName())
                                .commitAllowingStateLoss();
                    }
                });

                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("link", recipesPojo.get(step_position).getSteps().get(adapter_position - 1).getVideoURL());
                        bundle.putInt("position", step_position);
                        bundle.putInt("position_adapter", adapter_position - 1);
                        VideoFragment videoFragment = VideoFragment.newInstance(bundle, tablet_bool);

                        FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.recipes_container, videoFragment, VideoFragment.class.getSimpleName())
                                .commitAllowingStateLoss();
                    }
                });

                text.setText(MainActivity.recipesPojo.get(step_position).getSteps().get(adapter_position).getDescription());
        }

        videoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_player_view);
        videoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

/*        if((RelativeLayout) rootView.findViewWithTag("landscape")!=null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
            System.out.println("landscape");
            videoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }*/
        initializePlayer();
        progressBar.setProgress(0);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                //perhaps use intent if needed but i'm sure there's a specific intent action for up you can use to handle
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVideoPosition = mExoPlayer.getCurrentPosition();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    private void initializePlayer() {
        initializeMediaSession();
        initializeVideoPlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("link", vidAddress );
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
            vidAddress = savedInstanceState.getString("link");
    }

    private void initializeMediaSession() {
        if (mMediaSession == null || mStateBuilder == null) {
            mMediaSession = new MediaSessionCompat(getActivity(), "Recipe");
            mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
            mMediaSession.setMediaButtonReceiver(null);

            mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                    PlaybackStateCompat.ACTION_PLAY |
                            PlaybackStateCompat.ACTION_PAUSE |
                            PlaybackStateCompat.ACTION_PLAY_PAUSE);

            mMediaSession.setPlaybackState(mStateBuilder.build());

            mMediaSession.setCallback(new MediaSessionCompat.Callback() {
                @Override
                public void onPlay() {
                    mExoPlayer.setPlayWhenReady(true);
                }

                @Override
                public void onPause() {
                    mExoPlayer.setPlayWhenReady(false);
                }

                @Override
                public void onSkipToPrevious() {
                    mExoPlayer.seekTo(0);
                }
            });

            mMediaSession.setActive(true);
        }
    }

    private void initializeVideoPlayer() {
        if (mExoPlayer == null && vidAddress != null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayer.seekTo(currentVideoPosition);
            videoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "Recipe");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(vidAddress), new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
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
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
            progressBar.setVisibility(View.GONE);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
            progressBar.setVisibility(View.GONE);
        } else if (playbackState == ExoPlayer.STATE_BUFFERING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
        if (mStateBuilder != null) {
            mMediaSession.setPlaybackState(mStateBuilder.build());
        }
    }

    @Override
    public void onRepeatModeChanged(int i) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
//        showError(getString(R.string.label_no_internet));
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }


}

