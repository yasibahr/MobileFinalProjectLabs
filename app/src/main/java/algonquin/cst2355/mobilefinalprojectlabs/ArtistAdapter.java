package algonquin.cst2355.mobilefinalprojectlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private ArrayList<Artist> artists;
    private Context context;

    public ArtistAdapter(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.tvName);
            type = itemView.findViewById(R.id.tvType);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.artist_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Bind data to the views
        Artist artist = artists.get(position);
        //holder.image.setImageResource(artist.getImageResource());

        Glide.with(context).load(artist.getPicture()).into(holder.image);
        holder.name.setText(artist.getName());
        holder.type.setText(artist.getType());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}