package dbWorker;

interface Create extends SQL {
    void create(String firstName, String lastName, String email, String phoneNr, String company);
}
