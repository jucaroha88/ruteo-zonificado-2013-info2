/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.viewer;

import aimax.osm.viewer.MapViewFrame;
import edu.uca.info2.map.ZAMap;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.swing.JLabel;

/**
 *
 * @author Toshiba
 */
public class ZAMapViewFrame extends MapViewFrame{
    
    private JLabel statusBar;

    public ZAMapViewFrame() {
        super();
        
        initStatusBar();
        
        getView().addMouseMotionListener(new ViewMouseMotionListener());
        
        getView().setMap(new ZAMap());
        getView().setRenderer(new ZAEntityRenderer());
        readMap(new File("asu.osm"));
        setTitle("Zonas y Areas");
        setSize(800, 600);
        setVisible(true);
    }
    
    protected void initStatusBar(){
        statusBar = new JLabel("Le Status Bar");
        statusBar.setOpaque(true);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
    }
    
    private class ViewMouseMotionListener implements MouseMotionListener{
        @Override
        public void mouseMoved(MouseEvent e) {
            statusBar.setText(String.format("Lat: %.6f Lon %.6f",
                                            view.getTransformer().lat(e.getY()),
                                            view.getTransformer().lon(e.getX())  ));
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }
        
    }
}
