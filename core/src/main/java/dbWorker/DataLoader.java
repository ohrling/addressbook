package dbWorker;

import model.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DataLoader {
    List<Contact> getData(Connection connection, Map<String, String> searchValues) throws SQLException;
}
