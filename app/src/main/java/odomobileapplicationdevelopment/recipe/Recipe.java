package odomobileapplicationdevelopment.recipe;

/**
 * Created by j on 9/18/17.
 * Class to hold an individual recipes data
 */

public class Recipe {

    private String mRecipeName;
    private String mIngredients;
    private String mURL;
    private String mJPG;

    Recipe(){
        mRecipeName = "Ocean";
        mIngredients = "Salt, Water";
    }

    Recipe(String name, String ingredients){
        mRecipeName = name;
        mIngredients = ingredients;
    }

    public String getmRecipeName() {
        return mRecipeName;
    }

    public String getmIngredients() {
        return mIngredients;
    }
}
