package org.juanmariiaa.test;

import org.juanmariiaa.model.connection.ConnectionProperties;
import org.juanmariiaa.utils.XMLManager;

public class Connect {
    public static void main(String[] args) {
        ConnectionProperties co = new ConnectionProperties("localhost","3306","debate_tournament_manager","root","root");
        XMLManager.writeXML(co,"connection.xml");

        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
