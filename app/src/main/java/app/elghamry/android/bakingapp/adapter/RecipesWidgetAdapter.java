package app.elghamry.android.bakingapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.model.Recipe;

/**
 * Created by ELGHAMRY on 13/05/2017.
 */

public class RecipesWidgetAdapter extends BaseQuickAdapter<Recipe, BaseViewHolder> {
    public RecipesWidgetAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Recipe item) {
        helper.setText(R.id.tv_recipe_title, item.getName());
        helper.setText(R.id.tv_recipe_servings, String.format(helper.getView(R.id.tv_recipe_servings).getContext().getString(R.string.servings_value),String.valueOf(item.getServings())));


    }
}
