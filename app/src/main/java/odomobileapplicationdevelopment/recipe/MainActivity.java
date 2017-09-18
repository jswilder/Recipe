package odomobileapplicationdevelopment.recipe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String mURL = "http://www.recipepuppy.com/api/?i=&q=steak&p=5";
    private static String LOG_TAG = "RECIPE";
    ArrayList<Recipe> mRecipes;
    private ArrayAdapter<Recipe> recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

        mRecipes = new ArrayList<Recipe>();
        ListView recipeListView = (ListView) findViewById(R.id.Recipe_List_View);
        recipeAdapter = new RecipeAdapter(this, R.layout.recipe_list_item, mRecipes);
        recipeListView.setAdapter(recipeAdapter);
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Recipe recipe = recipeAdapter.getItem(position);

                Toast.makeText(getApplicationContext(),recipe.getmRecipeName(),Toast.LENGTH_SHORT).show();

                // TODO ; Add URL code
/*

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(String.valueOf(recipe.getUrl()));

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
*/
            }
        });
    }

    public void getRecipes(View view){
        new RecipeAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUi() {
        // Do nothing
        Log.i(LOG_TAG,"UpdateUI Called");

        for(Recipe r: mRecipes){
            Log.i(LOG_TAG,"name " + r.getmRecipeName());
            Log.i(LOG_TAG,r.getmIngredients());
        }
    }

    /*
        URL and Async Code
     */
    private class RecipeAsyncTask extends AsyncTask<URL, Void, List<Recipe>> {

        //private static String mURL = "http://www.recipepuppy.com/api/?i=&q=steak&p=5";
        //private static String LOG_TAG = "RECIPE";

        @Override
        protected List<Recipe> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(mURL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.e(LOG_TAG,"IOException in http request",e);
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<Recipe> recipes = new ArrayList<Recipe>(extractFeatureFromJson(jsonResponse));

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);

            if( recipes.isEmpty())
                return;

            mRecipes = (ArrayList)recipes;
            updateUi();
        }


        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            if( url == null )
                return jsonResponse;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

    /*
        Return an ArrayList of Recipe with all matching recipes
     */

        private ArrayList<Recipe> extractFeatureFromJson(String recipesJSON) {

            ArrayList<Recipe> recipes = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(recipesJSON);
                JSONArray resultsArray = root.getJSONArray("results");

                for(int i=0; i<resultsArray.length(); i++){
                    JSONObject recipe = resultsArray.getJSONObject(i);

                    String name = recipe.getString("title");
                    String ingredients = recipe.getString("ingredients");
                    recipes.add( new Recipe(name,ingredients) );
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return recipes;
        }
    }
}