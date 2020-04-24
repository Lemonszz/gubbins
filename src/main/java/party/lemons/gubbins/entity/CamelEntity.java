package party.lemons.gubbins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.world.World;
import party.lemons.gubbins.entity.ai.BetterFormCaravanGoal;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.EntityUtil;

public class CamelEntity extends AbstractDonkeyEntity implements RangedAttackMob, CaravanFollowable
{
	private CaravanFollowableContainer followableContainer;

	public CamelEntity(EntityType<? extends CamelEntity> entityType, World world)
	{
		super(entityType, world);
		followableContainer = new CaravanFollowableContainer(this);
	}

	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.2D));
		this.goalSelector.add(2, new BetterFormCaravanGoal<>(this, 2.1D));
		this.goalSelector.add(3, new ProjectileAttackGoal(this, 1.25D, 40, 20.0F));
		this.goalSelector.add(3, new EscapeDangerGoal(this, 1.2D));
		this.goalSelector.add(4, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(5, new FollowParentGoal(this, 1.0D));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7D));
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
	}

	protected void initDataTracker() {
		super.initDataTracker();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return createAbstractDonkeyAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15F).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D);
	}

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
		return this.isTame() && (followableContainer.hasCaravanLeaderCondition() || hasPassengers());
	}

	@Override
	public void attack(LivingEntity target, float pullProgress)
	{

	}

	@Override
	protected float getSoundPitch()
	{
		return super.getSoundPitch() / 2F;
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}
}
