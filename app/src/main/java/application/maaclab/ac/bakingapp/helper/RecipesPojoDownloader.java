package application.maaclab.ac.bakingapp.helper;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import application.maaclab.ac.bakingapp.R;
import application.maaclab.ac.bakingapp.model.Ingredient;
import application.maaclab.ac.bakingapp.model.RecipesPojo;
import application.maaclab.ac.bakingapp.model.Step;

/**
 * Created by Wipro on 08-10-2017.
 */

public class RecipesPojoDownloader {

  /*  private static final int DELAY_MILLIS = 3000;

    // Create an ArrayList of mTeas
    final static ArrayList<RecipesPojo> recipesPojo = new ArrayList<>();

    public interface DelayerCallback{
        void onDone(ArrayList<RecipesPojo> recipesPojo);
    }

    public static void downloadRecipesPojo(Context context, final DelayerCallback callback,
                              @Nullable final SimpleIdlingResource idlingResource) {

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        Ingredient ingredient = new Ingredient(1, "abc","abc");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(0,ingredient);

        Step step = new Step(1, "abc","abc","abc","abc");
        List<Step> steps= new ArrayList<>();
        steps.add(0,step);

        RecipesPojo  pojo = new RecipesPojo(1, "ankit", ingredients, steps, 1, "R.drawable.recipe_image");
        recipesPojo.add(0, pojo);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(recipesPojo);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }*/
}
