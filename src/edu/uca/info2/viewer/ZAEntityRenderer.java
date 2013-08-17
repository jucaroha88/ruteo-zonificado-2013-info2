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
import edu.uca.info2.util.MapNodeUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.HashMap;

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
                drawXcita(transformer.x(pos.getLon()), transformer.y(pos.getLat()), 8);
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
        /*
         * dibujar areas
         */
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
                drawCirculito(transformer.x(pos.getLon()), transformer.y(pos.getLat()));
            }
        }
        /*
         * dibujar nodos demarcados
         */
        for(MapNode nodo : map.getNodosDemarcados().keySet()){
            drawNodoYVecinos(nodo,map.getNodosDemarcados().get(nodo),null,null);
        }
        /*
         * dibujar nodo seleccionado
         */
         if( map.getSelectedNode() != null ){
            drawNodoYVecinos(map.getSelectedNode(),null,Color.black,Color.GREEN);
         }
    }
    
    /*
     * dibuja al nodo y sus vecinos que correspondan al area especificada, segun {@link edu.uca.info2.util}.
     * @param area el area dentro de la que se buscan los vecinos del nodo. Si es null se usa cualquier
     * area que le contenga al nodo
     * 
     * @param colorDelNodo si es null se usa {@link Color.CYAN}
     * @param colorDeVecinos si es null se usa {@link Color.YELLOW}
     * 
     */
    private void drawNodoYVecinos(MapNode nodo, Area area, Color colorDelNodo, Color colorDeVecinos){
        ZAMap map = (ZAMap) wnProvider;
        Color ncolor=colorDelNodo;
        Color vcolor=colorDeVecinos;
        //colores por default
        if(ncolor==null)
            ncolor=Color.CYAN;
        if(vcolor==null)
            vcolor=Color.YELLOW;
        //dibujamos el nodo protagonista
        g2.setColor(ncolor);
        Position pos = new Position(nodo);
        drawCirculito( transformer.x(pos.getLon()) , transformer.y(pos.getLat()));
        //establecemos el area
        Area ar=area;
        if(ar == null){
            for(Area a : map.getAreas()){
                if(a.isNodeInArea(nodo)){
                    ar=a;
                    break;
                }
            }
            if(ar == null){
                return;
            }
        }
        //dibujamos los vecinos
        g2.setColor(vcolor);
        for(MapNode vecino : MapNodeUtils.neighborsForNode(nodo, ar)){
            pos = new Position(vecino);
            drawCirculito( transformer.x(pos.getLon()) , transformer.y(pos.getLat()));
        }
    }
    
    /* dibuja una "X" chiquita, usada para marcar los limites de los rectangulos */
    private void drawXcita(int x, int y, int width){
        int halfwidth=width/2;
        g2.drawLine(x-halfwidth, y-halfwidth, x+halfwidth, y+halfwidth);
        g2.drawLine(x+halfwidth, y-halfwidth, x-halfwidth, y+halfwidth);
    }
    
    private void drawCirculito(int x, int y){
        g2.drawOval(x-2, y-2, 4, 4);
    }
}
