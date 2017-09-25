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

    Recipe(String name, String ingredients, String img){
        mRecipeName = name;
        mIngredients = ingredients;
        mJPG = img;
    }

    Recipe(String name, String ingredients, URL url){
        mRecipeName = name;
        mIngredients = ingredients;
        mURL = url;
    }

    Recipe(String name, String ingredients, URL url, String img){
        mRecipeName = name;
        mIngredients = ingredients;
        mURL = url;
        mJPG = img;
    }

    public String getmRecipeName() {
        return mRecipeName;
    }

    public String getmIngredients() {

        if( mIngredients.length() > 60 ){
            return mIngredients.substring(0,60) + "...";
        } else {
            return mIngredients;
        }
    }

    public URL getmURL() {
        return mURL;
    }

    public void setmURL(URL mURL) {
        this.mURL = mURL;
    }

    public String getmJPG() {
        return mJPG;
    }

    public void setmJPG(String mJPG) {
        this.mJPG = mJPG;
    }
}