package edu.uca.info2.routing;

import aima.core.search.framework.HeuristicFunction;
import edu.uca.info2.components.Segment;

import java.util.Map;

public class ZAUnvisitedSegmentsRemaining implements HeuristicFunction {
    @Override
    public double h(Object s) {
        ZASearchState state = (ZASearchState) s;
        double unvisitedSegments = 0;

        for (Map.Entry<Segment, Integer> entry : state.getSegmentsCounter().entrySet()) {
            if (entry.getValue() == 0) {
                unvisitedSegments++;
            }
        }

        return unvisitedSegments;
    }
}
