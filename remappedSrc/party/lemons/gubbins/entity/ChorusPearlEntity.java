package party.lemons.gubbins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.EntityUtil;

public class ChorusPearlEntity extends ThrownItemEntity
{
	private LivingEntity owner;

	public ChorusPearlEntity(EntityType<? extends ChorusPearlEntity> entityType, World world)
	{
		super(entityType, world);
	}
/*
	public ChorusPearlEntity(World world, LivingEntity owner)
	{
		super(GubbinsEntities.CHORUS_PEARL, owner, world);
		this.owner = owner;
	}

	@Environment(EnvType.CLIENT)
	public ChorusPearlEntity(World world, double x, double y, double z)
	{
		super(GubbinsEntities.CHORUS_PEARL, x, y, z, world);
	}*/

	protected Item getDefaultItem()
	{
		return GubbinsItems.GARNET;
	}

	protected void onEntityHit(EntityHitResult entityHitResult)
	{
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		EntityUtil.chorusTeleport(entity);
	}

	protected void onCollision(HitResult hitResult)
	{
		super.onCollision(hitResult);

		for(int i = 0; i < 32; ++i)
		{
			this.world.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
		}

		this.remove();
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, EntityUtil.WriteEntitySpawn(this));
	}
}