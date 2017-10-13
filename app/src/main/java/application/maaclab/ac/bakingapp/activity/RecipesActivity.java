package application.maaclab.ac.bakingapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.fragment.DetailFragment;
import application.maaclab.ac.bakingapp.fragment.VideoFragment;
import application.maaclab.ac.bakingapp.helper.SimpleIdlingResource;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;
import static application.maaclab.ac.bakingapp.fragment.VideoFragment.newInstance;
import static application.maaclab.ac.bakingapp.fragment.VideoFragment.videoFragment;

public class RecipesActivity extends AppCompatActivity {

    DetailFragment detailFragment;

    private Runnable runnableDetailFragmentNavigation = new Runnable() {
        @Override
        public void run() {
            detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            detailFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.recipes_container, detailFragment, DetailFragment.class.getSimpleName()).commit();
        }
    };
    private int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("position");
            getSupportActionBar().setTitle(recipesPojo.get(position).getName());
            if(savedInstanceState == null)
                runnableDetailFragmentNavigation.run();
        } else{
            Toast.makeText(this, "Try Again!!", Toast.LENGTH_SHORT).show();
        }
    }




}
