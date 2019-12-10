package dbWorker;

import core.singletons.ContactArrayContainer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// Skapar en ny rad i databasen, eller återtar raderandet om telefonnumret existerar sedan tidigare
public class SQLCreate extends SQLPerformer implements Create {

    public void create(String firstName, String lastName, String email, String phoneNr, String company) {
        // Check if phonenr exists in database
        SQLRead read = new SQLRead();
        Map<String, String> check = new HashMap<>();
        check.put("phoneNumber", phoneNr);
        read.read(phoneNr);
        read.closeCon();
        if(ContactArrayContainer.getContacts().isEmpty()) {
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
        } else {
            SQLUpdate update = new SQLUpdate();
            check.put("id", String.valueOf(ContactArrayContainer.getContacts().get(0).getIdNr()));
            check.put("firstName", firstName);
            check.put("lastName", lastName);
            check.put("email", email);
            check.put("company", company);
            check.put("isDeleted", "0");
            update.update(check);
            update.closeCon();
        }
    }
}
