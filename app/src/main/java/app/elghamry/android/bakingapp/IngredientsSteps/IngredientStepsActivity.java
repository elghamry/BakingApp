package app.elghamry.android.bakingapp.IngredientsSteps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import app.elghamry.android.bakingapp.IngredientsSteps.Fragments.IngredientStepsFragment;
import app.elghamry.android.bakingapp.IngredientsSteps.Fragments.IngredientsFragment;
import app.elghamry.android.bakingapp.IngredientsSteps.Fragments.StepsFragment;
import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.listeners.IngredientListener;
import app.elghamry.android.bakingapp.listeners.StepsListener;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public class IngredientStepsActivity extends MvpActivity<IngredientStepsView,IngredientStepsPresenter> implements IngredientStepsView,IngredientListener,StepsListener{
    @NonNull
    @Override
    public IngredientStepsPresenter createPresenter() {
        IngredientStepsPresenter presenter = new IngredientStepsPresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_steps_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            IngredientStepsFragment fragment = IngredientStepsFragment.newInstance();
            fragment.setListner(this);
            fragment.setListener2(this);
            fragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.IngStpFragment, fragment)
                    .commit();

        }
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void callIngredientFragment() {
        Log.d("TAG", "IngredientClicked1: ");
        clearRightPane();
        Bundle extras = getIntent().getExtras();
        IngredientsFragment fragment2 = IngredientsFragment.newInstance();
        fragment2.setArguments(extras);
getSupportFragmentManager().beginTransaction().add(R.id.step_fragment,fragment2).commit();
    }
    @Override
    public void callStepsFragment(int position) {
        Log.d("TAG", "IngredientClicked1: ");
        clearRightPane();
        Bundle extras = getIntent().getExtras();
        StepsFragment fragment2 = StepsFragment.newInstance();
        fragment2.setArguments(extras);
        fragment2.getArguments().putInt("position",position);
        getSupportFragmentManager().beginTransaction().add(R.id.step_fragment,fragment2).commit();
    }



    public void clearRightPane() {
        if (getSupportFragmentManager().findFragmentById(R.id.step_fragment) != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.step_fragment)).commit();
        }
    }
}


