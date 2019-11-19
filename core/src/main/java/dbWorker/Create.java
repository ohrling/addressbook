package dbWorker;

public interface Create extends SQL {
    String create(String firstName, String lastName, String email, String phoneNr, String company);
}
