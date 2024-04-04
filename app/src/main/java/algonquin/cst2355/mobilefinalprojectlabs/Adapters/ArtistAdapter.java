package algonquin.cst2355.mobilefinalprojectlabs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.Activities.MainActivity;
import algonquin.cst2355.mobilefinalprojectlabs.Artist;
import algonquin.cst2355.mobilefinalprojectlabs.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private ArrayList<Artist> artists;
    private Context context;
    private OnItemClickListener listener; // Add listener variable

    public interface OnItemClickListener {
        void onItemClick(Artist artist);
    }
    public ArtistAdapter(ArrayList<Artist> artists, OnItemClickListener listener) {
        this.artists = artists;
        this.listener = listener;
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

    public void setFilteredList(ArrayList<Artist> filteredList){
        this.artists = filteredList;
        notifyDataSetChanged();
    }

    // Method to set the item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        // Bind data to the views and set click listener
        final Artist artist = artists.get(position);
        Glide.with(context).load(artist.getPicture()).into(holder.image);
        holder.name.setText(artist.getName());
        holder.type.setText(artist.getType());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(artist); // Notify the listener
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}