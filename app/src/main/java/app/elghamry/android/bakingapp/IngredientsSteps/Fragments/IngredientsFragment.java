package app.elghamry.android.bakingapp.IngredientsSteps.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.elghamry.android.bakingapp.R;
import app.elghamry.android.bakingapp.adapter.IngredientsAdapter;
import app.elghamry.android.bakingapp.model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public class IngredientsFragment extends Fragment {
    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;
    public static IngredientsFragment newInstance() {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);
        ButterKnife.bind(this, rootView);
        getActivity().setTitle("Ingredients");
initFields();
        return rootView;

    }

  public  void   initFields(){
      ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>)getArguments().getSerializable("Ingredient");
      IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(R.layout.ingredients_item,ingredients);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      rvIngredients.setLayoutManager(linearLayoutManager);
      rvIngredients.setAdapter(ingredientsAdapter);
    }
}
