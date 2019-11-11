package dbWorker;

import java.sql.*;

// denna klass har som uppgift att skapa "inmemory" databaser
public class DataBaseCreator {

    private Connection connection = null;
    private Statement stmt;

    public boolean createNewDatabase(String dataBaseName) {
        String location = "jdbc:sqlite:" + dataBaseName;

        //Om inte databasen redan finns så skapas den
        try (Connection connection = DriverManager.getConnection(location)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                stmt = connection.createStatement();
                createTables();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void createTables() throws SQLException {
        //här skapas tabellen ContactsList                                                                                                                                                      `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP                                  //lastUpdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        stmt.execute("CREATE TABLE IF NOT EXISTS ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,firstName varchar(40),lastName varchar(40), email varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100), isDeleted tinyint(1), lastUpdated DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')));");

        /*här skapas en trigger som ser till att den senast modifierade raden i tabellen får en ny tidsstämpel när den ändras
         * detta för att man ska kunna sortera i tabellen efter senast ändrde rader */
        stmt.execute("CREATE TRIGGER change_lastUpdated AFTER UPDATE On ContactsList BEGIN UPDATE ContactsList SET lastUpdated = (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')) WHERE phoneNumber = NEW.phoneNumber; END;");
    }

    public boolean templateCreator() {
        // TODO: 2019-11-11 Tillfällig metod för att att kunna göra manuella poster, ska tas bort vid någon lämplig kommande refactorering
        //sätter in data i ContactsList-tabellen
        try {
            SQLPerformer create = new CreatePerformer();
//            create.perform(connection.prepareStatement("INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES ('Anders','Andersson','anders.andersson@siemens.se','0739922270','Siemens', 0);"));
//            create.perform(connection.prepareStatement("INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES ('Simon','Simonsson','simon.simonsson@sony.se','0709842371','Sony', 0);"));
//            create.perform(connection.prepareStatement("INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES ('Fadime','Molin','fadime.molin@volvo.se','0729242531','Volvo', 0);"));
//            create.perform(connection.prepareStatement("INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES ('Berit','Bengtson','berit.bengtsson@ikea.se','0732471091','IKEA', 0);"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}



