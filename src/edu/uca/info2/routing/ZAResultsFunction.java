package edu.uca.info2.routing;

import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;
import aimax.osm.routing.OsmMoveAction;
import edu.uca.info2.components.Segment;

public class ZAResultsFunction implements ResultFunction {

	@Override
	public Object result(Object s, Action a) {
		if (s instanceof ZASearchState && a instanceof OsmMoveAction) {
			ZASearchState state = (ZASearchState) s;
			OsmMoveAction action = (OsmMoveAction) a;
			Segment segment = new Segment(state.getNode(), action.getTo());

			ZASearchState nextState = new ZASearchState(segment,
					state.getSegmentsCounter());

			if (nextState.getSegmentsCounter().get(segment) <= nextState
					.getMaxSegmentPassThrough()) {
				return nextState;
			}

		}

		return s;
	}
}
