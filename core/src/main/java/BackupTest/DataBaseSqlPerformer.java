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

        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            stmt.execute(sqlStatement);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //visar innehåll av vald tabell
    public void dispalyContentOfContactsList() {
        ResultSet rs;
        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            //Visa bara poster med isDeleted = 0 dvs inte raderade
            rs = stmt.executeQuery("SELECT * FROM contactsList WHERE isDeleted = 0 ORDER BY id ASC;");

            while (rs.next()) {

                String id = rs.getString("id");
                String fname = rs.getString("firstName");
                String lname = rs.getString("lastName");
                String adress = rs.getString("adress");
                String phone = rs.getString("phoneNumber");
                String company = rs.getString("company");
                String isDeleted = rs.getString("isDeleted");
                String lastUpdated = rs.getString("lastUpdated");
                System.out.println(id + " " + fname + " " + lname + " " + adress + " " + phone + " " + company + " "+ isDeleted+ " "+ lastUpdated);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //gör senaste raderingen ogjort
    public void undoLatestDelete() {
        ResultSet rs;

        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            //hämta senast ändrade post genom sortering efter tidsstämpel
            rs = stmt.executeQuery("SELECT * FROM ContactsList WHERE isDeleted = 1 ORDER BY lastUpdated DESC LIMIT 1;");
            String id = rs.getString("id");
            //Återställ isDeleted till 0 (false)
            stmt.execute("UPDATE ContactsList SET isDeleted = 0 WHERE id = "+id+";");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    //gör om alla raderingar
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Denna metoden har en bug, den ska återställa alla raderade men återställer bara en, ska felsöka sen
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public void undoAllDeletes() {
        ResultSet rs;

        try {
            Connection con = this.connectToDatabase();
            Statement stmt = con.createStatement();
            //hämta senast ändrade post genom sortering efter tidsstämpel

            rs = stmt.executeQuery("SELECT * FROM ContactsList WHERE isDeleted = 1 ORDER BY lastUpdated DESC LIMIT 1;");

            while (rs.next()){

                String id = rs.getString("id");
                System.out.println(id);
                //Återställ isDeleted till 0 (false)
                stmt.execute("UPDATE ContactsList SET isDeleted = 0 WHERE id = "+id+";");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } */
}


