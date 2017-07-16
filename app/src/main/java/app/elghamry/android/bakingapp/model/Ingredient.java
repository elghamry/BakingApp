package app.elghamry.android.bakingapp.model;

import java.io.Serializable;

/**
 * Created by ELGHAMRY
 */

public class Ingredient implements Serializable {
    private String quantity,measure,ingredient;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return ingredient+": "+quantity+" "+measure;
    }
}
