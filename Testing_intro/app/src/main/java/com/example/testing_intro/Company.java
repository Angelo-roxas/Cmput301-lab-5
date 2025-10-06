package com.example.testing_intro;

import java.util.ArrayList;
import java.util.List;

public class Company {
    public List<Renter> renters = new ArrayList<>();
    public List<Car> cars = new ArrayList<>();
    public List<Rental> rentals = new ArrayList<>();



    public void add (Renter aRenter){
        renters.add(aRenter);
    }

    public void add (Car aCar){
        cars.add(aCar);
    }

    public void add (Rental aRental){
        rentals.add(aRental);
    }






}
