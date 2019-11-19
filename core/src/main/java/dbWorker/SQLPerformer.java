package dbWorker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQLPerformer implements SQL {
    private final String CREATE_DB = "CREATE TABLE IF NOT EXISTS ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,firstName varchar(40),lastName varchar(40), email varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100), isDeleted tinyint(1), lastUpdated DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')));";
    private final String CREATE_TRIGGER = "CREATE TRIGGER IF NOT EXISTS change_lastUpdated AFTER UPDATE On ContactsList BEGIN UPDATE ContactsList SET lastUpdated = (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')) WHERE phoneNumber = NEW.phoneNumber; END;";

    PreparedStatement stmt;
    Connection connection = null;
    private PropertyLoader propertyLoader;

    SQLPerformer() {
        propertyLoader = new PropertyLoader();
        try {
            String location = propertyLoader.getProperty("sql.location") + propertyLoader.getProperty("sql.dbname");
            System.out.println(location);
            connection = DriverManager.getConnection(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String init() {
        if(createTables())
            return "Databasen initierad";
        else {
            return "Fel vid initiering av databasen";
        }
    }

    public String closeCon() {
        try {
            if(stmt != null)
                stmt.close();
            if(stmt != null)
                connection.close();
            return "Anslutning till databasen stängd";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Fel vid stängning av anslutning till databasen";
        }
    }

    private boolean createTables() {
        try {
            //här skapas tabellen ContactsList
            stmt = connection.prepareStatement(CREATE_DB);
            stmt.execute();

            /*här skapas en trigger som ser till att den senast modifierade raden i tabellen får en ny tidsstämpel när den ändras
             * detta för att man ska kunna sortera i tabellen efter senast ändrde rader */
            stmt = connection.prepareStatement(CREATE_TRIGGER);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }




}
