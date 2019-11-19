package dbWorker;

import model.Contact;

import java.util.List;
import java.util.Map;

public interface Read extends SQL {
    List<Contact> read(Map<String, String> searchValues);
}
