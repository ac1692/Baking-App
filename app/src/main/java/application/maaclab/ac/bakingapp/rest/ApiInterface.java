package application.maaclab.ac.bakingapp.rest;

import java.util.List;

import application.maaclab.ac.bakingapp.model.RecipesPojo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Wipro on 22/08/17.
 */
public interface ApiInterface {
    @GET("baking.json")
    Call<List<RecipesPojo>> getData();
}

