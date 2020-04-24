package party.lemons.gubbins.entity;

public interface CaravanFollowable
{
	CaravanFollowable getFollower();
	CaravanFollowable getFollowing();
	void setFollower(CaravanFollowable follower);
	void setFollowing(CaravanFollowable follower);
	boolean canBeFollowed();
	boolean isLeader();

	default boolean isFollowing()
	{
		return getFollowing() != null;
	}

	default boolean isBeingFollowed()
	{
		return getFollower() != null;
	}

	default void stopFollowing()
	{
		if(this.isFollowing())
		{
			getFollowing().setFollower(null);
		}
		setFollowing(null);
	}
}
