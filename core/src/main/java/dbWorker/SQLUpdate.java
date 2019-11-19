package dbWorker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLUpdate extends SQLPerformer implements Update {
    public static final String UPDATE = "UPDATE ContactsList SET isDeleted = 1 WHERE phoneNumber='0709842371'";

    @Override
    public String update(Map<String,String> searchValues) {
        StringBuilder updateCall = new StringBuilder("UPDATE ContactsList SET ");
        int id = -1;
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, String> entry :
                searchValues.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            if(entry.getKey().equals("id"))
                id = searchValues.get(id).codePointCount(0,searchValues.get("id").length());
            else
                keys.add(entry.getKey());
        }
        if(keys.size() == 1) {
            updateCall.append(keys.get(0));
        } else {
            for (String key :
                keys) {
                if (keys.indexOf(key) == 0) {
                    updateCall.append(key).append(" = ?");
                } else {
                    updateCall.append(" AND ").append(key);
                }
                if (keys.indexOf(key) > keys.size()) {
                    updateCall.append(" = ?");
                }
            }
        }
        updateCall.append(" WHERE id = ? ;");
        try {
            stmt = connection.prepareStatement(updateCall.toString());
            for (String key :
                    keys) {
                stmt.setString((keys.indexOf(key) + 1), searchValues.get(key));
            }
            stmt.setInt(keys.size() + 1, id);
            System.out.println(updateCall.toString());
            return String.format("%s är uppdaterad.",searchValues.get("firstname"));
        } catch (SQLException e) {
            e.printStackTrace();
            return "Kunde inte updatera kontakten i databasen.";
        }
    }
}
