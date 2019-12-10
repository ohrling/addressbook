package core.main;

import com.github.javafaker.Faker;
import dbWorker.SQLCreate;

public class AddDummyContacts {
    public static void main(String[] args) {
        addSampleContacts();
    }

    private static void addSampleContacts()  {

        SQLCreate creator = new SQLCreate();
        for (int i = 0; i<100; i++ ){
            creator.init();
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            String company = faker.company().name();

            creator.create(firstName, lastName, email, phoneNumber, company);
            creator.closeCon();
        }
    }
}
