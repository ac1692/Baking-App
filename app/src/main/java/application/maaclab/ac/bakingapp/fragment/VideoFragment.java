package application.maaclab.ac.bakingapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.MainActivity;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;


/**
 * Created by Wipro on 20-09-2017.
 */

public class VideoFragment extends Fragment {

    private Button previous, next;
    private TextView text;
    private Context context;
    private String vidAddress;
//    private ProgressBar progressBar;
    private VideoView videoview;
    private int adapter_position;
    private int step_position;
    private int size;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_layout, container, false);

        context = getActivity().getApplicationContext();

        text = (TextView) rootView.findViewById(R.id.text);
        previous = (Button) rootView.findViewById(R.id.previous);
        next = (Button) rootView.findViewById(R.id.next);

        size = recipesPojo.get(step_position).getSteps().size();
        if(getArguments() != null) {
            vidAddress = getArguments().getString("link");
            adapter_position = getArguments().getInt("position_adapter");
            step_position = getArguments().getInt("position");

        } else {
            Toast.makeText(context, "Error Loading\nTry Again!!", Toast.LENGTH_SHORT).show();
        }

        if((adapter_position + 1) >= size )
            next.setEnabled(false);
        if(adapter_position == 0)
            previous.setEnabled(false);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoFragment videoFragment = new VideoFragment();
                Bundle  bundle = new Bundle();
                bundle.putString("link", recipesPojo.get(step_position).getSteps().get(adapter_position+1).getVideoURL());
                bundle.putInt("position", step_position);
                bundle.putInt("position_adapter", adapter_position+1);
                videoFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.recipes_container, videoFragment, VideoFragment.class.getSimpleName())
                        .commitAllowingStateLoss();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoFragment videoFragment = new VideoFragment();
                Bundle  bundle = new Bundle();
                bundle.putString("link", recipesPojo.get(step_position).getSteps().get(adapter_position-1).getVideoURL());
                bundle.putInt("position", step_position);
                bundle.putInt("position_adapter", adapter_position+1);
                videoFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.recipes_container, videoFragment, VideoFragment.class.getSimpleName())
                        .commitAllowingStateLoss();
            }
        });

        videoview = (VideoView) rootView.findViewById(R.id.VideoView);
//        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//        progressBar.setProgress(0);
//        progressBar.setVisibility(ProgressBar.VISIBLE);



        text.setText(MainActivity.recipesPojo.get(step_position).getSteps().get(adapter_position).getDescription());
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(context);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(vidAddress);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
//                progressBar.setVisibility(ProgressBar.GONE);
                videoview.start();
            }
        });

        return rootView;
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
}

