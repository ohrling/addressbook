package dbWorker;

import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLRead extends SQLPerformer implements Read {
    private List<Contact> contacts = null;

    @Override
    public List<Contact> read(Map<String,String> searchValues) {
        // Om searchValues är null så returneras alla kontakter som inte är raderade
        if(searchValues == null || searchValues.isEmpty()){
            try {
                stmt = connection.prepareStatement("SELECT * FROM ContactsList WHERE isDeleted = 0;");
                generateContactList(stmt.executeQuery());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // Om det finns sökvärden så byggs en query av dessa som sedan exvekeras
            StringBuilder sqlCall = new StringBuilder("SELECT * FROM ContactsList WHERE ");
            List<String> keys = new ArrayList<>();
            for (Map.Entry<String, String> entry :
                    searchValues.entrySet()) {
                keys.add(entry.getKey());
            }
            if (keys.size() == 1) {
                sqlCall.append(keys.get(0));
            } else {
                for (String key :
                        keys) {
                    if (keys.indexOf(key) == 0) {
                        sqlCall.append(key).append(" = ?");
                    } else {
                        sqlCall.append(" , ").append(key);
                    }
                    if (key.indexOf(key) > keys.size()) {
                        sqlCall.append(" = ?");
                    }
                }
            }
            sqlCall.append(" = ? ORDER BY lastUpdated DESC;");
            try {
                stmt = connection.prepareStatement(sqlCall.toString());
                for (String key :
                        keys) {
                    stmt.setString((keys.indexOf(key) + 1), searchValues.get(key));
                }
                generateContactList(stmt.executeQuery());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return contacts;
    }

    // Genererar listan som returneras
    private void generateContactList(ResultSet rs) throws SQLException {
        contacts = new ArrayList<>();
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
    }
}
