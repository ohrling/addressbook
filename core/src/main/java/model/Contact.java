package model;

import java.text.MessageFormat;

public class Contact {
    private final int idNr;
    private String firstName, lastName, email, phoneNumber, company;
    private Boolean isDeleted;

    public Contact(int idNr, String firstName, String lastName, String email, String phoneNumber, String company, Boolean isDeleted) {
        this.idNr = idNr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String fullInfo() {
        return MessageFormat.format(
                "Namn: {0} {1}\n" +
                "Företag: {2} \n" +
                "Telefonnummer: {3} \n" +
                "E-mail: {4}",
                firstName,lastName,company, phoneNumber,email);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1}", firstName, lastName);
    }
}
