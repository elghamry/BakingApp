package app.elghamry.android.bakingapp.IngredientsSteps.Fragments;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public class IngredientStepsFragmentPresenter extends MvpBasePresenter<IngredientStepsFragmentView> {

    public void init() {
        if(isViewAttached())
        {
            getView().setData();
            Log.d(TAG, "initt: 123+");
        }
    }
}
