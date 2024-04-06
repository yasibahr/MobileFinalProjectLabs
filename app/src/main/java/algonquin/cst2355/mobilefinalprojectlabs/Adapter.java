package algonquin.cst2355.mobilefinalprojectlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context myContext;
    private ArrayList<RecipeModel> recipes;





    public Adapter(Context myContext, ArrayList<RecipeModel> recipes) {
        this.myContext = myContext;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myContext);
        view = inflater.inflate(R.layout.search_row_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RecipeModel recipeModel = recipes.get(position);
        holder.id.setText(recipeModel.getId().toString());
        holder.title.setText(recipeModel.getTitle().toString());
        holder.bind(recipeModel);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView title;
       ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idTextView);
            title = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);


        }

        public void bind(RecipeModel recipeModel) {
            Picasso.get().load(recipeModel.getImage()).into(imageView);

        }
    }

}
