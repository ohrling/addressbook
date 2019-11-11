package dbWorker;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreatePerformer implements SQLPerformer {
    @Override
    public boolean perform(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.execute();
    }
}
