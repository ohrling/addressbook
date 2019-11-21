package dbWorker;

import gui.singletons.MessageContainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUndoLastDelete extends SQLPerformer implements UndoLast {
    @Override
    public boolean undo() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM ContactsList WHERE isDeleted = 1 ORDER BY lastUpdated DESC LIMIT 1;");
            int id = rs.getInt("id");
            return stmt.execute("UPDATE ContactsList SET isDeleted = 0 WHERE id = " + id + ";");
        } catch (SQLException e) {
            MessageContainer.setRightLabelMessage(e.getMessage());
            return false;
        }
    }
}
