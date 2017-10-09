package application.maaclab.ac.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.MainActivity;
import application.maaclab.ac.bakingapp.adapter.BakingAdapter;
import application.maaclab.ac.bakingapp.helper.RecipesPojoDownloader;
import application.maaclab.ac.bakingapp.helper.SimpleIdlingResource;
import application.maaclab.ac.bakingapp.model.RecipesPojo;

/**
 * Created by Wipro on 18-09-2017.
 */

public class BakingFragment extends Fragment {

    private BakingAdapter bakingAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private long currentVisiblePosition = 0;
    private int orientation = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.fragment_baking_layout, container, false);

        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) rooView.findViewById(R.id.recycler);

        bakingAdapter = new BakingAdapter(context, getActivity(), false, null);
        if(((RelativeLayout) rooView.findViewById(R.id.relative)).getTag().equals("600")) {
            orientation = 1;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            orientation = 0;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        recyclerView.setAdapter(bakingAdapter);

        return rooView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("orientation", orientation);
        outState.putParcelableArrayList("list", (ArrayList) MainActivity.recipesPojo);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            orientation = savedInstanceState.getInt("orientation");
            MainActivity.recipesPojo = savedInstanceState.getParcelableArrayList("list");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(orientation == 0)
            currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        else if(orientation == 1)
            currentVisiblePosition = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.getLayoutManager().scrollToPosition((int)currentVisiblePosition);
        currentVisiblePosition = 0;
    }

}
