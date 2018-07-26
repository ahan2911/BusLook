package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import java.util.ArrayList;

public class BusesOnRoute {
    private ArrayList<Route> routes;

    public BusesOnRoute(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public int getCount() {
        return routes.size();
    }


    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

}
