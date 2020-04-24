package party.lemons.gubbins.mixin;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.WeightedGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.GoalSelectorAccessor;

import java.util.Set;

@Mixin(GoalSelector.class)
public abstract class GoalSelectorMixin implements GoalSelectorAccessor
{
	@Shadow @Final private Set<WeightedGoal> goals;

	@Shadow public abstract void remove(Goal goal);

	@Override
	public void removeGoal(Class<? extends Goal> clazz)
	{
		Goal toRemove = null;

		for(WeightedGoal goal : goals)
		{
			if(goal.getGoal().getClass() == clazz)
			{
				toRemove = goal.getGoal();
				break;
			}
		}

		System.out.println(toRemove);
		if(toRemove != null)
			remove(toRemove);
	}
}
