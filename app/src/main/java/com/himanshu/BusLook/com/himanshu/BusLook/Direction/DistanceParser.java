package com.himanshu.BusLook.com.himanshu.BusLook.Direction;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DistanceParser {
    private Document document;

    public DistanceParser(Document document) {
        this.document = document;
    }
    public String getDistance(){
        String distance = "NA";
        String status = document.getElementsByTagName("status")
                .item(0)
                .getTextContent();
        if (status.equalsIgnoreCase("OK")) {
            Element element = (Element) document.getElementsByTagName("element").item(0);
            if(element.getElementsByTagName("status").item(0).getTextContent().equalsIgnoreCase("OK")){
                Element element1 = (Element)element.getElementsByTagName("distance").item(0);
                element1 =(Element) element1.getElementsByTagName("text").item(0);
                distance = element1.getTextContent();

            }
        }
        return distance;
    }

    public long getDuration(){
        long duration = 0;
        String status = document.getElementsByTagName("status")
                .item(0)
                .getTextContent();
        if (status.equalsIgnoreCase("OK")) {
            Element element = (Element) document.getElementsByTagName("element").item(0);
            if(element.getElementsByTagName("status").item(0).getTextContent().equalsIgnoreCase("OK")){
                Element element1 = (Element)element.getElementsByTagName("duration").item(0);
                element1 =(Element) element1.getElementsByTagName("value").item(0);
                duration = Long.parseLong(element1.getTextContent());
            }
        }
        return duration;
    }
}
