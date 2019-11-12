package dbWorker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public interface StatementGenerator {
    Statement create(Connection connection, String firstName, String lastName, String email, String phoneNr, String company, boolean isDeleted) throws SQLException;
    Statement read(Connection connection, Map<String,String> searchValues) throws SQLException;
    Statement update(Connection connection, String firstName, String lastName, String email, String phoneNr, String company, boolean isDeleted);
    Statement delete(Connection connection, String phoneNr) throws SQLException;
}