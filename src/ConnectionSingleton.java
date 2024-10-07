import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSingleton {
    static private Connection coneccion;

    private ConnectionSingleton(){
        String url = "jdbc:sqlserver://localhost:1433;databaseName=MANGODB;encrypt=true;trustServerCertificate=true";

        try {
            coneccion = DriverManager.getConnection(url, "sa", "sql2025");

        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("FELICIDADES TE CONECTASTE A UNA BD");

    }

    public static Connection getConnection(){
        if(coneccion == null){
            new ConnectionSingleton();
        }
        return coneccion;
    }


}
