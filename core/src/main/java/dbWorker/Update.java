package dbWorker;

import java.util.Map;

public interface Update extends SQL {
    String update(Map<String,String> searchValues);
}
