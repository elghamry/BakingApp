package app.elghamry.android.bakingapp.IngredientsSteps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import app.elghamry.android.bakingapp.IngredientsSteps.Fragments.IngredientsFragment;
import app.elghamry.android.bakingapp.R;

/**
 * Created by ELGHAMRY on 10/05/2017.
 */

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            IngredientsFragment fragment = IngredientsFragment.newInstance();
            fragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.IngFragment, fragment)
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
}
