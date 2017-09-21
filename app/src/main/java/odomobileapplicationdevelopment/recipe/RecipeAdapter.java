package odomobileapplicationdevelopment.recipe;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by j on 9/18/17.
 */

public class RecipeAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Recipe> mRecipes;

    public RecipeAdapter(Context context, int resource, ArrayList<Recipe> arrayList){
        super(context,resource,arrayList);
        mContext = context;
        mRecipes = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Recipe recipe = mRecipes.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = null;

        if( position == 0) {
            view = layoutInflater.inflate(R.layout.recipe_list_item_special, null);
            TextView special = (TextView) view.findViewById(R.id.specail_text_box);
            special.setText("I'm Special!!");
        }
        else {
            view = layoutInflater.inflate(R.layout.recipe_list_item, null);
        }

        TextView recipeName = (TextView) view.findViewById(R.id.Recipe_Name);
        TextView recipeIngredients = (TextView) view.findViewById(R.id.Ingredients);
        ImageView image = (ImageView) view.findViewById(R.id.Recipe_Image);

        recipeIngredients.setText(String.valueOf( recipe.getmIngredients() ));
        recipeName.setText( String.valueOf(recipe.getmRecipeName()));
        image.setImageResource(R.drawable.burger);

        return view;
    }
}
