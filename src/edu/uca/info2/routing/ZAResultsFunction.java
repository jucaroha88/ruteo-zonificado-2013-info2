package edu.uca.info2.routing;

import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;
import aimax.osm.routing.OsmMoveAction;
import edu.uca.info2.components.Segment;

import java.util.HashMap;

public class ZAResultsFunction implements ResultFunction {

    @Override
    public Object result(Object s, Action a) {
        if (s instanceof ZASearchState && a instanceof OsmMoveAction) {
            ZASearchState state = (ZASearchState) s;
            OsmMoveAction action = (OsmMoveAction) a;
            Segment segment = new Segment(state.getNode(), action.getTo());

            HashMap<Segment, Integer> segmentsCounter = state.getSegmentsCounter();
            ZASearchState nextState = new ZASearchState(segment,
                    segmentsCounter, state.getArea());

            Segment inverted = segment.inverted();

            if (segmentsCounter.containsKey(segment)) {
                if (nextState.getSegmentsCounter().get(segment) <= nextState
                        .getMaxSegmentPassThrough()) {
                    return nextState;
                }
            } else if (segmentsCounter.containsKey(inverted)) {
                if (nextState.getSegmentsCounter().get(inverted) <= nextState
                        .getMaxSegmentPassThrough()) {
                    return nextState;
                }
            }
        }

        return s;
    }
}
