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
import party.lemons.gubbins.entity.ai.BetterFormCavavanGoal;
import party.lemons.gubbins.util.accessor.GoalSelectorAccessor;

@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin  extends AbstractDonkeyEntity implements CaravanFollowable
{
	@Inject(at=@At("RETURN"), method = "initGoals()V")
	protected void initGoals(CallbackInfo cbi)
	{
		((GoalSelectorAccessor)goalSelector).removeGoal(FormCaravanGoal.class);
		goalSelector.add(2, new BetterFormCavavanGoal<>(this, 2.1D));
	}

	private CaravanFollowable followingEntity;
	private CaravanFollowable followerEntity;

	@Override
	public CaravanFollowable getFollower()
	{
		return this.followerEntity;
	}

	@Override
	public CaravanFollowable getFollowing()
	{
		return this.followingEntity;
	}

	@Override
	public void setFollower(CaravanFollowable follower)
	{
		this.followerEntity = follower;
	}

	@Override
	public void setFollowing(CaravanFollowable follower)
	{
		this.followingEntity = follower;
		follower.setFollower(this);
	}

	@Override
	public boolean canBeFollowed()
	{
		return getFollowing() != null && getFollower() == null;
	}

	@Override
	public boolean isLeader()
	{
		return this.isLeashed() && !isBeingFollowed();
	}

	protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world)
	{
		super(entityType, world);
	}
}
