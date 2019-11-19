package dbWorker;

import model.Contact;

import java.sql.SQLException;

public class SQLDelete extends SQLPerformer implements Delete{
    private final String DELETE = "UPDATE ContactsList SET isDeleted = 1 WHERE id = ?;";

    public String delete(Contact contact) {
        try {
            stmt = connection.prepareStatement(DELETE);
            stmt.setInt(1, contact.getIdNr());
            if(stmt.execute())
                return "Kontakten togs bort";
            else
                return "Kontakten kunde inte tas bort";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Kunde inte ta bort kontakten";
        }
    }
}
