package edu.uca.info2.components;

import aimax.osm.data.entities.MapNode;
import aimax.osm.data.impl.DefaultMapNode;

public class Segment {

	private MapNode from;
	private MapNode to;

	public Segment(MapNode from, MapNode to) {
		this.from = from;
		this.to = to;
	}

	public MapNode getFrom() {
		return from;
	}

	public void setFrom(MapNode from) {
		this.from = from;
	}

	public MapNode getTo() {
		return to;
	}

	public void setTo(MapNode to) {
		this.to = to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		// if (from == null) {
		// if (other.from != null)
		// return false;
		// } else if (!from.equals(other.from))
		// return false;
		// if (to == null) {
		// if (other.to != null)
		// return false;
		// } else if (!to.equals(other.to))
		// return false;
		if (from.getLat() == other.from.getLat()
				&& from.getLon() == other.from.getLon()
				&& to.getLat() == other.to.getLat()
				&& to.getLon() == other.to.getLon()) {
			return true;
		}
		
		return false;
	}

}
