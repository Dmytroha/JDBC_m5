package org.example.entities;

public class MaxSalaryWorker {
    String name;
    Long salary;
    public MaxSalaryWorker(String name, Long salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "name: " +name+ ", salary: "+ salary;
    }
}
