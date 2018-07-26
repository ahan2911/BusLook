package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;

public class DirectionParser {

    private Document document;

    public DirectionParser(Document document) {
        this.document = document;
    }


    public BusesOnRoute getNumberOfRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        String status = document.getElementsByTagName("status")
                .item(0)
                .getTextContent();
        if (status.equalsIgnoreCase("OK")) {
            NodeList nodeList = document.getElementsByTagName("route");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = (Element) nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    routes.add(getRoutes(element));
                }
            }
        }
        return new BusesOnRoute(routes);
    }

    private Route getRoutes(Element element) {
        ArrayList<Steps> steps = new ArrayList<>();
        NodeList nodeList = element.getElementsByTagName("step");
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                Element element1 = (Element) element.getElementsByTagName("travel_mode").item(0);
                if (element1.getTextContent().equals("TRANSIT")) {
                    Steps steps1 = getSteps(element);
                    steps.add(steps1);
                }
            }
        }

        return new Route(steps);
    }

    private Steps getSteps(Element element) {
        Element element1 = (Element) element.getElementsByTagName("duration").item(0);
        Element element2 = (Element) element1.getElementsByTagName("text").item(0);
        String duration = element2.getTextContent();



        element1 = (Element) element.getElementsByTagName("distance").item(0);
        element2 = (Element) element1.getElementsByTagName("value").item(0);
        double distance = Double.parseDouble(element2.getTextContent());


        Element transit_detail = (Element) element.getElementsByTagName("transit_details").item(0);

        element1 = (Element) transit_detail.getElementsByTagName("departure_stop").item(0);
        element2 = (Element) element1.getElementsByTagName("name").item(0);
        String departureStop = element2.getTextContent();


        element1 = (Element) transit_detail.getElementsByTagName("arrival_stop").item(0);
        element2 = (Element) element1.getElementsByTagName("name").item(0);
        String arrivalStop = element2.getTextContent();

        element2 = (Element) element1.getElementsByTagName("lat").item(0);
        double lat = Double.parseDouble(element2.getTextContent());

        element2 = (Element) element1.getElementsByTagName("lng").item(0);
        double lang = Double.parseDouble(element2.getTextContent());

        element1 = (Element) transit_detail.getElementsByTagName("short_name").item(0);
        String busNumber = element1.getTextContent();

        element1 = (Element) transit_detail.getElementsByTagName("num_stops").item(0);
        String numberStop = element1.getTextContent();


        element1 = (Element) transit_detail.getElementsByTagName("departure_time").item(0);
        element2 = (Element) element1.getElementsByTagName("text").item(0);
        String departureTime = element2.getTextContent();


        element2 = (Element) transit_detail.getElementsByTagName("arrival_time").item(0);
        element1 = (Element) element2.getElementsByTagName("text").item(0);
        String arrivalTime = element1.getTextContent();

        if(busNumber.equalsIgnoreCase("Mini bus")){
            busNumber = "9B";
        }

        return new Steps(busNumber, arrivalStop, departureStop, duration, distance, numberStop, arrivalTime, departureTime, lat, lang);
    }
}

