package party.lemons.gubbins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.boat.BoatType;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.init.GubbinsEntities;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.EntityUtil;

public class NewBoatEntity extends BoatEntity
{
	private static final TrackedData<String> BOAT_TYPE = DataTracker.registerData(NewBoatEntity.class, TrackedDataHandlerRegistry.STRING);

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


	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(BOAT_TYPE, new Identifier(Gubbins.MODID, "crimson").toString());
	}

	public void setBoatType(BoatType type)
	{
		this.getDataTracker().set(BOAT_TYPE, type.id.toString());
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

	protected void writeCustomDataToTag(CompoundTag tag)
	{
		tag.putString("NewType", this.getNewBoatType().id.toString());
	}

	protected void readCustomDataFromTag(CompoundTag tag) {
		if (tag.contains("NewType", 8)) {
			this.setBoatType(BoatTypes.REGISTRY.get(new Identifier(tag.getString("NewType"))));
		}

	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}
}
