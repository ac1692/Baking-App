package application.maaclab.ac.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.MainActivity;
import application.maaclab.ac.bakingapp.adapter.IngredientsAdapter;
import application.maaclab.ac.bakingapp.adapter.StepsAdapter;

/**
 * Created by Wipro on 18-09-2017.
 */

public class DetailFragment extends Fragment {

    private RecyclerView ingredientsRecyclerView, stepsRecyclerView;
    private Context context;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private int currentVisiblePositionIngredients;
    private int currentVisiblePositionSteps;
    private int position;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_layout, container, false);


        context = getActivity().getApplicationContext();
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_ingredients);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_description);

        ingredientsAdapter = new IngredientsAdapter(context, position);
        stepsAdapter = new StepsAdapter(context, position, getActivity());
        if(((RelativeLayout) rootView.findViewById(R.id.linear)).getTag().equals("600")) {
            ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            stepsRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        stepsRecyclerView.setAdapter(stepsAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", (ArrayList) MainActivity.recipesPojo);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            MainActivity.recipesPojo = savedInstanceState.getParcelableArrayList("list");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
            currentVisiblePositionIngredients = ((LinearLayoutManager)ingredientsRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            currentVisiblePositionSteps = ((LinearLayoutManager)stepsRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        ingredientsRecyclerView.getLayoutManager().scrollToPosition((int)currentVisiblePositionIngredients);
        stepsRecyclerView.getLayoutManager().scrollToPosition((int)currentVisiblePositionSteps);
        currentVisiblePositionSteps = 0;
        currentVisiblePositionIngredients = 0;
    }
}
