package model;

import dbWorker.DataBaseCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchPerformer {
    private Connection connection;

    public SearchPerformer() {
        DataBaseCreator localDatabase = new DataBaseCreator();
        if(localDatabase.createNewDatabase("addressbook.db")) {
            System.out.println("Databas-anslutning skapad!");
        } else {
            System.out.println("Databas existerar redan!");
        }
        connection = localDatabase.getConnection();
        localDatabase.templateCreator();
    }

    public List<Contact> getAllContacts() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM contactsList WHERE isDeleted = 0 ORDER BY id ASC;");
        ResultSet rs = ps.executeQuery();
        List<Contact> contacts = new ArrayList<>();

        while(rs.next()) {
            contacts.add(new Contact(
                    rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("phoneNr"),
                    rs.getString("company"),
                    rs.getByte("isDeleted") == 1
                    ));
        }
        return contacts;
    }

    public ArrayList<Contact> getFoundContacts(String firstName, String lastName, String email, String phoneNr, String company) {


        return null;
    }
}
