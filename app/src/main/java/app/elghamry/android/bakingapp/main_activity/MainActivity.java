package app.elghamry.android.bakingapp.main_activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import app.elghamry.android.bakingapp.IngredientsSteps.IngredientStepsActivity;
import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.adapter.RecipesAdapter;
import app.elghamry.android.bakingapp.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

import static app.elghamry.android.bakingapp.R.id.rv_recipes;

public class MainActivity extends MvpActivity<MainView,MainPresenter> implements MainView{
    private GridLayoutManager gLayout;
    private ProgressDialog progress;
    RecipesAdapter rv_adapter;
    @BindView(rv_recipes)
    RecyclerView rv_recpies;
    List<Recipe> myRecipes= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //create api object from retrofit
        initFields();

        getPresenter().initService();

    }

    private void initFields() {

        progress = new ProgressDialog(MainActivity.this);
        int no_columns;
        if(getResources().getBoolean(R.bool.is_phone)){
            no_columns=1;

        }
        else
        {
            no_columns=2;

        }
        gLayout = new GridLayoutManager(MainActivity.this, no_columns);
        rv_recpies.setLayoutManager(gLayout);





    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
    MainPresenter    presenter = new MainPresenter();
        presenter.attachView(this);

        return presenter;
    }


    @Override
    public void setData(ArrayList<Recipe> recipes) {
        rv_adapter=new RecipesAdapter(R.layout.reciepes_item,recipes);
        myRecipes.addAll(recipes);


        rv_recpies.setAdapter(rv_adapter);

        rv_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                throwIntent(position);
            }
        });
    }

    private void throwIntent(int i) {
        Intent intent = new Intent(this,IngredientStepsActivity.class);
        intent.putExtra(getString(R.string.ingredient_key),myRecipes.get(i).getIngredients());
        intent.putExtra(getString(R.string.step_key),myRecipes.get(i).getSteps());
        startActivity(intent);
    }

    @Override
    public void progressState(boolean a) {
        if(a){
            showProgressDialog();
        }else
        {
            dismissProgressDialog();

        }

    }

    void showProgressDialog() {
        progress = ProgressDialog.show(this, "", "Loading", true);
    }

    void dismissProgressDialog() {
        progress.dismiss();
    }

    @Override
    public void showError(Throwable e) {
        Context context = getApplicationContext();
        CharSequence text = "Check your connection ,please";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
