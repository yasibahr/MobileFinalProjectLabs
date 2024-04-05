package algonquin.cst2355.mobilefinalprojectlabs.SunriseSunsetAPP;

/**
 * Author: Fereshteh Rohani, 041096855
 * Course: CST2335
 * Lab Section: #012
 * Date: 2024 4 4
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteLocation {

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="latitude")
    private String latitude;

    @ColumnInfo(name="longitude")
    private String longitude;

    public FavoriteLocation (String latitude, String longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude() {
        this.latitude = latitude;
    }

    public void setLongitude() {
        this.longitude = longitude;
    }

}
