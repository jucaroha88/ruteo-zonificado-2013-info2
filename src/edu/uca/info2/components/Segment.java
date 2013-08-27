package edu.uca.info2.components;

import aimax.osm.data.entities.MapNode;

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

    public Segment inverted(){
        return new Segment(this.to, this.from);
    }

	@Override
	public String toString() {
		return "Segment [from=" + from + ", to=" + to + "]";
	}

	public MapNode getTo() {
		return to;
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

		if (from.getLat() == other.from.getLat()
				&& from.getLon() == other.from.getLon()
				&& to.getLat() == other.to.getLat()
				&& to.getLon() == other.to.getLon()) {
			return true;
		}

		return false;
	}

}
