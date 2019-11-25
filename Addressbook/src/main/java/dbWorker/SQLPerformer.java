package dbWorker;

import core.singletons.MessageContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Initierar databasen med grundtabeller och en trigger om dessa inte finns
// Parent-klass till alla klasser som utnyttjar databasen.
public abstract class SQLPerformer implements SQL {

    PreparedStatement stmt;
    Connection connection = null;

    public SQLPerformer() {
        PropertyLoader propertyLoader = new PropertyLoader();
        try {
            String location = propertyLoader.getProperty("sql.location") + propertyLoader.getProperty("sql.dbname"); // Hämtas från application.properties
            connection = DriverManager.getConnection(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        if(createTables())
            MessageContainer.setRightLabelMessage("Databasen initierad");
        else {
            MessageContainer.setRightLabelMessage("Fel vid initiering av databasen");
        }
    }

    public void closeCon() {
        try {
            if(stmt != null)
                stmt.close();
            if(stmt != null)
                connection.close();
            MessageContainer.setRightLabelMessage("Anslutning till databasen stängd");
        } catch (SQLException e) {
            e.printStackTrace();
            MessageContainer.setRightLabelMessage("Fel vid stängning av anslutning till databasen");
        }
    }

    private boolean createTables() {
        try {
            //här skapas tabellen ContactsList
            String CREATE_DB = "CREATE TABLE IF NOT EXISTS ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,firstName varchar(40),lastName varchar(40), email varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100), isDeleted tinyint(1), lastUpdated DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')));";
            stmt = connection.prepareStatement(CREATE_DB);
            stmt.execute();

            /*här skapas en trigger som ser till att den senast modifierade raden i tabellen får en ny tidsstämpel när den ändras
             * detta för att man ska kunna sortera i tabellen efter senast ändrde rader */
            String CREATE_TRIGGER = "CREATE TRIGGER IF NOT EXISTS change_lastUpdated AFTER UPDATE On ContactsList BEGIN UPDATE ContactsList SET lastUpdated = (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')) WHERE phoneNumber = NEW.phoneNumber; END;";
            stmt = connection.prepareStatement(CREATE_TRIGGER);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }




}
