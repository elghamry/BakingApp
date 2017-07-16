package app.elghamry.android.bakingapp.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.model.Step;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public class StepsAdapter  extends BaseQuickAdapter<Step,BaseViewHolder> {
    public StepsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Step item) {
        helper.setText(R.id.item_name, item.getShortDescription());
        if(!(item.getThumbnailURL()).equals("")&&!item.getThumbnailURL().matches("(.*).mp4")) {

            Picasso.with(helper.getView(R.id.iv_step).getContext()).load(item.getThumbnailURL()).into((ImageView)
                    helper.getView(R.id.iv_step));
        }


    }
}