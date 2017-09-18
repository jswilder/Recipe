package odomobileapplicationdevelopment.recipe;

import java.net.URL;

/**
 * Created by j on 9/18/17.
 * Class to hold an individual recipes data
 */

public class Recipe {

    private String mRecipeName;
    private String mIngredients;
    private URL mURL;
    private String mJPG;

    Recipe(){
        mRecipeName = "Ocean";
        mIngredients = "Salt, Water";
    }

    Recipe(String name, String ingredients){
        mRecipeName = name;
        mIngredients = ingredients;
    }

    Recipe(String name, String ingredients, URL url){
        mRecipeName = name;
        mIngredients = ingredients;
        mURL = url;
    }


    public String getmRecipeName() {
        return mRecipeName;
    }

    public String getmIngredients() {
        return mIngredients;
    }

    public URL getmURL() {
        return mURL;
    }

    public void setmURL(URL mURL) {
        this.mURL = mURL;
    }
}
