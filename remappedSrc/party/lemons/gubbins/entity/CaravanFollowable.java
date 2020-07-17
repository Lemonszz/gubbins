package party.lemons.gubbins.entity;

import net.minecraft.entity.mob.MobEntity;

public interface CaravanFollowable
{
	CaravanFollowable getFollower();
	CaravanFollowable getLeader();
	void setFollower(CaravanFollowable follower);
	void setLeader(CaravanFollowable follower);
	boolean canBeFollowed();
	boolean hasCaravanLeaderCondition();

	default boolean hasLeader()
	{
		return getLeader() != null && ((MobEntity)getLeader()).isAlive();
	}

	default boolean hasFollower()
	{
		return getFollower() != null && ((MobEntity)getFollower()).isAlive();
	}

	default void removeLeader()
	{
		if(this.hasLeader())
		{
			getLeader().setFollower(null);
		}
		setLeader(null);
	}
}
