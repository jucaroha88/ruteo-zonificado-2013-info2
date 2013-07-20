/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.viewer;

import aimax.osm.viewer.DefaultEntityRenderer;
import edu.uca.info2.components.Zone;
import edu.uca.info2.map.ZAMap;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 *
 * @author Toshiba
 */
public class ZAEntityRenderer extends DefaultEntityRenderer {
    @Override
    public void printBufferedObjects(){
        super.printBufferedObjects();
        ZAMap map = (ZAMap) wnProvider;
        for(Zone zone : map.getZones()){
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(transformer.x(zone.getLon1()),
                        transformer.y(zone.getLat1()),
                        transformer.x(zone.getLon2()),
                        transformer.y(zone.getLat1()));
            g2.drawLine(transformer.x(zone.getLon2()),
                        transformer.y(zone.getLat1()),
                        transformer.x(zone.getLon2()),
                        transformer.y(zone.getLat2()));
            g2.drawLine(transformer.x(zone.getLon2()),
                        transformer.y(zone.getLat2()),
                        transformer.x(zone.getLon1()),
                        transformer.y(zone.getLat2()));
            g2.drawLine(transformer.x(zone.getLon1()),
                        transformer.y(zone.getLat2()),
                        transformer.x(zone.getLon1()),
                        transformer.y(zone.getLat1()));
            System.out.println(zone.getZoneId()+" Lat: "+zone.getLat1()+" Lon: "+zone.getLon1());
        }
    }
}
