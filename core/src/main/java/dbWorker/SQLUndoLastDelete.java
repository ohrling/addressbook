package dbWorker;

import gui.singletons.MessageContainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUndoLastDelete extends SQLPerformer implements UndoLast {
    @Override
    public boolean undo() {
        try {
            stmt = connection.prepareStatement("SELECT * FROM ContactsList WHERE isDeleted = 1 ORDER BY lastUpdated DESC LIMIT 1;");
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt("id");
            stmt.close();
            // TODO: 2019-11-22 Lyckas inte få det att fungera utan att stänga stmt, någon annan idé?
            stmt = connection.prepareStatement("UPDATE ContactsList SET isDeleted = 0 WHERE id = " + id + ";");
            return stmt.execute();
        } catch (SQLException e) {
            MessageContainer.setRightLabelMessage(e.getMessage());
            return false;
        }
    }
}
