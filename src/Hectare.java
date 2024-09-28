public class Hectare {
    private int idHectare;
    private String community;
    private double latitude, longitude;
    private boolean isRented, validity;

    public Hectare(int idHectare, String community, boolean isRented, double latitude, double longitude) {
        this.idHectare = idHectare;
        this.community = community;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isRented = isRented;
        this.validity = true;
    }

    public int getIdHectare() {
        return idHectare;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }
}
