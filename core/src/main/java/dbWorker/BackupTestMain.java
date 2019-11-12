package dbWorker;

public class BackupTestMain {

/*

    I main nedan skapas en databas med en tabell som är en kontaktlista för personuppgifter.

    Kolumnen isDeleted i tabellen kan vara 0 (false) eller 1 (true). Dvs den kolumnen avgör om kontakte ska vara "raderad" eller inte

     Kontaktlistans telefonnummer är en unik identifierare för poster som
    ska raderas. Därför bör telefonnummer användas som villkor när man ska radera en post i tabellen.
    Det betyder också att alla telefonnummer måste vara unika när de matas in i databasen.
    Poster som införs i tabellen som inte har unikt telefonnummer kommer inte att registreras*/

    public static void temp (){

        //DataBaseCreator skapar "in memory" databasen sqlLite
        DataBaseConnector localDatabase = new DataBaseConnector();
        //localDatabase.createNewDatabase("addressbook.db");

        //  DataBaseSqlPerformers uppgift är att utföra mottagna sql-kommandon i databasen
        DataBaseSqlPerformer sqlLiteDBCommunicator = new DataBaseSqlPerformer();

        //redovisa tabellens innehåll
        System.out.println();
        System.out.println("innehåll i ContactsList INNAN delete-statement: ");


        //sqlLiteDBCommunicator.dispalyContentOfContactsList();




        System.out.println();
        System.out.println("innehåll i ContactsList EFTER delete-statement: ");


        //sqlLiteDBCommunicator.dispalyContentOfContactsList();

        //ångra senast radering
        sqlLiteDBCommunicator.undoLatestDelete();

        System.out.println();
        System.out.println("innehåll i ContactsList EFTER ångra-statement: ");


        //sqlLiteDBCommunicator.dispalyContentOfContactsList();



    }
}
