package algonquin.cst2355.mobilefinalprojectlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 1st view of the application where a user can input a recipe to search for.
 *  @author Jennifer Goodchild
 * @section CST2355 012
 * @creationDate 06/04/2024
 */
public class RecipeActivity extends AppCompatActivity {

    ArrayList<RecipeModel> recipeModels = new ArrayList<>();

    RecyclerView recyclerView;

    RecipeAdapter adapter;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * 1st method that is called when the apps starts.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity_main);


        recyclerView = findViewById(R.id.RecipeRecyclerView);
        adapter = new RecipeAdapter(this, recipeModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("My Data", MODE_PRIVATE);
        String recipeName = prefs.getString("recipeName", "");
        EditText recipeET = findViewById(R.id.searchET);
        recipeET.setText(recipeName);

        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.search_Button);
/**
 * onClickListener on the Search button that has volley built into it to retrieve the JSON data of the recipes searched for.
 */

        button.setOnClickListener(click -> {


            /**
             * gets the search text
             */
            String searchText = recipeET.getText().toString();


            /**
             * updates shared preferences to save search state
             */
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("recipeName", searchText);
            editor.apply();

            /**
             * clears the search text
             */
            recipeET.setText("");

            /**
             * shows the toast letting you know the recipes are loading
             */
            Toast.makeText(this, R.string.loading, Toast.LENGTH_SHORT).show();

            /**
             * function that fetches the data to be displayed in recyclerview
             */
            sendRequest(searchText);

        });


    }

    /**
     * Displays the menu toolbar at the top of the page.
     * @param menu The options menu in which you place your items.
     *
     * @return is true to show that it's been handled or else is false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu_recipe, menu);
        return true;
    }


    /**
     * Allows user to use the home button which clears recyclerview as well as displaying the alertdialog to show how the app is to be used.
     * @param item The menu item that was selected.
     *
     * @return true if the item is handled. Otherwise false.
     */


@Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.aboutRecipe) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.help_menu))
                    .setMessage(getString(R.string.help_message))
                    .setPositiveButton(getString(R.string.ok),null)
                    .show();

            return true;
                 }  else if(id==R.id.myMenu){
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
        return true;
    }
    return super.onOptionsItemSelected(item);
}

    /**
     *
     * @param query The recipe that was typed into the edittext that is then used to pull the json data.
     */

    private void sendRequest(String query) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=12faad18cf964f1aa8b289f1d88610f4";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // get the whole json object
                    JSONObject jsonObject = new JSONObject(response);
                    // get the 'results' array
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    // iterate over results array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject recipeObject = jsonArray.getJSONObject(i);

                        RecipeModel recipeModel = new RecipeModel();
                        recipeModel.setId(recipeObject.getString("id"));
                        recipeModel.setTitle(recipeObject.getString("title"));
                        recipeModel.setImage(recipeObject.getString("image"));
                        System.out.println(recipeModel.getTitle());
                        recipeModels.add(recipeModel);


                    }
                    Log.d("hello", "made it here");
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, error -> Toast.makeText(RecipeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());

        requestQueue.add(stringRequest);
    }

    /**
     *
     * RecyclerViewAdapter inner class to display the recipes
     */
    class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

        private Context myContext;
        private ArrayList<RecipeModel> recipes;


        public RecipeAdapter(Context myContext, ArrayList<RecipeModel> recipes) {
            this.myContext = myContext;
            this.recipes = recipes;
        }

        @NonNull
        @Override
        public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(myContext);
            View view = inflater.inflate(R.layout.search_row_view, parent, false);

            return new RecipeAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            RecipeModel recipeModel = recipes.get(position);
            holder.id.setText(recipeModel.getId().toString());
            holder.title.setText(recipeModel.getTitle());
            Picasso.get().load(recipeModel.getImage()).into(holder.imageView);
            //holder.imageView.setImageResource(Integer.parseInt(recipeModel.getImage().toString()));


            // add your click listeners here for your functions


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return recipes.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView id;
            TextView title;
            ImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.idTextView);
                title = itemView.findViewById(R.id.titleTextView);
                imageView = itemView.findViewById(R.id.imageView);


            }
        }




    }

}



