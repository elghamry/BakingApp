package app.elghamry.android.bakingapp.widget;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.ArrayList;

import app.elghamry.android.bakingapp.model.Recipe;

/**
 * Created by ELGHAMRY on 13/05/2017.
 */

public interface RwcActivityView extends MvpView{
    void setProgressState(boolean state);


    void setUpRecyclerView(ArrayList<Recipe> recipes);

    void showError(Throwable e);
}
