package app.elghamry.android.bakingapp.widget;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;

import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.adapter.RecipesWidgetAdapter;
import app.elghamry.android.bakingapp.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesWidgetConfigureActivity extends MvpActivity<RwcActivityView,RwcActivityPresenter> implements RwcActivityView {

    private static final String TAG = RecipesWidgetConfigureActivity.class.getSimpleName();
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ProgressDialog progressDialog;

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipeList;

    public RecipesWidgetConfigureActivity() {
        super();
    }

    static void deletePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getApplicationContext().getSharedPreferences(context.getString(R.string.name_sp_widget), 0).edit();
        prefs.remove(String.valueOf(appWidgetId));
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.recipes_widget_configure);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        ButterKnife.bind(this);

    }

    @NonNull
    @Override
    public RwcActivityPresenter createPresenter() {
        RwcActivityPresenter presenter = new RwcActivityPresenter();
        presenter.attachView(this);
        presenter.fetchRecipes();
        return presenter;
    }

    @Override
    public void setProgressState(boolean state) {
        if (state)
            showProgressDialog();
        else
            dismissProgressDialog();
    }

    void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "", "Loading", true);
    }

    void dismissProgressDialog() {
        progressDialog.dismiss();
    }

        @Override
    public void setUpRecyclerView(final ArrayList<Recipe> recipes) {
        RecipesWidgetAdapter recipeAdapter = new RecipesWidgetAdapter(R.layout.item_configuration_widget_list,recipes);
      recipeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

              onItemClicked(recipes.get(position));
          }
      });
        rvRecipeList.setAdapter(recipeAdapter);
    }

    @Override
    public void showError(Throwable e) {
        Context context = getApplicationContext();
        CharSequence text = "Check your connection ,please";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onItemClicked(Recipe recipe) {
        //just save the recipe as a shared preference as serialized JSON
        Gson gson = new Gson();
        String stringifyRecipe = gson.toJson(recipe);
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences(getString(R.string.name_sp_widget),0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(mAppWidgetId),stringifyRecipe);
        editor.commit();

        Intent i = new Intent(this, RecipesWidget.class);
        i.putExtra(getString(R.string.key_widget_id),mAppWidgetId);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        this.sendBroadcast(i);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }


}

