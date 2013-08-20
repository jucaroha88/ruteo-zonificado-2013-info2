package edu.uca.info2.routing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aimax.osm.data.entities.MapNode;
import edu.uca.info2.components.Area;
import edu.uca.info2.components.Segment;
import edu.uca.info2.util.MapUtils;

public class ZASearchState {
	// current node
	private MapNode node;

	// Para contar la cantidad de veces que se paso p/ un segmento
	private HashMap<Segment, Integer> segmentsCounter = new HashMap<Segment, Integer>();

	public ZASearchState(MapNode node, Area area) {
		this.node = node;
		List<Segment> segments = MapUtils.segmentsInArea(area);

		for (Segment segment : segments) {
			segmentsCounter.put(segment, 0);
		}
	}

	public ZASearchState(Segment segment,
			HashMap<Segment, Integer> segmentsCounter) {
		this.node = segment.getTo();

		// deep copy
		for (Map.Entry<Segment, Integer> entry : segmentsCounter.entrySet()) {
			this.segmentsCounter.put(entry.getKey(), entry.getValue());
		}
		
		// incrementamos el ultimo segmento atravezado
		incrementSegmentCounter(segment);
	}

	public MapNode getNode() {
		return node;
	}


	public HashMap<Segment, Integer> getSegmentsCounter() {
		return segmentsCounter;
	}

	private void incrementSegmentCounter(Segment segment) {
		if (segmentsCounter.containsKey(segment)) {
			int counter = segmentsCounter.get(segment) + 1;
			segmentsCounter.put(segment, counter);
		}
	}

}
