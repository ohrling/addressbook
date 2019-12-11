package dbWorker;

import java.util.Map;

interface Update extends SQL {
    void update(Map<String,String> searchValues);
}
