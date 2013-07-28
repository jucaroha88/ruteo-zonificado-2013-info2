/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.viewer;

import aimax.osm.data.Position;
import aimax.osm.data.entities.MapNode;
import aimax.osm.viewer.DefaultEntityRenderer;
import edu.uca.info2.components.Area;
import edu.uca.info2.components.Zone;
import edu.uca.info2.map.ZAMap;
import edu.uca.info2.util.CartUtils;
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
        }
        
        for(Area area : map.getAreas()){
            //dibujar circulo del area
            g2.setColor(Color.BLUE);
            float radiusInDegrees = CartUtils.kmToDegreesAprox(area.getRadius());
            g2.drawOval(transformer.x(area.getLon()),
                        transformer.y(area.getLat()),
                        Math.round(2*radiusInDegrees*transformer.getDotsPerDeg()),
                        Math.round(2*radiusInDegrees*transformer.getDotsPerDeg()));
            //dibujar nodos del area
            g2.setColor(Color.MAGENTA);
            for(MapNode nodo : area.getNodos()){
                Position pos = new Position(nodo);
                g2.drawOval(transformer.x(pos.getLon()), transformer.y(pos.getLat()), 3, 3);
            }
        }
    }
}
