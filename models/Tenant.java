package models;

import utils.TimeHelper;

public class Tenant {
    private String name;
    private String category;
    private int startTime;
    private int endTime;
    private static int counter = 1;
    private int id;

    public Tenant(String name, String category, int startTime, int endTime) {
        this.id = counter++;
        this.name = name;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("%-20s | %-15s | %3d | %3d",
            name, category, TimeHelper.format(startTime), TimeHelper.format(endTime)
        );
    }
}