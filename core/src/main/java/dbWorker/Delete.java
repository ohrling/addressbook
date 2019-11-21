package dbWorker;

import model.Contact;

interface Delete extends SQL {
    void delete(Contact contact);
}
