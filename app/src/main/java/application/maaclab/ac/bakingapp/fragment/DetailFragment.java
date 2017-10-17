package application.maaclab.ac.bakingapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import application.maaclab.ac.bakingapp.helper.CallbackClick;

import static android.content.Context.MODE_PRIVATE;
import static application.maaclab.ac.bakingapp.activity.MainActivity.recipesPojo;

/**
 * Created by Wipro on 18-09-2017.
 */

public class DetailFragment extends Fragment implements CallbackClick {


    private RecyclerView stepsRecyclerView;
    private Context context;
    private StepsAdapter stepsAdapter;
    private int currentVisiblePositionSteps;
    private int position;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            position = getArguments().getInt("position");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        fragmentListener = (RecipeFragmentListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_layout, container, false);


        context = getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("last_opened_recipe", position);
        editor.apply();

        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_description);

        CallbackClick click = (CallbackClick) this;
        if(((LinearLayout) rootView.findViewById(R.id.linear)).getTag().equals("600")) {
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            stepsAdapter = new StepsAdapter(context, position, getActivity(), true, click);

            if (savedInstanceState == null) {
                Bundle bundle = new Bundle();
                bundle.putString("link", recipesPojo.get(position).getSteps().get(0).getVideoURL());
                bundle.putInt("position", position);
                bundle.putInt("position_adapter", 0);
                VideoFragment videoFragment = VideoFragment.newInstance(bundle, true);
                FragmentTransaction fragmentTransaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, videoFragment, VideoFragment.class.getSimpleName())
                        .addToBackStack(null).commit();
            }

        } else {
            stepsAdapter = new StepsAdapter(context, position, getActivity(), false, click);
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
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
        super.onPause();}

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void clickMethod(Bundle bundle) {

        if(bundle == null) {
            Bundle bundle1 = new Bundle();
            bundle1.putInt("position", position);
            IngredientFragment ingredientFragment = IngredientFragment.newInstance(bundle1);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, ingredientFragment, IngredientFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else {
            VideoFragment videoFragment = VideoFragment.newInstance(bundle, true);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, videoFragment, VideoFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}
