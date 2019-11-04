package BackupTest;

import java.sql.Connection;

public class BackupTestMain {

    //Här testar jag att skapa en sqlLite databas, den innehåller 2 tabeller ContactsList och ContactsListBackup
    // sqlLite har inte primary key funktionen, däremot har varje rad i databasen ett eget id per automatik som man kan använda
    //I denna version används kontaktlistans telefonnummer som en unik identifierare när poster som ska raderas kopieras över till backup-tabellen
    // det betyder att alla telefonnummer måste vara unika när de matas in i databasen (om man inte vill råka radera fler personer)

    public static void main(String[] args) {

        //DataBaseCreator skapar databasen lokalt
        DataBaseCreator localDatabase = new DataBaseCreator();
        localDatabase.createNewDatabase("adressbook.db");

        //  DataBaseSqlPerformer utför mottagna sql-kommandon i databasen
        DataBaseSqlPerformer sqlLiteDBCommunicator = new DataBaseSqlPerformer();

        //här skapas tabellerna ContactsList och ContactsListBackup
        sqlLiteDBCommunicator.performSqlStatment("CREATE TABLE IF NOT EXISTS ContactsList(firstName varchar(40),lastName varchar(40),adress varchar(100),phoneNumber varchar(100), company varchar(100));");
        sqlLiteDBCommunicator.performSqlStatment("CREATE TABLE IF NOT EXISTS ContactsListBackup(firstName varchar(40),lastName varchar(40),adress varchar(100),phoneNumber varchar(100), company varchar(100));");

        //sätter in data i ContactsList-tabellen
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Berit','Bengtson','Storgatan 9','0732471091','IKEA');");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Anders','Andersson','Lillgatan 137','0739922270','Siemens');");
        sqlLiteDBCommunicator.performSqlStatment("INSERT INTO ContactsList (firstName, lastName, adress, phoneNumber, company) VALUES ('Simon','Simonsson','Kungsgatan 29','0709842371','Sony');");

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList INNAN delete-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsList");


        System.out.println();
        System.out.println("innehåll i ContactsListBackup INNAN delete-statement");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsListBackup");

        //raderar en kontakt
        sqlLiteDBCommunicator.performSqlStatment("DELETE FROM ContactsList WHERE phoneNumber='0732471091';");

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList EFTER delete-statement: ");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsList");

        //redovisa backup tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsListBackup EFTER delete-statement");
        sqlLiteDBCommunicator.dispalyContentOfTable("ContactsListBackup");
    }
}
