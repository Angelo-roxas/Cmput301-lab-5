package com.example.listycity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a collection of {@link City} objects.
 * <p>
 * The {@code CityList} class provides methods for managing a list of cities,
 * including adding, deleting, checking for existence, retrieving a sorted list,
 * and counting the total number of cities.
 * </p>
 */
public class CityList {

    /** Internal list that stores all {@link City} objects. */
    private final List<City> cities = new ArrayList<>();

    /**
     * Adds a new city to the list if it does not already exist.
     * <p>
     * If the city is already present in the list, this method throws an
     * {@link IllegalArgumentException} to prevent duplicates.
     * </p>
     *
     * @param city the {@link City} object to be added
     * @throws IllegalArgumentException if the specified city already exists in the list
     */
    public void add(City city) {
        if (cities.contains(city)) {
            throw new IllegalArgumentException("City already exists in the list.");
        }
        cities.add(city);
    }

    /**
     * Returns a sorted copy of the city list.
     * <p>
     * The list is sorted alphabetically by city name, and if two cities have the same
     * name, they are further sorted by province name (as defined in {@link City#compareTo(City)}).
     * The returned list is a shallow copy; modifications to it will not affect the internal list.
     * </p>
     *
     * @return a sorted {@link List} of {@link City} objects
     */
    public List<City> getCities() {
        List<City> list = new ArrayList<>(cities); // copy to avoid altering the internal list
        Collections.sort(list);
        return list;
    }

    /**
     * Checks whether a given city exists in the list.
     *
     * @param city the {@link City} object to check for
     * @return {@code true} if the city exists in the list; {@code false} otherwise
     */
    public boolean hasCity(City city) {
        return cities.contains(city);
    }

    /**
     * Deletes a city from the list if it exists.
     * <p>
     * If the city is not found in the list, this method throws an
     * {@link IllegalArgumentException}.
     * </p>
     *
     * @param city the {@link City} object to remove from the list
     * @throws IllegalArgumentException if the specified city does not exist in the list
     */
    public void delete(City city) {
        if (!cities.contains(city)) {
            throw new IllegalArgumentException("City not found in the list.");
        }
        cities.remove(city);
    }

    /**
     * Returns the total number of cities currently in the list.
     *
     * @return the number of {@link City} objects in the list
     */
    public int countCities() {
        return cities.size();
    }
}
