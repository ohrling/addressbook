package dbWorker;

import java.sql.SQLException;

public class SQLCreate extends SQLPerformer implements Create {
    private final String INSERT_INTO = "INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES (?, ?, ?, ?, ?, ?);";

    public String create(String firstName, String lastName, String email, String phoneNr, String company) {
        stmt = null;
        try {
            stmt = connection.prepareStatement(INSERT_INTO);
            stmt.setString(1,firstName);
            stmt.setString(2,lastName);
            stmt.setString(3,email);
            stmt.setString(4,phoneNr);
            stmt.setString(5,company);
            stmt.setInt(6,0);
            if(stmt.execute())
                return String.format("%s %s är sparad.",firstName,lastName);
            else
                return String.format("%s %s kunde inte sparas.",firstName,lastName);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Fel vid sparande av kontakt.";
        }
    }
}
