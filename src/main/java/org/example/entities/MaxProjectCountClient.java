package org.example.entities;

public class MaxProjectCountClient {
    private String name;
    private int projectCount;
    public MaxProjectCountClient(String name, int projectCount) {
        this.name = name;
        this.projectCount = projectCount;
    }

    @Override
    public String toString() {

        return " name: "+ name +", projectCount: "+ projectCount ;
    }
}
