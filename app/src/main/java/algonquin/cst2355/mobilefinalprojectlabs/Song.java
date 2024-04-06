package algonquin.cst2355.mobilefinalprojectlabs;

public class Song {
    private String title; // Title of the song
    //private int id; // Unique identifier for the song
    private int duration; // Duration of the song
    private String albumName; // Name of the album containing the song
    private String albumCoverUrl; // URL of the album cover image

    private boolean isFavorite;

    // Constructor to initialize a Song object
    public Song(String title, int duration, String albumName, String albumCoverUrl, boolean isFavorite) {
        this.title = title;
        this.duration = duration;
        this.albumName = albumName;
        this.albumCoverUrl = albumCoverUrl;
        this.isFavorite = isFavorite;
    }

    // Getter methods to retrieve the values of the song properties
    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumCoverUrl() {
        return albumCoverUrl;
    }

}
