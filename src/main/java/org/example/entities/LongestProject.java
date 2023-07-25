package org.example.entities;

public class LongestProject {
    public LongestProject(long id, String name, int monthCount) {
        this.id = id;
        this.name = name;
        this.monthCount = monthCount;
    }

    private final long id;
    private final String name;
    private final int monthCount;

    @Override
    public String toString() {
        return "id: " + id +
                ", name: "+ this.name +
                ", monthCount:" + this.monthCount;
    }
}
