package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Parser {

    private ArrayList<BusStopList> busStops = new ArrayList<>();
    private Document document;

    public Parser(Document document){
        this.document = document;
    }



    public ArrayList<BusStopList> getBusStop(){

        String status = document.getElementsByTagName("status")
                .item(0)
                .getTextContent();
        if(status.equalsIgnoreCase("OK")){
            NodeList nodeList = document.getElementsByTagName("result");
            for(int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String busStop = element.getElementsByTagName("name")
                            .item(0)
                            .getTextContent();
                    String placeId = element.getElementsByTagName("place_id")
                            .item(0)
                            .getTextContent();
                    if (!checkExistence(busStop)) {
                        busStops.add(new BusStopList(placeId, busStop));
                    }
                }
            }
            return busStops;
        }
        else{
            return null;
        }
    }

    private boolean checkExistence(String busStop) {
        boolean b = false;
        for (int i = 0; i<busStops.size(); i++){
            if(busStop.contains(busStops.get(i).getBusStopName())){
                b = true;
            }
        }
        return b;
    }

}
