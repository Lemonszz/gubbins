package party.lemons.gubbins.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import party.lemons.gubbins.entity.CaravanFollowable;

import java.util.EnumSet;
import java.util.List;

public class BetterFormCavavanGoal<FollowableEntity extends MobEntity & CaravanFollowable> extends Goal
{
	public final FollowableEntity entity;
	private double speed;
	private int counter;

	public BetterFormCavavanGoal(FollowableEntity entity, double speed) {
		this.entity = entity;
		this.speed = speed;
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}

	public boolean canStart() {
		if (!this.entity.isLeashed() && !this.entity.isFollowing())
		{
			List<Entity> nearby = this.entity.world.getEntities(this.entity, this.entity.getBoundingBox().expand(9.0D, 4.0D, 9.0D), (entity) ->entity instanceof CaravanFollowable);
			double minDistance = Double.MAX_VALUE;
			FollowableEntity toFollow = null;

			for(Entity e : nearby)  //Find nearest non-leader
			{
				FollowableEntity followable = (FollowableEntity) e;
				if(followable.canBeFollowed())
				{
					double dis = entity.squaredDistanceTo(followable);
					if(dis <= minDistance)
					{
						minDistance = dis;
						toFollow = followable;
					}
				}
			}

			if(toFollow == null)
			{
				for(Entity e : nearby)  //Find nearest leader
				{
					FollowableEntity followable = (FollowableEntity) e;
					if(followable.isLeader())
					{
						double dis = entity.squaredDistanceTo(followable);
						if(dis <= minDistance)
						{
							minDistance = dis;
							toFollow = followable;
						}
					}
				}
			}

			if (toFollow == null)
			{
				return false;
			}
			else if (minDistance < 4.0D)
			{
				return false;
			}
			else if (!toFollow.isLeashed() && !this.canFollow(toFollow, 1)) {
				return false;
			}
			else
			{
				this.entity.setFollowing(toFollow);
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean shouldContinue()
	{
		if (this.entity.isFollowing() && ((Entity)entity.getFollowing()).isAlive() && this.canFollow(this.entity, 0)) {
			double distance = this.entity.squaredDistanceTo((Entity)this.entity.getFollowing());
			if (distance > 676.0D) {
				if (this.speed <= 3.0D) {
					this.speed *= 1.2D;
					this.counter = 40;
					return true;
				}

				if (this.counter == 0) {
					return false;
				}
			}

			if (this.counter > 0) {
				--this.counter;
			}

			return true;
		} else {
			return false;
		}
	}

	public void stop() {
		this.entity.stopFollowing();
		this.speed = 2.1D;
	}

	public void tick() {
		if (this.entity.isFollowing())
		{
			CaravanFollowable following = this.entity.getFollowing();
			Entity followEntity = (Entity) following;

			double distance = this.entity.distanceTo(followEntity);
			float scale = 2.0F;
			Vec3d vec3d = (new Vec3d(followEntity.getX() - this.entity.getX(), followEntity.getY() - this.entity.getY(), followEntity.getZ() - this.entity.getZ())).normalize().multiply(Math.max(distance - scale, 0.0D));
			this.entity.getNavigation().startMovingTo(this.entity.getX() + vec3d.x, this.entity.getY() + vec3d.y, this.entity.getZ() + vec3d.z, this.speed);
		}
	}

	private boolean canFollow(CaravanFollowable followable, int length)
	{
		if (length > 8) {
			return false;
		}
		else if (followable.isFollowing())
		{
			if (followable.isLeader()) {
				return true;
			}
			else {
				CaravanFollowable following = followable.getFollowing();
				++length;
				return this.canFollow(following, length);
			}
		} else {
			return false;
		}
	}
}
