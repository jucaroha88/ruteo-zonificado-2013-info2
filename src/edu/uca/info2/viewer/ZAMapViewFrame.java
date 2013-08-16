/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.viewer;

import aimax.osm.data.Position;
import aimax.osm.data.entities.MapNode;
import aimax.osm.viewer.MapViewFrame;
import edu.uca.info2.map.ZAMap;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.swing.JLabel;

/**
 *
 * @author Toshiba
 */
public class ZAMapViewFrame extends MapViewFrame{
    private StatusBar statusBar = new StatusBar();
    
    public ZAMapViewFrame() {
        super();
        
        ViewMouseListener myMouseListener = new ViewMouseListener();
        getView().addMouseMotionListener(myMouseListener);
        getView().addMouseListener(myMouseListener);
        
        getView().setMap(new ZAMap());
        getView().setRenderer(new ZAEntityRenderer());
        //readMap(new File("asu.osm"));
        setTitle("Zonas y Areas");
        setSize(800, 600);
        setVisible(true);
    }
    
    private class StatusBar{
        private JLabel label;
        float lat,lon;
        long selectedNodeId;
        
        public StatusBar(){
            label = new JLabel();
            label.setOpaque(true);
            getContentPane().add(label, BorderLayout.SOUTH);
        }
        
        protected void render(){
            label.setText(String.format("Lat: %.6f Lon %.6f | Selected Node ID: %d", lat, lon, selectedNodeId));
        }
        
        
        public void setCoords(float lat, float lon){
            this.lat=lat;
            this.lon=lon;
            render();
        }

        public void setSelectedNodeId(long selectedNodeId) {
            this.selectedNodeId = selectedNodeId;
            render();
        }
    }
    
    private class ViewMouseListener implements MouseMotionListener, MouseListener{
        @Override
        public void mouseMoved(MouseEvent e) {
            statusBar.setCoords(view.getTransformer().lat(e.getY()), view.getTransformer().lon(e.getX()));
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            ZAMap map= (ZAMap)getView().getMap();
            MapNode selectedNode = map.getNearestWayNode(new Position(view.getTransformer().lat(e.getY()),
                                                                   view.getTransformer().lon(e.getX()) ),null);
            map.setSelectedNode(selectedNode);
            statusBar.setSelectedNodeId(selectedNode.getId());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
        
        
    }
}
