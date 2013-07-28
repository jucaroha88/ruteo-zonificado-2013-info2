/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.util;

import aimax.osm.data.Position;

/**
 *
 * @author Toshiba
 */
public class CartUtils {
    /* convierte de kilometros a grados, con una relación
     * trigonométrica, usando el radio de la tierra
     */
    public static float kmToDegreesAprox(float km){
        double rad = ((double)km)/((double)Position.EARTH_RADIUS);
        return (float)Math.toDegrees(rad);
    }
}
