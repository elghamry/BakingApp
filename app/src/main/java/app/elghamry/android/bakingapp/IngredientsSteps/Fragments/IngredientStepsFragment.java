package app.elghamry.android.bakingapp.IngredientsSteps.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;

import app.elghamry.android.bakingapp.IngredientsSteps.IngredientsActivity;
import app.elghamry.android.bakingapp.IngredientsSteps.StepsActivity;
import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.adapter.StepsAdapter;
import app.elghamry.android.bakingapp.listeners.IngredientListener;
import app.elghamry.android.bakingapp.listeners.StepsListener;
import app.elghamry.android.bakingapp.model.Ingredient;
import app.elghamry.android.bakingapp.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public class IngredientStepsFragment extends MvpFragment<IngredientStepsFragmentView,IngredientStepsFragmentPresenter> implements IngredientStepsFragmentView {
   @BindView(R.id.rv_steps)
   RecyclerView recyclerViewSteps;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    ArrayList<Step> steps;
    ArrayList<Ingredient> ingredients;
    StepsAdapter stepsAdapter;
    IngredientListener ingredientListener;
    StepsListener stepsListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_steps_fragment, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;

    }


    public static IngredientStepsFragment newInstance() {
       IngredientStepsFragment fragment = new IngredientStepsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public IngredientStepsFragmentPresenter createPresenter() {
        IngredientStepsFragmentPresenter presenter = new IngredientStepsFragmentPresenter();
        presenter.attachView(this);
        presenter.init();
        return presenter;
    }

    @Override
    public void setData() {
        steps =((ArrayList<Step>)getArguments().getSerializable("Steps"));
        ingredients = (ArrayList<Ingredient>)getArguments().getSerializable("Ingredient");
         stepsAdapter= new StepsAdapter(R.layout.step_item,steps);
        stepsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(getResources().getBoolean(R.bool.is_phone)) {
                    Intent intent = new Intent(getActivity(), StepsActivity.class);
                    intent.putExtra("Steps", steps);
                    Log.d(TAG, "onItemClick: " + position);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
                else {
                    stepsListener.callStepsFragment(position);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSteps.setLayoutManager(linearLayoutManager);
        recyclerViewSteps.setAdapter(stepsAdapter);
    }

    @OnClick(R.id.ingredientLayout)
    void IngredientClicked()
    {
        if(getResources().getBoolean(R.bool.is_phone)) {
            Intent intent = new Intent(getActivity(), IngredientsActivity.class);
            intent.putExtra("Ingredient", ingredients);
            startActivity(intent);
        }
        else
        {
            ingredientListener.callIngredientFragment();
            Log.d(TAG, "IngredientClicked: ");
        }
    }

    public void setListner(IngredientListener Listner)
    {
        ingredientListener = Listner;
    }
    public  void setListener2(StepsListener Listener)
    {
        stepsListener = Listener;
    }
}
