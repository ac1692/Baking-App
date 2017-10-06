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
/*

    @GET(".")
    Call<BaseClassSample> getPackageNameToInstall();

    @POST("/oimio/ijum/ ")
    Call<BaseClassSample> postRatedMovies();

    @POST("/oimio/ijum/ ")
    Call<BaseClassSample> postPackageName();

    @FormUrlEncoded
    @POST("/v1/user/auth")
    Call<PostEmailId> postEmailID(@Field("email") String email);

    @FormUrlEncoded
    @POST("/v1/user/app/register")
    Call<PostPackage> postPackage(@Field("userid") long userid,
                                  @Field("package") String package_name);
*/

}

