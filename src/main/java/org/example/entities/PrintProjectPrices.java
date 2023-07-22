package org.example.entities;

public class PrintProjectPrices {

    String name;
    Long price;

    public PrintProjectPrices(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "name: " +name+ ", price: "+ price;
    }
}
