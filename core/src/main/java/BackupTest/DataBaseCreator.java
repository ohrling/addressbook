package BackupTest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

// denna klass har som uppgift att skapa "inmemory" databaser
public class DataBaseCreator {

    Connection connection = null;

    public void createNewDatabase(String dataBaseName) {

        String location = "jdbc:sqlite:" + dataBaseName;

        //Om inte databasen redan finns så skapas den
        try (Connection connection = DriverManager.getConnection(location)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}



