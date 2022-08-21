package com.raiseup.javaSpringWebService.data.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
public class AddressEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String address_id;
    @Column(length = 50,nullable = false)
    private String country;
    @Column(length = 50,nullable = false)
    private String city;
    @Column(length = 50,nullable = false)
    private String streetName;
    @Column(length = 8,nullable = true)
    private String postalCode;
    @Column(nullable = false,length = 15)
    private String type;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
