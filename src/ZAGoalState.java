import java.util.HashMap;

import aima.core.search.framework.GoalTest;
import edu.uca.info2.components.Segment;
import edu.uca.info2.routing.ZASearchState;

public class ZAGoalState implements GoalTest {

	@Override
	public boolean isGoalState(Object state) {
		boolean isGoal = true;

		if (state instanceof ZASearchState) {
			HashMap<Segment, Integer> segmentsCounter = ((ZASearchState) state)
					.getSegmentsCounter();

			for (Segment s : segmentsCounter.keySet()) {
				if (segmentsCounter.get(s) == 0) {
					isGoal = false;
				}
			}
		}

		return isGoal;
	}

}
