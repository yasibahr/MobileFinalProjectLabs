package algonquin.cst2355.mobilefinalprojectlabs.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2355.mobilefinalprojectlabs.Adapters.FavoritesAdapter;
import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesDatabase;
import algonquin.cst2355.mobilefinalprojectlabs.Database.FavoritesEntry;
import algonquin.cst2355.mobilefinalprojectlabs.R;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FavoritesAdapter favoritesAdapter;
    private FavoritesDatabase favoritesDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesAdapter = new FavoritesAdapter(new ArrayList<>(), new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavoritesEntry favorite) {
                showFavoriteDetailsDialog(favorite);
            }
        });
        recyclerView.setAdapter(favoritesAdapter);

        favoritesDatabase = FavoritesDatabase.getInstance(this);
        favoritesDatabase.favoritesDao().getAllFavorites().observe(this, new Observer<List<FavoritesEntry>>() {
            @Override
            public void onChanged(List<FavoritesEntry> favoritesList) {
                favoritesAdapter.updateFavoritesList(favoritesList);
            }
        });
    }

    private void showFavoriteDetailsDialog(FavoritesEntry favorite) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Favorite Song Details");

        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_favorite, null);
        builder.setView(dialogView);

        // Initialize views in the dialog layout
        TextView artistTextView = dialogView.findViewById(R.id.artistNameTextView);
        TextView songTitleTextView = dialogView.findViewById(R.id.songTitleTextView);
        ImageView albumCoverImageView = dialogView.findViewById(R.id.albumCoverImageView);

        // Set the details of the selected favorite song
        artistTextView.setText(favorite.getArtistName());
        songTitleTextView.setText(favorite.getSongTitle());
        Glide.with(this).load(favorite.getAlbumCoverUrl()).into(albumCoverImageView);

        // Add a button to dismiss the dialog
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}