package dbWorker;

import java.sql.*;

// denna klass har som uppgift att skapa "inmemory" databaser
public class DataBaseConnector {
    private final String CREATE_DB = "CREATE TABLE IF NOT EXISTS ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,firstName varchar(40),lastName varchar(40), email varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100), isDeleted tinyint(1), lastUpdated DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')));";
    private final String CREATE_TRIGGER = "CREATE TRIGGER change_lastUpdated AFTER UPDATE On ContactsList BEGIN UPDATE ContactsList SET lastUpdated = (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')) WHERE phoneNumber = NEW.phoneNumber; END;";
    private final String DB_NAME = "addressbook.db";

    private Connection connection = null;
    private Statement stmt;

    public boolean init() {
        String location = "jdbc:sqlite:" + DB_NAME;

        //Om inte databasen redan finns så skapas den
        try {
            connection = DriverManager.getConnection(location);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                stmt = connection.createStatement();
                createTables();
                return true;
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void closeCon() {
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        //här skapas tabellen ContactsList
        stmt.execute(CREATE_DB);

        /*här skapas en trigger som ser till att den senast modifierade raden i tabellen får en ny tidsstämpel när den ändras
         * detta för att man ska kunna sortera i tabellen efter senast ändrde rader */
        stmt.execute(CREATE_TRIGGER);
    }

    public Connection getConnection() {
        return connection;
    }
}



