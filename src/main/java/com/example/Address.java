package com.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amarendra on 25/1/17.
 */
@Document
public class Address {

    @Id
    private String id;

    private List<String> houseNumber;

    private String city;

    public Address() {
        this.houseNumber = new ArrayList<>();
    }

    public Address(String city) {
        this.houseNumber = new ArrayList<>();
        this.city = city;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(List<String> houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void addHouseNumber(String houseNumber) {
        this.houseNumber.add(houseNumber);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", houseNumber=" + houseNumber +
                ", city='" + city + '\'' +
                '}';
    }
}
