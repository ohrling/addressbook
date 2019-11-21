package dbWorker;

import gui.singletons.MessageContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Uppdaterar önskad kontakt utifrån id men hjälp av värden från en Map.
// Mapen används för att generera queryt
public class SQLUpdate extends SQLPerformer implements Update {

    @Override
    public void update(Map<String,String> searchValues) {
        StringBuilder updateCall = new StringBuilder("UPDATE ContactsList SET ");
        int id = Integer.parseInt(searchValues.get("id"));
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, String> entry :
                searchValues.entrySet()) {
            if(!entry.getKey().equals("id"))
                keys.add(entry.getKey());
        }
        if(keys.size() == 1) {
            updateCall.append(keys.get(0));
        } else {
            for (String key :
                keys) {
                updateCall.append(key);
                if(keys.indexOf(key) < keys.size() - 1) {
                    updateCall.append(" = ?").append(" , ");
                } else {
                    updateCall.append(" = ?");
                }
            }
        }
        updateCall.append(" WHERE id = ? ;");
        System.out.println(updateCall.toString());
        try {
            stmt = connection.prepareStatement(updateCall.toString());
            for (String key :
                    keys) {
                if(key.equals("isDeleted"))
                    stmt.setInt((keys.indexOf(key) + 1), Integer.parseInt(searchValues.get(key)));
                else
                    stmt.setString((keys.indexOf(key) + 1), searchValues.get(key));
            }
            stmt.setInt(keys.size() + 1, id);
            System.out.println(updateCall.toString());
            System.out.println("Antal uppdaterade rader: " + stmt.executeUpdate());
            MessageContainer.setRightLabelMessage(searchValues.get("firstname") + " är uppdaterad.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            MessageContainer.setRightLabelMessage("Kunde inte updatera kontakten i databasen.");
        }
    }
}
