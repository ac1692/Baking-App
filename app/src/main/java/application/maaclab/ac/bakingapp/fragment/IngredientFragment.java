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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.activity.MainActivity;
import application.maaclab.ac.bakingapp.adapter.IngredientsAdapter;

/**
 * Created by Wipro on 06-10-2017.
 */

public class IngredientFragment extends Fragment {

    private RecyclerView ingredientsRecyclerView;
    private int position;
    private int currentVisiblePositionIngredients;
    private Context context;
    private IngredientsAdapter ingredientsAdapter;


    public static IngredientFragment newInstance(Bundle bundle) {
        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setArguments(bundle);

        return ingredientFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_ingredients);
        ingredientsAdapter = new IngredientsAdapter(context, position);

        FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.frame);

        if(frameLayout.getTag().equals("600")) {
            ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", (ArrayList) MainActivity.recipesPojo);
        outState.putInt("position", currentVisiblePositionIngredients);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            MainActivity.recipesPojo = savedInstanceState.getParcelableArrayList("list");
            currentVisiblePositionIngredients = savedInstanceState.getInt("position");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVisiblePositionIngredients = ((LinearLayoutManager)ingredientsRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        ingredientsRecyclerView.getLayoutManager().scrollToPosition((int)currentVisiblePositionIngredients);
        currentVisiblePositionIngredients = 0;
    }
    }
