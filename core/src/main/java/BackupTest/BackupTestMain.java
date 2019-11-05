package BackupTest;

public class BackupTestMain {

/*    Här testar jag att skapa en sqlLite databas, den innehåller 2 tabeller ContactsList och ContactsListBackup

    I denna version används kontaktlistans telefonnummer som en unik identifierare när poster som
    ska raderas kopieras över till backup-tabellen. Därför är telefonnummer det enda som fungerar
    att använda som att villkor när man ska radera en post i tabellen (se exempel nedan).
    Det betyder också att alla telefonnummer måste vara unika när de matas in i databasen.
    Alla telefonnummer måste också omges med symbolerna '' för att kunna identifieras.
    Poster som införs i tabellen som inte har unikt telefonnummer kommer inte att föras in*/

    public static void main(String[] args) {

        //DataBaseCreator skapar databasen lokalt
        DataBaseCreator localDatabase = new DataBaseCreator();
        localDatabase.createNewDatabase("adressbook.db");

        //  DataBaseSqlPerformer utför mottagna sql-kommandon i databasen samt utför backup-operationer när data raderas
        DataBaseSqlPerformer sqlLiteDBCommunicator = new DataBaseSqlPerformer();

        //här skapas tabellerna ContactsList och ContactsListBackup
        sqlLiteDBCommunicator.performSqlStatment("CREATE TABLE IF NOT EXISTS ContactsList(id INTEGER PRIMARY KEY AUTOINCREMENT,firstName varchar(40),lastName varchar(40),adress varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100));");
        sqlLiteDBCommunicator.performSqlStatment("CREATE TABLE IF NOT EXISTS ContactsListBackup(id INTEGER PRIMARY KEY AUTOINCREMENT, firstName varchar(40),lastName varchar(40),adress varchar(100),phoneNumber varchar(100) UNIQUE, company varchar(100));");

        //sätter in data i ContactsList-tabellen
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Berit','Bengtson','Storgatan 9','0732471091','IKEA');");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Anders','Andersson','Lillgatan 137','0739922270','Siemens');");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Simon','Simonsson','Kungsgatan 29','0709842371','Sony');");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Fadime','Molin','Västra Torget 7','0729242531','Volvo');");

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList INNAN delete-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsList");


        System.out.println();
        System.out.println("innehåll i ContactsListBackup INNAN delete-statement");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsListBackup");

        //raderar kontakter från tabellen med kontakter
        //telefonnummer används som villkor!
        System.out.println();
        System.out.println("Radera: ");
        sqlLiteDBCommunicator.performSqlStatment("DELETE FROM ContactsList WHERE phoneNumber='0732471091';");
        sqlLiteDBCommunicator.performSqlStatment("DELETE FROM ContactsList WHERE phoneNumber='0709842371';");
        sqlLiteDBCommunicator.performSqlStatment("DELETE FROM ContactsList WHERE phoneNumber='0729242531';");

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList EFTER delete-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsList");

        //redovisa backup tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsListBackup EFTER delete-statement");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsListBackup");

        System.out.println("ångra radera * 2");
        sqlLiteDBCommunicator.undoDelete();
        sqlLiteDBCommunicator.undoDelete();

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList EFTER ångra: ");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsList");

        //redovisa backup tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsListBackup EFTER ångra");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsListBackup");
    }
}
