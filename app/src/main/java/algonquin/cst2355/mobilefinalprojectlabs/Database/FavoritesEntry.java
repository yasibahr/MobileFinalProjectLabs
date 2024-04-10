package algonquin.cst2355.mobilefinalprojectlabs.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites") // Define the table name in the database
public class FavoritesEntry {
    @PrimaryKey(autoGenerate = true)
    private int id; // Primary key column with auto-generated values

    // Columns to store information about the favorite song
    private int songId; // Unique identifier for the song
    @ColumnInfo(name = "artist_name") // Specify the column name in the database
    private String artistName; // Name of the artist
    @ColumnInfo(name = "song_title") // Specify the column name in the database
    private String songTitle; // Title of the song
    @ColumnInfo(name = "album_cover_url") // Specify the column name in the database
    private String albumCoverUrl; // URL of the album cover

    // Constructor to initialize a FavoritesEntry object
    public FavoritesEntry(int songId, String artistName, String songTitle, String albumCoverUrl) {
        this.songId = songId;
        this.artistName = artistName;
        this.songTitle = songTitle;
        this.albumCoverUrl = albumCoverUrl;
    }

    // Getters and setters for all the columns
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getAlbumCoverUrl() {
        return albumCoverUrl;
    }

    public void setAlbumCoverUrl(String albumCoverUrl) {
        this.albumCoverUrl = albumCoverUrl;
    }
}
