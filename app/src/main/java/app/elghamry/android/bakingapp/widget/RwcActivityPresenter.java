package app.elghamry.android.bakingapp.widget;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

import app.elghamry.android.bakingapp.model.Recipe;
import app.elghamry.android.bakingapp.service.JsonPlaceHolderApi;
import app.elghamry.android.bakingapp.service.RetrofitProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by ELGHAMRY on 13/05/2017.
 */

public class RwcActivityPresenter extends MvpBasePresenter <RwcActivityView> {
    private JsonPlaceHolderApi api;
    ArrayList<Recipe> recipes = new ArrayList<>();

    public void fetchRecipes() {
        api = RetrofitProvider.get().create(JsonPlaceHolderApi.class);
        api.getRecipes()
                .subscribeOn(Schedulers.io())       //setting up worker thread
                .observeOn(AndroidSchedulers.mainThread())               //setting up thread where result will be delivered
//                .doOnSubscribe( progress.show())                    //show progress bar on subscribe
// rX2
//                .doOnCompleted( progress.dismiss())                 //dismiss progress bar on complete
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                       getView().setProgressState(true);
                    }

                    @Override
                    public void onNext(List<Recipe> value) {
                        recipes.clear();
                        recipes.addAll(value);
                        Log.d(TAG, "onNext: "+value);
                    }


                    @Override
                    public void onError(Throwable e) {
                        getView().setProgressState(false);
                        getView().showError(e);
                    }

                    @Override
                    public void onComplete() {
                        getView().setProgressState(false);
                        getView().setUpRecyclerView(recipes);

                    }
                }) ;
    }
}
