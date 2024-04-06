package algonquin.cst2355.mobilefinalprojectlabs.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesEntry;
import algonquin.cst2355.mobilefinalprojectlabs.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<FavoritesEntry> favoritesList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FavoritesEntry favorite);
    }

    public FavoritesAdapter(List<FavoritesEntry> favoritesList, OnItemClickListener listener) {
        this.favoritesList = favoritesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        FavoritesEntry favorite = favoritesList.get(position);
        holder.bind(favorite, listener);
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public void updateFavoritesList(List<FavoritesEntry> favoritesList) {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }

    static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        private TextView artistNameTextView;
        private TextView songTitleTextView;
        private ImageView albumCoverImageView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            artistNameTextView = itemView.findViewById(R.id.artistNameTextView);
            songTitleTextView = itemView.findViewById(R.id.songTitleTextView);
            albumCoverImageView = itemView.findViewById(R.id.albumCoverImageView);
        }

        public void bind(final FavoritesEntry favorite, final OnItemClickListener listener) {
            artistNameTextView.setText(favorite.getArtistName());
            songTitleTextView.setText(favorite.getSongTitle());
            // Load album cover image using Glide or any other library
            Glide.with(itemView.getContext())
                    .load(favorite.getAlbumCoverUrl())
                    .into(albumCoverImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(favorite);
                }
            });
        }
    }
}
