package com.raiseup.javaSpringWebService.ui.model.request;


import java.util.ArrayList;
import java.util.List;

public class UserDetailsRequestModel {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    private List<AddressRequestModel> addresses= new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public List<AddressRequestModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequestModel> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "UserDetailsRequestModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
