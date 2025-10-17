package com.example.listycity;


/**
 * This is a class that defines a city
 * it has two attributes: city (string) and province (string)
 * it has two functions:
 * 1) getCityName() - returns the String city associated with City object.
 * 2) getProvinceName() - returns the String province associated with City object
 */


public class City implements Comparable<City> {
    private String city;
    private String province;
    City(String city, String province){
        this.city = city;
        this.province = province;
    }
    String getCityName(){
        return this.city;
    }
    String getProvinceName(){
        return this.province;
    }



    /**
     * Compares this city to another city for order.
     * <p>
     * Cities are first compared alphabetically by their city name.
     * If the names are the same, they are compared by province name.
     * </p>
     *
     * @param o the {@code City} to be compared
     * @return a negative integer, zero, or a positive integer as this city
     *         is less than, equal to, or greater than the specified city
     */
    @Override
    public int compareTo(City o) {
        // Sort alphabetically by city name, then by province
        int cityCompare = this.city.compareTo(o.getCityName());
        if (cityCompare == 0) {
            return this.province.compareTo(o.getProvinceName());
        }
        return cityCompare;
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two {@code City} objects are considered equal if they have the same city
     * name and province name.
     * </p>
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument;
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        City cityObj = (City) obj;
        return this.city.equals(cityObj.getCityName()) &&
                this.province.equals(cityObj.getProvinceName());
    }

    /**
     * Returns a hash code value for the city.
     * <p>
     * The hash code is computed based on both the city name and province name.
     * </p>
     *
     * @return a hash code value for this city
     */
    @Override
    public int hashCode() {
        return this.city.hashCode() + this.province.hashCode();
    }


    /**
     * Returns a string representation of this city.
     * <p>
     * The format is "City, Province".
     * </p>
     *
     * @return a string representation of the city
     */
    @Override
    public String toString() {
        return city + ", " + province;
    }
}


