package party.lemons.gubbins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FormCaravanGoal;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.entity.CaravanFollowable;
import party.lemons.gubbins.entity.CaravanFollowableContainer;
import party.lemons.gubbins.entity.ai.BetterFormCaravanGoal;
import party.lemons.gubbins.util.accessor.GoalSelectorAccessor;

@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin  extends AbstractDonkeyEntity implements CaravanFollowable
{
	@Inject(at=@At("RETURN"), method = "initGoals()V")
	protected void initGoals(CallbackInfo cbi)
	{
		((GoalSelectorAccessor)goalSelector).removeGoal(FormCaravanGoal.class);
		goalSelector.add(2, new BetterFormCaravanGoal<>(this, 2.1D));
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	public void onConstruct(CallbackInfo cbi)
	{
		followableContainer = new CaravanFollowableContainer(this);
	}

	private CaravanFollowableContainer followableContainer;

	@Override
	public CaravanFollowable getFollower()
	{
		return followableContainer.getFollower();
	}

	@Override
	public CaravanFollowable getLeader()
	{
		return followableContainer.getLeader();
	}

	@Override
	public void setFollower(CaravanFollowable follower)
	{
		followableContainer.setFollower(follower);
	}

	@Override
	public void setLeader(CaravanFollowable follower)
	{
		followableContainer.setLeader(follower);
	}

	@Override
	public boolean canBeFollowed()
	{
		return followableContainer.canBeFollowed();
	}

	@Override
	public boolean hasCaravanLeaderCondition()
	{
		return followableContainer.hasCaravanLeaderCondition();
	}

	protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world)
	{
		super(entityType, world);
	}
}
