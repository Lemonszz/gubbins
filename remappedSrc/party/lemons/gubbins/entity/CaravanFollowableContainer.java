package party.lemons.gubbins.entity;

import net.minecraft.entity.mob.MobEntity;

public class CaravanFollowableContainer implements CaravanFollowable
{
	private CaravanFollowable leaderEntity;
	private CaravanFollowable followerEntity;
	private MobEntity entity;

	public CaravanFollowableContainer(MobEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public CaravanFollowable getFollower()
	{
		return this.followerEntity;
	}

	@Override
	public CaravanFollowable getLeader()
	{
		return this.leaderEntity;
	}

	@Override
	public void setFollower(CaravanFollowable follower)
	{
		this.followerEntity = follower;
	}

	@Override
	public void setLeader(CaravanFollowable follower)
	{
		if(leaderEntity != null)
			leaderEntity.setLeader(null);

		this.leaderEntity = follower;
		if(follower != null)
			follower.setFollower((CaravanFollowable) entity);
	}

	@Override
	public boolean canBeFollowed()
	{
		return getLeader() != null && getFollower() == null;
	}

	@Override
	public boolean hasCaravanLeaderCondition()
	{
		return entity.isLeashed();
	}
}
