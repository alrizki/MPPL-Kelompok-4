package laundry.laundry.database;

/**
 * Created by Saepul Uyun on 5/29/2018.
 */

//Class konstruksi dan getter setter untuk maps
public class maps {
    private String username;
    private String namalaundry;
    private double lat;
    private double lng;

    public maps() {

    }

    public maps(String username, String namalaundry, double lat, double lng) {
        this.username = username;
        this.namalaundry = namalaundry;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamalaundry() {
        return namalaundry;
    }

    public void setNamalaundry(String namalaundry) {
        this.namalaundry = namalaundry;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}