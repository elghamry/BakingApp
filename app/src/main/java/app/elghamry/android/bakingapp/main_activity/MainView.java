package app.elghamry.android.bakingapp.main_activity;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.ArrayList;

import app.elghamry.android.bakingapp.model.Recipe;

/**
 * Created by ELGHAMRY on 04/05/2017.
 */

public interface MainView extends MvpView {
    void setData(ArrayList<Recipe> recipes);
    void progressState(boolean a);

    void showError(Throwable e);
}
