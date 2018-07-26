package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {
    private ArrayList<Steps> steps;

    Route(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public int getCount() {
        return steps.size();
    }

}
