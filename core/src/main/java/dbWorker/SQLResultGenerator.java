package dbWorker;

import model.Contact;

import java.sql.SQLException;
import java.util.List;

public interface SQLResultGenerator extends SQLPerformer {
    List<Contact> getResult() throws SQLException;
}
