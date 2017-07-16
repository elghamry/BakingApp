package app.elghamry.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import app.elghamry.android.bakingapp.IngredientsSteps.IngredientStepsActivity;
import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.model.Recipe;


public class RecipesWidget extends AppWidgetProvider {

    private static final String TAG = RecipesWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.name_sp_widget),0);
        String jsonString = sharedPreferences.getString(String.valueOf(appWidgetId),null);
        Gson gson = new Gson();
        Recipe recipe = null;
        if(jsonString==null || jsonString.length()==0) return;
        else recipe = gson.fromJson(jsonString, Recipe.class);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.recipes_widget);
        views.setTextViewText(R.id.tv_ingredients_label, String.format(context.getString(R.string.appwidget_ingredient_text_label),recipe.getName()));
        Intent ingredientStepsLaunchIntent = new Intent(context, IngredientStepsActivity.class);
        ingredientStepsLaunchIntent.putExtra("Ingredient",recipe.getIngredients());
        ingredientStepsLaunchIntent.putExtra("Steps",recipe.getSteps());
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, ingredientStepsLaunchIntent,0);
        views.setOnClickPendingIntent(R.id.iv_appwidget,pendingIntent);

        Intent widgetConfigureLaunchIntent = new Intent(context, RecipesWidgetConfigureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        widgetConfigureLaunchIntent.putExtras(bundle);
        views.setOnClickPendingIntent(R.id.tv_ingredients_label, PendingIntent.getActivity(context,1,widgetConfigureLaunchIntent,0));
        /*
        Now we setup an intent to launch a ListWidgetService, which is a RemoteViewsService
         */
        Intent intent = new Intent(context, WidgetService.class);
        // Add the app widget ID to the intent extras.
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.lv_ingredients,intent);
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            RecipesWidgetConfigureActivity.deletePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            int id = intent.getIntExtra(context.getString(R.string.key_widget_id),0);
            updateAppWidget(context, gm, id);
        }else
        super.onReceive(context, intent);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

