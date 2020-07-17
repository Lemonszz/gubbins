package party.lemons.gubbins.entity;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.boat.BoatType;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.init.GubbinsEntities;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.EntityUtil;
import party.lemons.gubbins.util.accessor.BoatAccessor;

public class NewBoatEntity extends BoatEntity
{
	private static final TrackedData<String> BOAT_TYPE = DataTracker.registerData(NewBoatEntity.class, TrackedDataHandlerRegistry.STRING);
	private static final TrackedData<Boolean> HAS_CHEST = DataTracker.registerData(NewBoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final String TAG_HAS_CHEST = "HasChest";
	private static final String TAG_TYPE = "NewType";
	private static final String TAG_ITEMS = "Items";
	private static final String TAG_SLOT = "Slot";

	public final SimpleInventory inventory = new SimpleInventory(27);

	public NewBoatEntity(EntityType<? extends BoatEntity> entityType, World world)
	{
		super(entityType, world);
	}

	public NewBoatEntity(World world, double x, double y, double z)
	{
		this(GubbinsEntities.NEW_BOAT, world);
		this.updatePosition(x, y, z);
		this.setVelocity(Vec3d.ZERO);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	public NewBoatEntity(BoatEntity boatEntity, BoatType type)
	{
		this(GubbinsEntities.NEW_BOAT, boatEntity.world);

		this.copyPositionAndRotation(boatEntity);
		setChest(true);
		setBoatType(type);
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(BOAT_TYPE, new Identifier(Gubbins.MODID, "crimson").toString());
		this.dataTracker.startTracking(HAS_CHEST, false);
	}

	public void setBoatType(BoatType type)
	{
		this.getDataTracker().set(BOAT_TYPE, type.id.toString());
	}

	public void setChest(boolean chest)
	{
		this.getDataTracker().set(HAS_CHEST, chest);
	}

	public boolean hasChest()
	{
		return this.getDataTracker().get(HAS_CHEST);
	}

	public BoatType getNewBoatType()
	{
		return BoatTypes.REGISTRY.get(new Identifier(this.getDataTracker().get(BOAT_TYPE)));
	}

	@Override
	public Item asItem()
	{
		return getNewBoatType().item.asItem();
	}

	@Override
	protected void writeCustomDataToTag(CompoundTag tag)
	{
		tag.putString(TAG_TYPE, this.getNewBoatType().id.toString());

		if(hasChest())
		{
			tag.putBoolean(TAG_HAS_CHEST, true);

			ListTag invTag = new ListTag();

			for(int i = 0; i < this.inventory.size(); ++i)
			{
				ItemStack itemStack = this.inventory.getStack(i);
				if (!itemStack.isEmpty())
				{
					CompoundTag stackTag = new CompoundTag();
					stackTag.putByte(TAG_SLOT, (byte)i);
					itemStack.toTag(stackTag);
					invTag.add(stackTag);
				}
			}

			tag.put(TAG_ITEMS, invTag);
		}
	}

	@Override
	public boolean damage(DamageSource source, float amount)
	{
		boolean damaged = super.damage(source, amount);
		if(removed && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
		{
			dropInventory();
		}

		return damaged;
	}

	public void dropInventory() {
		if (this.hasChest()) {
			if (!this.world.isClient) {
				this.dropItem(Blocks.CHEST);

				for(ItemStack stack : inventory.clearToList())
				{
					this.dropStack(stack);
				}
				setChest(false);
			}
		}

	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag)
	{
		if (tag.contains(TAG_TYPE, 8))
		{
			this.setBoatType(BoatTypes.REGISTRY.get(new Identifier(tag.getString(TAG_TYPE))));
		}

		if(tag.contains(TAG_HAS_CHEST))
		{
			this.setChest(true);

			ListTag listTag = tag.getList(TAG_ITEMS, 10);

			for(int i = 0; i < listTag.size(); ++i)
			{
				CompoundTag compoundTag = listTag.getCompound(i);
				int j = compoundTag.getByte(TAG_SLOT) & 255;
				if (j < this.inventory.size())
				{
					this.inventory.setStack(j, ItemStack.fromTag(compoundTag));
				}
			}
		}
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand)
	{
		if(hasChest() && player.isSneaking())
		{
			openInventory(player);
			return ActionResult.SUCCESS;
		}

		if(player.shouldCancelInteraction()) return ActionResult.PASS;

		ItemStack stack = player.getStackInHand(hand);
		if(!stack.isEmpty() && stack.getItem() == Blocks.CHEST.asItem() && !hasChest())
		{
			setChest(true);
			stack.decrement(1);
			return ActionResult.PASS;
		}

		return super.interact(player, hand);
	}

	@Override
	protected boolean canAddPassenger(Entity passenger)
	{
		if(getPassengerList().size() == 1 && hasChest())
			return false;

		return this.getPassengerList().size() < 2 && !this.isSubmergedIn(FluidTags.WATER);
	}

	@Override
	public void updatePassengerPosition(Entity passenger)
	{
		if(this.hasPassenger(passenger))
		{
			float offset = 0.0F;
			float yOffset = (float)((this.removed ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());

			if(this.getPassengerList().size() > 1)
			{
				int i = this.getPassengerList().indexOf(passenger);
				offset = i == 0 ? 0.2F : -0.6F;

				if(passenger instanceof AnimalEntity)
				{
					offset += 0.2F;
				}
			}

			if(hasChest())
				offset += 0.25F;

			Vec3d offsetDir = (new Vec3d(offset, 0.0D, 0.0D)).rotateY(-this.yaw * 0.017453292F - 1.5707964F);
			passenger.updatePosition(this.getX() + offsetDir.x, this.getY() + (double)yOffset, this.getZ() + offsetDir.z);
			passenger.yaw += ((BoatAccessor)this).getYawVelocity();
			passenger.setHeadYaw(passenger.getHeadYaw() + ((BoatAccessor)this).getYawVelocity());
			this.copyEntityData(passenger);

			if (passenger instanceof AnimalEntity && this.getPassengerList().size() > 1) {
				int animalLook = passenger.getEntityId() % 2 == 0 ? 90 : 270;
				passenger.setYaw(((AnimalEntity)passenger).bodyYaw + (float)animalLook);
				passenger.setHeadYaw(passenger.getHeadYaw() + (float)animalLook);
			}
		}
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}

	public void openInventory(PlayerEntity player)
	{
		if(hasChest() && !player.world.isClient())
			ContainerProviderRegistry.INSTANCE.openContainer(Gubbins.BOAT_CHEST_SCREEN, player, b->b.writeInt(this.getEntityId()));
	}
}
