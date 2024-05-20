package org.juanmariiaa.test;

import org.juanmariiaa.model.connection.ConnectionProperties;
import org.juanmariiaa.utils.XMLManager;

public class Load {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
