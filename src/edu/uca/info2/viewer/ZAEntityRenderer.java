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
            
            //dibujar puntitos de referencia de las zonas
            long nodesids[] = { zone.getX1(), zone.getX2(), zone.getY1(), zone.getY2()};
            for(long nodeid : nodesids){
                Position pos = new Position(map.getNode(nodeid));
                drawXcita(transformer.x(pos.getLon()), transformer.y(pos.getLat()), 3);
                //System.out.println("Lat: "+pos.getLat()+"Lon: "+pos.getLon());
            }
            
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
            int radiusInDots = Math.round(radiusInDegrees*transformer.getDotsPerDeg());
            g2.drawOval(transformer.x(area.getLon()) - radiusInDots,
                        transformer.y(area.getLat()) - radiusInDots,
                        2*radiusInDots,
                        2*radiusInDots);
            //dibujar nodos del area
            g2.setColor(Color.MAGENTA);
            for(MapNode nodo : area.getNodos()){
                Position pos = new Position(nodo);
                g2.drawOval(transformer.x(pos.getLon()), transformer.y(pos.getLat()), 3, 3);
            }
        }
    }
    
    /* dibuja una "X" chiquita, usada para marcar los limites de los rectangulos */
    private void drawXcita(int x, int y, int width){
        g2.drawLine(x-width, y-width, x+width, y+width);
        g2.drawLine(x+width, y-width, x-width, y+width);
    }
}
