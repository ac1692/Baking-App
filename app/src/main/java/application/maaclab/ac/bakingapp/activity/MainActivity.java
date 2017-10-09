package application.maaclab.ac.bakingapp.activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.fragment.BakingFragment;
import application.maaclab.ac.bakingapp.helper.SimpleIdlingResource;
import application.maaclab.ac.bakingapp.model.RecipesPojo;
import application.maaclab.ac.bakingapp.rest.ApiClient;
import application.maaclab.ac.bakingapp.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static application.maaclab.ac.bakingapp.widget.BakingAppWidget.bakingAppWidget;

public class MainActivity extends AppCompatActivity {

    private AtomicBoolean runningTest;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Nullable
    @VisibleForTesting
    public SimpleIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private ProgressBar progressBar;
    public static List<RecipesPojo> recipesPojo;
    private Runnable runnableNavigationBakingFragment = new Runnable() {
        @Override
        public void run() {
            BakingFragment bakingFragment = new BakingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, bakingFragment, BakingFragment.class.getSimpleName()).commitAllowingStateLoss();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.baking);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setProgress(0);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<RecipesPojo>> getData = apiService.getData();
        getData.enqueue(new Callback<List<RecipesPojo>>() {
            @Override
            public void onResponse(Call<List<RecipesPojo>> call, Response<List<RecipesPojo>> response) {
                recipesPojo = response.body();
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                runnableNavigationBakingFragment.run();
            }

            @Override
            public void onFailure(Call<List<RecipesPojo>> call, Throwable t) {
               Toast.makeText(getApplicationContext(), R.string.try_again,Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
    }

}
