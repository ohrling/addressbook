package BackupTest;

public class BackupTestMain {

/*

    I main nedan skapas en databas med en tabell som är en kontaktlista för personuppgifter.

    Kolumnen isDeleted i tabellen kan vara 0 (false) eller 1 (true). Dvs den kolumnen avgör om kontakte ska vara "raderad" eller inte

     Kontaktlistans telefonnummer är en unik identifierare för poster som
    ska raderas. Därför bör telefonnummer användas som villkor när man ska radera en post i tabellen.
    Det betyder också att alla telefonnummer måste vara unika när de matas in i databasen.
    Poster som införs i tabellen som inte har unikt telefonnummer kommer inte att registreras*/

    public static void main(String[] args) {

        //DataBaseCreator skapar "in memory" databasen sqlLite
        DataBaseCreator localDatabase = new DataBaseCreator();
        localDatabase.createNewDatabase("adressbook.db");

        //  DataBaseSqlPerformers uppgift är att utföra mottagna sql-kommandon i databasen
        DataBaseSqlPerformer sqlLiteDBCommunicator = new DataBaseSqlPerformer();

        //här skapas tabellen ContactsList                                                                                                                                                      `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP                                  //lastUpdated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        sqlLiteDBCommunicator.performSqlStatment("CREATE TABLE IF NOT EXISTS ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,firstName varchar(40),lastName varchar(40),adress varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100), isDeleted tinyint(1), lastUpdated DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')));");

        /*här skapas en trigger som ser till att den senast modifierade raden i tabellen får en ny tidsstämpel när den ändras
        * detta för att man ska kunna sortera i tabellen efter senast ändrde rader */
        sqlLiteDBCommunicator.performSqlStatment("CREATE TRIGGER change_lastUpdated AFTER UPDATE On ContactsList BEGIN UPDATE ContactsList SET lastUpdated = (strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')) WHERE phoneNumber = NEW.phoneNumber; END;");

        //sätter in data i ContactsList-tabellen
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company, isDeleted) VALUES ('Berit','Bengtson','Storgatan 9','0732471091','IKEA', 0);");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company, isDeleted) VALUES ('Anders','Andersson','Lillgatan 137','0739922270','Siemens', 0);");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company, isDeleted) VALUES ('Simon','Simonsson','Kungsgatan 29','0709842371','Sony', 0);");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company, isDeleted) VALUES ('Fadime','Molin','Västra Torget 7','0729242531','Volvo', 0);");

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList INNAN delete-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfContactsList();

        //raderar kontakt från tabellen
        //OBS! phoneNumber används som argument för vilken rad i tabellen som ska raderas
        sqlLiteDBCommunicator.performSqlStatment("UPDATE ContactsList SET isDeleted = 1 WHERE phoneNumber='0709842371';");


        System.out.println();
        System.out.println("innehåll i ContactsList EFTER delete-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfContactsList();

        //ångra senast radering
        sqlLiteDBCommunicator.undoLatestDelete();

        System.out.println();
        System.out.println("innehåll i ContactsList EFTER ångra-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfContactsList();



    }
}
