package algonquin.cst2355.mobilefinalprojectlabs;

public class Artist {
    private int id;
    private String name;
    private String link;
    private String picture;
    private String type;
    private String tracklist; // Field for the tracklist URL

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public Artist(int id, String name, String link, String picture, String type, String tracklist) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.type = type;
        this.tracklist = tracklist; // Initialize tracklist URL
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                ", type='" + type + '\'' +
                ", tracklist='" + tracklist + '\'' +
                '}';
    }
}
