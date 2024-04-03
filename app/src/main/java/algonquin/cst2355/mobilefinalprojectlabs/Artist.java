package algonquin.cst2355.mobilefinalprojectlabs;
public class Artist {
    private int id;
    private String name;
    private String link;
    private String picture;
    private String type;


    public Artist(int id, String name, String link, String picture, String type){
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.type = type;

    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
