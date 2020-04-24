package party.lemons.gubbins.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import party.lemons.gubbins.entity.CaravanFollowable;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class BetterFormCaravanGoal<T extends MobEntity & CaravanFollowable> extends Goal
{
	public final T entity;
	private double speed;
	private int counter;

	public BetterFormCaravanGoal(T entity, double speed) {
		this.entity = entity;
		this.speed = speed;
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}

	public boolean canStart() {
		if (!this.entity.hasCaravanLeaderCondition() && !this.entity.hasLeader())
		{
			List<Entity> list = this.entity.world.getEntities(this.entity, this.entity.getBoundingBox().expand(9.0D, 4.0D, 9.0D), (entity) ->entity instanceof CaravanFollowable);
			T llamaEntity = null;
			double d = Double.MAX_VALUE;
			Iterator var5 = list.iterator();

			Entity entity2;
			T llamaEntity3;
			double f;
			while(var5.hasNext()) {
				entity2 = (Entity)var5.next();
				llamaEntity3 = (T)entity2;
				if (llamaEntity3.hasLeader() && !llamaEntity3.hasFollower()) {
					f = this.entity.squaredDistanceTo(llamaEntity3);
					if (f <= d) {
						d = f;
						llamaEntity = llamaEntity3;
					}
				}
			}

			if (llamaEntity == null) {
				var5 = list.iterator();

				while(var5.hasNext()) {
					entity2 = (Entity)var5.next();
					llamaEntity3 = (T)entity2;
					if (llamaEntity3.hasCaravanLeaderCondition() && !llamaEntity3.hasFollower()) {
						f = this.entity.squaredDistanceTo(llamaEntity3);
						if (f <= d) {
							d = f;
							llamaEntity = llamaEntity3;
						}
					}
				}
			}

			if (llamaEntity == null) {
				return false;
			} else if (d < 4.0D) {
				return false;
			} else if (!llamaEntity.hasCaravanLeaderCondition() && !this.canFollow(llamaEntity, 1)) {
				return false;
			} else {
				this.entity.setLeader(llamaEntity);
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean shouldContinue() {
		if (this.entity.hasLeader() && ((T)this.entity.getLeader()).isAlive() && this.canFollow(this.entity, 0)) {
			double d = this.entity.squaredDistanceTo((T)this.entity.getLeader());
			if (d > 676.0D) {
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
		this.entity.removeLeader();
		this.speed = 2.1D;
	}

	public void tick() {
		if (this.entity.hasLeader()) {
			T llamaEntity = (T)this.entity.getLeader();
			double d = (double)this.entity.distanceTo(llamaEntity);
			float f = 2.0F;
			Vec3d vec3d = (new Vec3d(llamaEntity.getX() - this.entity.getX(), llamaEntity.getY() - this.entity.getY(), llamaEntity.getZ() - this.entity.getZ())).normalize().multiply(Math.max(d - 2.0D, 0.0D));
			this.entity.getNavigation().startMovingTo(this.entity.getX() + vec3d.x, this.entity.getY() + vec3d.y, this.entity.getZ() + vec3d.z, this.speed);
		}
	}

	private boolean canFollow(T llama, int length) {
		if (length > 8) {
			return false;
		} else if (llama.hasLeader()) {
			if (((T)llama.getLeader()).hasCaravanLeaderCondition()) {
				return true;
			} else {
				T var10001 = (T)llama.getLeader();
				++length;
				return this.canFollow(var10001, length);
			}
		} else {
			return false;
		}
	}
}