package party.lemons.gubbins.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
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
	public boolean canSpawn(WorldView world)
	{
		return super.canSpawn(world) && random.nextInt(3) == 0;
	}

	protected boolean receiveFood(PlayerEntity player, ItemStack item) {
		int ageChange = 0;
		int temperChange = 0;
		float healthChange = 0.0F;
		boolean playSound = false;

		Item item2 = item.getItem();
		if (item2 == Items.WHEAT)
		{
			ageChange = 10;
			temperChange = 3;
			healthChange = 2.0F;
		} else if (item2 == Blocks.HAY_BLOCK.asItem())
		{
			ageChange = 90;
			temperChange = 6;
			healthChange = 10.0F;
			if (this.isTame() && this.getBreedingAge() == 0 && this.canEat()) {
				playSound = true;
				this.lovePlayer(player);
			}
		}

		if (this.getHealth() < this.getMaxHealth() && healthChange > 0.0F) {
			this.heal(healthChange);
			playSound = true;
		}

		if (this.isBaby() && ageChange > 0)
		{
			this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
			if (!this.world.isClient) {
				this.growUp(ageChange);
			}

			playSound = true;
		}

		if (temperChange > 0 && (playSound || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
			playSound = true;
			if (!this.world.isClient) {
				this.addTemper(temperChange);
			}
		}

		if (playSound && !this.isSilent()) {
			this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_LLAMA_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
		}

		return playSound;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return !stack.isEmpty() && stack.getItem() == Items.HAY_BLOCK;
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}
}
