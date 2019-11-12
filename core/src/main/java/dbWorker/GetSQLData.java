package dbWorker;

import model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetSQLData implements DataLoader {
    private StatementGenerator statementGenerator = new SQLiteStatementGenerator();
    @Override
    public List<Contact> getData(Connection connection, Map<String, String> searchValues) throws SQLException {
        PreparedStatement statement;
        if(searchValues == null || searchValues.isEmpty()) {
            statement = connection.prepareStatement("SELECT * FROM ContactsList;");
        } else {
            System.out.println(searchValues + " i GetSQLData");
            statement = (PreparedStatement) statementGenerator.read(connection, searchValues);
        }
        ResultSet rs = statement.executeQuery();
        List<Contact> contacts = new ArrayList<>();
        while (rs.next()) {
            boolean isDeleted = rs.getByte("isDeleted") == 1;
            contacts.add(new Contact(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("company"),
                    isDeleted));
        }
        System.out.println(contacts);
        return contacts;
    }
}
