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

        //om det är ett deletestatement - sätt in anropad kontakt datan i backuptabellen
        if (sqlStatement.toUpperCase().contains("DELETE")){
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
    public void dispalyContentOfTable(String tableName){
        ResultSet rs;
        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM "+tableName+";");

            while (rs.next()) {
                //sqllite har inte primary key däremot ett unikt rowid-värde som man kan använda
                //RowId rowid = rs.getRowId("rowid");
                String fname = rs.getString("firstName");
                String lname = rs.getString("lastName");
                String adress = rs.getString("adress");
                String phone = rs.getString("phoneNumber");
                String company = rs.getString("company");
                System.out.println(fname+" "+lname+" "+adress+" "+phone+" "+company);
            }

        } catch (Exception e){
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
    public void insertPostIntoBackupTable(String phoneNumber){

        ResultSet rs;
        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ContactsList WHERE phoneNumber='"+phoneNumber+"';");

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
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}


