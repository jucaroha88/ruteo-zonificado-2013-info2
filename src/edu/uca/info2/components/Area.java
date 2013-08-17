package edu.uca.info2.components;

import aimax.osm.data.BoundingBox;
import aimax.osm.data.Position;
import aimax.osm.data.entities.MapNode;
import aimax.osm.data.entities.MapWay;
import edu.uca.info2.map.ZAMap;
import java.util.ArrayList;
import java.util.List;

public class Area {

	private ZAMap map;

	private long centerNodeId;

	private int radius;

	private List<MapNode> nodos;

	public Area(long centerNodeId) {
		setCenterNodeId(centerNodeId);
		setRadius(1);
		this.nodos = new ArrayList<MapNode>();
	}

	public long getCenterNodeId() {
		return centerNodeId;
	}

	public void setCenterNodeId(long centerNodeId) {
		this.centerNodeId = centerNodeId;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public float getLat() {
		return new Position(map.getNode(centerNodeId)).getLat();
	}

	public float getLon() {
		return new Position(map.getNode(centerNodeId)).getLon();
	}

	public String toString() {

		return " Area: center: " + getCenterNodeId() + " radius: "
				+ getRadius();
	}

	public List<MapNode> getNodos() {
		return nodos;
	}

	public void setMap(ZAMap map) {
		this.map = map;
	}

	/* busca los nodos dentro de su rango y los agrega a su lista de nodos */
	public void findNodes() {
		this.nodos = new ArrayList<MapNode>();
		MapNode centernode = map.getNode(centerNodeId);
		for (MapWay mapway : map.getWays(new BoundingBox())) {
			for (MapNode nodo : mapway.getNodes()) {
				if (new Position(nodo).getDistKM(centernode) < radius) {
					this.nodos.add(nodo);
				}	
			}
		}
	}
	
	public boolean isNodeInArea(MapNode node) {
		return this.nodos.contains(node);
	}
}
