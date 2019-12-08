package dbWorker;

import core.singletons.ContactArrayContainer;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLRead extends SQLPerformer implements Read {
    private List<Contact> contacts = new ArrayList<>();

    @Override
    public void read(String searchValues) {
        // Om searchValues är null så returneras alla kontakter som inte är raderade sorterat i efternamnets bokstavsordning
        if (searchValues == null || searchValues.isEmpty()) {
            try {
                stmt = connection.prepareStatement("SELECT * FROM ContactsList WHERE isDeleted = 0 ORDER BY lastName COLLATE NOCASE ASC;");
                generateContactList(stmt.executeQuery());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // Om det finns sökvärde så byggs en query av detta som sedan exvekeras
            StringBuilder sqlCall = new StringBuilder("SELECT * FROM ContactsList WHERE isDeleted = 0 AND (");

            // om ett eller flera sökvärde dela upp söksträngen i substrings
            String[] searchValuesDivided;
            String delimeter = " ";
            searchValuesDivided = searchValues.trim().split(delimeter);

            //antalet sökord
            int numOfSearchValues = searchValuesDivided.length;

            // om användaren enbart angivet ett sökvärde
            if (numOfSearchValues == 1) {
                //...sök på det värdet
                String searchValue = "'%" + searchValuesDivided[0] + "%'";

                sqlCall.append("lastName LIKE ")
                        .append(searchValue)
                        .append(" OR firstName LIKE ")
                        .append(searchValue)
                        .append(" OR company LIKE ")
                        .append(searchValue)
                        .append(" OR email LIKE ")
                        .append(searchValue)
                        .append(" OR phoneNumber LIKE ")
                        .append(searchValue);

            } else { // Om användaren sökt på flera värden adderas varje sökvärde till query i for loopen
                for (int i = 0; i < numOfSearchValues; i++) {
                    String searchValue = "'%" + searchValuesDivided[i] + "%'";
                    //den sista ska avslutas med ;
                    sqlCall.append("lastName LIKE ")
                            .append(searchValue)
                            .append(" OR firstName LIKE ")
                            .append(searchValue)
                            .append(" OR company LIKE ")
                            .append(searchValue)
                            .append(" OR email LIKE ")
                            .append(searchValue)
                            .append(" OR phoneNumber LIKE ")
                            .append(searchValue);
                    if (i == numOfSearchValues - 1) {
                        System.out.println(i + " : " + sqlCall);
                    } else {
                        sqlCall.append(") AND (");
                        System.out.println(i + " : " + sqlCall);
                    }
                }
            }

            sqlCall.append(") ORDER BY lastUpdated DESC;");
            System.out.println(sqlCall.toString());
            try {
                stmt = connection.prepareStatement(sqlCall.toString());
                generateContactList(stmt.executeQuery());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        ContactArrayContainer.setContactsList(contacts);
    }

    // Genererar listan som returneras
    private void generateContactList(ResultSet rs) throws SQLException {
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

    public void readOrderedByFirstName() {
        try {
            stmt = connection.prepareStatement("SELECT * FROM ContactsList WHERE isDeleted = 0 ORDER BY firstName COLLATE NOCASE ASC;");
            generateContactList(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ContactArrayContainer.setContactsList(contacts);
    }

    public void readOrderedByCompany() {
        try {
            stmt = connection.prepareStatement("SELECT * FROM ContactsList WHERE isDeleted = 0 ORDER BY company COLLATE NOCASE ASC;");
            generateContactList(stmt.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ContactArrayContainer.setContactsList(contacts);
    }



    public void genericDatabaseSearch(String searchValues) {

        //om inget sökvärde, hämta allt
        if (searchValues == null || searchValues.isEmpty()) {
            try {
                stmt = connection.prepareStatement("SELECT * FROM ContactsList WHERE isDeleted = 0 ORDER BY lastName COLLATE NOCASE ASC;");
                generateContactList(stmt.executeQuery());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // om ett eller flera sökvärde dela upp söksträngen i substrings
            String[] searchValuesDivided;
            String delimeter = " ";
            searchValuesDivided = searchValues.trim().split(delimeter);

            //antalet sökord
            int numOfSearchValues = searchValuesDivided.length;

            //påbörja på query
            String sqlString = "SELECT * FROM ContactsList WHERE ";

            // om användaren enbart angivet ett sökvärde
            if (numOfSearchValues == 1) {
                //...sök på det värdet
                String searchValue = "'%" + searchValuesDivided[0] + "%'";
                sqlString += "lastName LIKE " + searchValue + " OR firstName LIKE " + searchValue + " OR company LIKE " + searchValue + " OR phoneNumber LIKE " + searchValue + " ";
            } else { // Om användaren sökt på flera värden adderas varje sökvärde till query i for loopen
                for (int i = 0; i < numOfSearchValues; i++) {
                    if (i == numOfSearchValues - 1) {
                        //den sista ska avslutas med ;
                        String searchValue = "'%" + searchValuesDivided[i] + "%'";
                        sqlString += "(lastName LIKE " + searchValue + " OR firstName LIKE " + searchValue + " OR company LIKE " + searchValue + " OR email LIKE " + searchValue + " OR phoneNumber LIKE " + searchValue + ")";
                        sqlString += ";";
                        System.out.println(i + " : " + sqlString);
                    } else {
                        String searchValue = "'%" + searchValuesDivided[i] + "%'";
                        sqlString += "(lastName LIKE " + searchValue + " OR firstName LIKE " + searchValue + " OR company LIKE " + searchValue + " OR email LIKE " + searchValue + " OR phoneNumber LIKE " + searchValue + ")";
                        sqlString += " AND ";
                        System.out.println(i + " : " + sqlString);
                    }
                }
            }
            sqlString += "AND isDeleted = 0 ORDER BY lastName;";
            System.out.println(sqlString);
            try {
                stmt = connection.prepareStatement(sqlString);
                generateContactList(stmt.executeQuery());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            ContactArrayContainer.setContactsList(contacts);
        }
    }
}
