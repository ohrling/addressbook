package dbWorker;

import java.sql.*;

public class DataBaseSqlPerformer {
    private Statement stmt;

    //gör senaste raderingen ogjort
    public void undoLatestDelete() {
        ResultSet rs;
        try {
            //hämta senast ändrade post genom sortering efter tidsstämpel
            rs = stmt.executeQuery("SELECT * FROM ContactsList WHERE isDeleted = 1 ORDER BY lastUpdated DESC LIMIT 1;");
            String id = rs.getString("id");
            //Återställ isDeleted till 0 (false)
            stmt.execute("UPDATE ContactsList SET isDeleted = 0 WHERE id = "+id+";");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int delete() {
        //raderar kontakt från tabellen
        //OBS! phoneNumber används som argument för vilken rad i tabellen som ska raderas
        // Returnerar antalet poster som raderats
        try {
            return stmt.executeUpdate("UPDATE ContactsList SET isDeleted = 1 WHERE phoneNumber='0709842371';");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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


