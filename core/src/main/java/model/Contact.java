package model;

import java.text.MessageFormat;

public class Contact {
    private int idNr;
    private String firstName, lastName, email, phoneNr, company;
    private boolean isDeleted;

    public Contact(int idNr, String firstName, String lastName, String email, String phoneNr, String company, boolean isDeleted) {
        this.idNr = idNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNr = phoneNr;
        this.company = company;
        this.isDeleted = isDeleted;
    }

    public int getIdNr() {
        return idNr;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public String getCompany() {
        return company;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String fullInfo() {
        return MessageFormat.format(
                "Namn: {0} {1}\n" +
                "Företag: {2} \n" +
                "Telefonnummer: {3} \n" +
                "E-mail: {4}",
                firstName,lastName,company,phoneNr,email);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1}", firstName, lastName);
    }
}
