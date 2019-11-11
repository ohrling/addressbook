package dbWorker;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLPerformer {
    boolean perform(PreparedStatement preparedStatement) throws SQLException;
}
