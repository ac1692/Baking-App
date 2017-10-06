package application.maaclab.ac.bakingapp.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.fragment.DetailFragment;

import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;

public class RecipesActivity extends AppCompatActivity {

    private Runnable runnableDetailFragmentNavigation = new Runnable() {
        @Override
        public void run() {
            DetailFragment detailFragment = new DetailFragment();
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

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("position");
            getSupportActionBar().setTitle(recipesPojo.get(position).getName());
            runnableDetailFragmentNavigation.run();
        } else{
            finish();
        }


    }
}
