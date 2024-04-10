package algonquin.cst2355.mobilefinalprojectlabs.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import algonquin.cst2355.mobilefinalprojectlabs.R;
import algonquin.cst2355.mobilefinalprojectlabs.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private ArrayList<Song> songList;

    public SongAdapter(ArrayList<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView durationTextView;
        private TextView albumNameTextView;
        private ImageView albumCoverImageView;
        private Button saveButton;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.songName);
            durationTextView = itemView.findViewById(R.id.duration);
            albumNameTextView = itemView.findViewById(R.id.albumName);
            albumCoverImageView = itemView.findViewById(R.id.albumCoverImageView);
            saveButton = itemView.findViewById(R.id.saveButton);
        }

        public void bind(Song song) {
            titleTextView.setText(song.getTitle());
            durationTextView.setText(String.valueOf(song.getDuration()));
            albumNameTextView.setText(song.getAlbumName());
            // Load album cover image using Picasso library
            Picasso.get().load(song.getAlbumCoverUrl()).into(albumCoverImageView);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle save button click
                }
            });
        }
    }
}
