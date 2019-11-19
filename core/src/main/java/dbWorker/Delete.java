package dbWorker;

import model.Contact;

public interface Delete extends SQL {
    String delete(Contact contact);
}
