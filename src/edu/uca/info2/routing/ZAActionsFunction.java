package edu.uca.info2.routing;

import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aimax.osm.data.MapWayAttFilter;
import aimax.osm.routing.OsmActionsFunction;

public class ZAActionsFunction implements ActionsFunction {

	@Override
	public Set<Action> actions(Object s) {
		OsmActionsFunction actionsFunction = new OsmActionsFunction(
				MapWayAttFilter.createCarWayFilter(), false, null);

		ZASearchState state = (ZASearchState) s;

		return actionsFunction.actions(state.getNode());
	}

}
