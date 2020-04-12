package party.lemons.gubbins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.world.World;
import party.lemons.gubbins.init.GubbinsEntities;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.EntityUtil;

public class PrismarineArrowEntity extends ArrowEntity
{
	public PrismarineArrowEntity(EntityType<? extends PrismarineArrowEntity> entityType, World world)
	{
		super(entityType, world);
	}

	public PrismarineArrowEntity(World world, LivingEntity shooter)
	{
		super(GubbinsEntities.PRISMARINE_ARROW, world);
		this.updatePosition(shooter.getX(), shooter.getEyeY() - 0.10000000149011612D, shooter.getZ());
		setOwner(shooter);

		if (shooter instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}
	}

	@Override
	protected ItemStack asItemStack() {
		return new ItemStack(GubbinsItems.PRISMARINE_ARROW);
	}

	@Override
	public void tick()
	{
		super.tick();
	}

	protected float getDragInWater() {
		return 1.0F;
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}
}
