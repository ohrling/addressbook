package dbWorker;

import java.util.Map;

interface Read extends SQL {
    void read(Map<String, String> searchValues);

}
