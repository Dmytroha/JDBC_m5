package org.example.entities;

public class PrintProjectPrices {

    private final String name;
    private final Long price;

    public PrintProjectPrices(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "name: " +name+ ", price: "+ price;
    }
}
