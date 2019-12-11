package dbWorker;

import model.Contact;

import java.sql.SQLException;

public class SQLDelete extends SQLPerformer implements Delete {

    public void delete(Contact contact) {
        try {
            stmt = connection.prepareStatement("UPDATE ContactsList SET isDeleted = 1 WHERE id = ?;");
            stmt.setInt(1, contact.getIdNr());
            if (stmt.execute())
                System.out.println("Kontakten togs bort");
            else
                System.out.println("Kontakten kunde inte tas bort");
        } catch (SQLException e) {
            System.out.println("Kunde inte ta bort kontakten");
            System.out.println(e.getMessage());
        }
    }
}
