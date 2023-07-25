package org.example.entities;

public class MaxProjectCountClient {
    private final String name;
    private final int projectCount;
    public MaxProjectCountClient(String name, int projectCount) {
        this.name = name;
        this.projectCount = projectCount;
    }

    @Override
    public String toString() {

        return " name: "+ name +", projectCount: "+ projectCount ;
    }
}
