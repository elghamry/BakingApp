package app.elghamry.android.bakingapp.main_activity;

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

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * Created by ELGHAMRY on 04/05/2017.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {
    private JsonPlaceHolderApi api;
    ArrayList<Recipe> recipes;
    void initService()
    {
        recipes=new ArrayList<>();
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
                        getView().progressState(true);
                    }

                    @Override
                    public void onNext(List<Recipe> value) {
                        recipes.clear();
                        recipes.addAll(value);
                        Log.d(TAG, "onNext: "+value);
                    }


                    @Override
                    public void onError(Throwable e) {
                        getView().progressState(false);
                        getView().showError(e);

                    }

                    @Override
                    public void onComplete() {
                        getView().progressState(false);
                        getView().setData(recipes);

                    }
                }) ;
    }

}
