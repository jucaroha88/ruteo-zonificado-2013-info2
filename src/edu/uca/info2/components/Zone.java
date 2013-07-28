package edu.uca.info2.components;

import aimax.osm.data.OsmMap;
import edu.uca.info2.map.ZAMap;

public class Zone {

    private String zoneId;
    private ZoneRestriction restriction;
    private ZAMap map;
    private long x1, x2, y1, y2;

    public Zone() {
    }


    public void setMap(ZAMap map) {
        this.map = map;
    }

    public String toString() {

        return "ZONE: " + zoneId + " Restriction: " + restriction;
    }

    /* getLat1,getLon1,getLat2,getLon2 obtienen las coordenadas a partir
     * los puntos de referencia
     * consideramos Lat1,Lon1,Lat2,Lon2 como Y1,X1,Y2,X2 respectivamente */
    public float getLat1() {
        return map.getNode(y1).getLat();
    }

    public float getLon1() {
        return map.getNode(x1).getLon();
    }

    public float getLat2() {
        return map.getNode(y2).getLat();
    }

    public float getLon2() {
        return map.getNode(x2).getLon();
    }

    public long getX1() {
        return x1;
    }

    public void setX1(long x1) {
        this.x1 = x1;
    }

    public long getX2() {
        return x2;
    }

    public void setX2(long x2) {
        this.x2 = x2;
    }

    public long getY1() {
        return y1;
    }

    public void setY1(long y1) {
        this.y1 = y1;
    }

    public long getY2() {
        return y2;
    }

    public void setY2(long y2) {
        this.y2 = y2;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneRestriction getRestriction() {
        return restriction;
    }

    public void setRestriction(ZoneRestriction restriction) {
        this.restriction = restriction;
    }
}
