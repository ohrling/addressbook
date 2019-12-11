package dbWorker;

import java.sql.SQLException;

// Skapar en ny rad i databasen, eller återtar raderandet om telefonnumret existerar sedan tidigare
public class SQLCreate extends SQLPerformer implements Create {

    public void create(String firstName, String lastName, String email, String phoneNr, String company) {
        stmt = null;
        try {
            String INSERT_INTO = "INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES (?, ?, ?, ?, ?, ?);";
            stmt = connection.prepareStatement(INSERT_INTO);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phoneNr);
            stmt.setString(5, company);
            stmt.setInt(6, 0);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Fel vid sparande av kontakt.");
            System.out.println(e.getMessage());
        }

    }
}
