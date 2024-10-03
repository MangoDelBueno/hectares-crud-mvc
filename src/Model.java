import java.sql.*;
import java.util.ArrayList;

public class Model {
    private Connection connection;

    public Model() {
        this.connection = ConnectionSingleton.getConnection();
    }

    public boolean save(Hectare hectare) {
        if (hectare == null){
            return false;
        }
        if(getHectareById(hectare.getIdHectare())!=null){
            return false;
        }
        String query = "INSERT INTO Hectares (idHectare, community, isRented, latitude, longitude, validity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preSta = connection.prepareStatement(query)) {
            preSta.setInt(1, hectare.getIdHectare());
            preSta.setString(2, hectare.getCommunity());
            preSta.setBoolean(3, hectare.isRented());
            preSta.setDouble(4, hectare.getLatitude());
            preSta.setDouble(5, hectare.getLongitude());
            preSta.setBoolean(6, hectare.isValidity());

            preSta.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while inserting Hectare: " + e.getMessage());
            return false;
        }
        System.out.println(":D");
        return true;
    }

    public Hectare getHectareById(int idHectare) {
        if(idHectare == -1){
            return null;
        }
        String query = "SELECT * FROM Hectares WHERE idHectare = ? AND validity = 1";
        try (PreparedStatement preSta = connection.prepareStatement(query)) {
            preSta.setInt(1, idHectare);
            ResultSet rs = preSta.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int id = rs.getInt("idHectare");
            String community = rs.getString("community");
            boolean isRented = rs.getBoolean("isRented");
            double latitude = rs.getDouble("latitude");
            double longitude = rs.getDouble("longitude");
            boolean validity = rs.getBoolean("validity");

            return new Hectare(id, community, isRented, latitude, longitude);

        } catch (SQLException e) {
            System.err.println("Error while inserting Hectare: " + e.getMessage());
            return null;
        }
    }

    public boolean upadatehectare(Hectare hectare) {
        if (hectare==null || getHectareById(hectare.getIdHectare()) == null)
            return false;
        String query = "UPDATE Hectares SET community = ?, isRented = ?, latitude = ?, longitude = ? WHERE idHectare = ?";

        try (PreparedStatement preSta = connection.prepareStatement(query)) {

            preSta.setString(1, hectare.getCommunity());
            preSta.setBoolean(2, hectare.isRented());
            preSta.setDouble(3, hectare.getLatitude());
            preSta.setDouble(4, hectare.getLongitude());
            preSta.setInt(5, hectare.getIdHectare());

            int rowsAffected = preSta.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Error while update Hectare: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteHectare(Hectare hectare) {
        String query = "UPDATE Hectares SET validity = 0 WHERE idHectare = ?";

        try (PreparedStatement preSta = connection.prepareStatement(query)) {
            preSta.setInt(1, hectare.getIdHectare());
            int rowsAffected = preSta.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Error while delete Hectare: " + e.getMessage());
            return false;
        }

    }

    public ResultSet getAllHectares() {
        String query = "SELECT * FROM Hectares WHERE validity = 1";
        ResultSet rs = null;

        try {
            PreparedStatement preSta = connection.prepareStatement(query);
            rs = preSta.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error while loading Hectares: " + e.getMessage());
        }
        return rs;
    }
}