package app.elghamry.android.bakingapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.model.Ingredient;

/**
 * Created by ELGHAMRY on 05/05/2017.
 */

public class IngredientsAdapter extends BaseQuickAdapter<Ingredient, BaseViewHolder> {
    public IngredientsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Ingredient item) {
        helper.setText(R.id.q, item.getQuantity());
        helper.setText(R.id.i, item.getIngredient());
        helper.setText(R.id.m, item.getMeasure());

    }


}