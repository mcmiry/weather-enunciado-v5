package training.weather.models;

import com.google.gson.annotations.SerializedName;

public class CityLocation {

    @SerializedName("longt")
    private String longitude;

    @SerializedName("latt")
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
