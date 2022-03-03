package com.snm.asignment;

public class Line {
    private int id;
    private static int count = 0;
    private String state;

    Line(){
        id = count;
        count++;
         this.state = "idle";
    }

    public int getId() {
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Line ID: " + id + ", in state: " + state;
    }
}
