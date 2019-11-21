package dbWorker;

import gui.singletons.MessageContainer;
import model.Contact;

import java.sql.SQLException;

public class SQLDelete extends SQLPerformer implements Delete{

    public void delete(Contact contact) {
        try {
            stmt = connection.prepareStatement("UPDATE ContactsList SET isDeleted = 1 WHERE id = ?;");
            stmt.setInt(1, contact.getIdNr());
            if(stmt.execute())
                MessageContainer.setRightLabelMessage("Kontakten togs bort");
            else
                MessageContainer.setRightLabelMessage("Kontakten kunde inte tas bort");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            MessageContainer.setRightLabelMessage("Kunde inte ta bort kontakten");
        }
    }
}
