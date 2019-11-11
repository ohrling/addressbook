package dbWorker;

import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadPerformer implements SQLResultGenerator {
    private ResultSet rs = null;
    @Override
    public boolean perform(PreparedStatement preparedStatement) {
        try {
            //Visa bara poster med isDeleted = 0 dvs inte raderade
            rs = preparedStatement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Contact> getResult() throws SQLException {
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
        return contacts;
    }
}
