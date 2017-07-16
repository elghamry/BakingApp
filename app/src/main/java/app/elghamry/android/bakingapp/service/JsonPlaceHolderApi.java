package app.elghamry.android.bakingapp.service;

import java.util.List;

import app.elghamry.android.bakingapp.model.Recipe;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ELGHAMRY on 04/05/2017.
 */

public interface JsonPlaceHolderApi {
    @GET("baking.json")
    Observable<List<Recipe>> getRecipes();
}