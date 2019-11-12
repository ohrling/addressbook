package dbWorker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLiteStatementGenerator implements StatementGenerator {
    private final String INSERT_INTO = "INSERT INTO ContactsList (firstName, lastName, email, phoneNumber, company, isDeleted) VALUES (?, ?, ?, ?, ?, ?);";
    private final String DELETE = "UPDATE ContactsList SET isDeleted = 1 WHERE phoneNumber = ?;";


    @Override
    public Statement create(Connection connection, String firstName, String lastName, String email, String phoneNr, String company, boolean isDeleted) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_INTO);
        ps.setString(1,firstName);
        ps.setString(2,lastName);
        ps.setString(3,email);
        ps.setString(4,phoneNr);
        ps.setString(5,company);
        ps.setInt(6,0);
        return ps;
    }

    @Override
    public Statement read(Connection connection, Map<String,String> searchValues) throws SQLException {
        StringBuilder sqlCall = new StringBuilder("SELECT * FROM ContactsList WHERE ");
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, String> entry :
                searchValues.entrySet()) {
            System.out.println(entry.getKey());
            keys.add(entry.getKey());
        }
        if(keys.size() == 1) {
            sqlCall.append(keys.get(0));
        } else {
            for (String key :
                    keys) {
                if(keys.indexOf(key) == 0) {
                    sqlCall.append(key).append(" = ?");
                } else {
                    sqlCall.append(" AND ").append(key);
                }
                if(key.indexOf(key) > keys.size()) {
                    sqlCall.append(" = ?");
                }
            }
        }
        sqlCall.append(" = ? ORDER BY lastUpdated DESC;");
        System.out.println(sqlCall);
        PreparedStatement ps = connection.prepareStatement(sqlCall.toString());
        for (String key :
                keys) {
            ps.setString((keys.indexOf(key) + 1), searchValues.get(key));
        }
        return ps;
    }

    @Override
    public Statement update(Connection connection, String firstName, String lastName, String email, String phoneNr, String company, boolean isDeleted) {
        return null;
    }

    @Override
    public Statement delete(Connection connection, String phoneNr) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE);
        ps.setString(1, phoneNr);
        return ps;
    }
}
