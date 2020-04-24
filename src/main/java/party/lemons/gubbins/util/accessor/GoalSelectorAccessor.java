package party.lemons.gubbins.util.accessor;

import net.minecraft.entity.ai.goal.Goal;

public interface GoalSelectorAccessor
{
	void removeGoal(Class<? extends Goal> clazz);
}
