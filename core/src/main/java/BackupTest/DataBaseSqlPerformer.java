package BackupTest;

import java.sql.*;

public class DataBaseSqlPerformer {

    //returnerar anslutning till databasen
    private Connection connectToDatabase() {

        String url = "jdbc:sqlite:adressbook.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //utför sql kommando
    public void performSqlStatment(String sqlStatement) {

        //om det är ett deletestatement - sätt in raderad kontakt i backuptabellen
        if (sqlStatement.toUpperCase().contains("DELETE")) {
            addDeletedPostToBackup(sqlStatement);
        }

        //Utför sql statment
        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            stmt.execute(sqlStatement);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //visar innehåll av vald tabell
    public void dispalyContentOfTable(String tableName) {
        ResultSet rs;
        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");

            while (rs.next()) {

                String id = rs.getString("id");
                String fname = rs.getString("firstName");
                String lname = rs.getString("lastName");
                String adress = rs.getString("adress");
                String phone = rs.getString("phoneNumber");
                String company = rs.getString("company");
                System.out.println(id + " " + fname + " " + lname + " " + adress + " " + phone + " " + company);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void addDeletedPostToBackup(String sqlStatement) {

        String phoneNumber = extractPhoneNumber(sqlStatement);
        insertPostIntoBackupTable(phoneNumber);
    }

    private String extractPhoneNumber(String sqlStatement) {

        String splitUpDeleteStatement[] = sqlStatement.split("'", 4);
        String phoneNumber = splitUpDeleteStatement[1];

        return phoneNumber;
    }

    //skapar och utför det sql-statment som sätter in datan som ska raderas in i backup-tabellen
    public void insertPostIntoBackupTable(String phoneNumber) {

        ResultSet rs;
        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ContactsList WHERE phoneNumber='" + phoneNumber + "';");

            String insertToBbackupStatement = "INSERT INTO ContactsListBackup (firstName, lastName, adress, phoneNumber, company) VALUES (";


            while (rs.next()) {
                //RowId rowid = rs.getRowId("rowid");
                insertToBbackupStatement += "'";
                insertToBbackupStatement += rs.getString("firstName");
                insertToBbackupStatement += "',";

                insertToBbackupStatement += "'";
                insertToBbackupStatement += rs.getString("lastName");
                insertToBbackupStatement += "',";

                insertToBbackupStatement += "'";
                insertToBbackupStatement += rs.getString("adress");
                insertToBbackupStatement += "',";

                insertToBbackupStatement += "'";
                insertToBbackupStatement += rs.getString("phoneNumber");
                insertToBbackupStatement += "',";

                insertToBbackupStatement += "'";
                insertToBbackupStatement += rs.getString("company");
                insertToBbackupStatement += "');";
            }
            stmt.execute(insertToBbackupStatement);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //gör senaste raderingen ogjort
    public void undoDelete() {
        ResultSet rs;

        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();

            //hämta senast insatta post i backup
            rs = stmt.executeQuery("SELECT * FROM ContactsListBackup ORDER BY id DESC LIMIT 1;");
            //statement som fylls på används för att sätta in datan i ContactsList
            String reinsertToContactsTable = "INSERT INTO ContactsList(firstName, lastName, adress, phoneNumber, company) VALUES (";

            String idOfContactToDeleteFromBackupTable = rs.getString("id");

            reinsertToContactsTable += "'";
            reinsertToContactsTable += rs.getString("firstName");
            reinsertToContactsTable += "',";

            reinsertToContactsTable += "'";
            reinsertToContactsTable += rs.getString("lastName");
            reinsertToContactsTable += "',";

            reinsertToContactsTable += "'";
            reinsertToContactsTable += rs.getString("adress");
            reinsertToContactsTable += "',";

            reinsertToContactsTable += "'";
            reinsertToContactsTable += rs.getString("phoneNumber");
            reinsertToContactsTable += "',";

            reinsertToContactsTable += "'";
            reinsertToContactsTable += rs.getString("company");
            reinsertToContactsTable += "');";

            //när posten är överförd tillbaka till tabellen....
            stmt.execute(reinsertToContactsTable);
            //...så raderas den från backupen
            stmt.execute("DELETE FROM ContactsListBackup WHERE id=" + idOfContactToDeleteFromBackupTable + ";");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


