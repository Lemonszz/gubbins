package party.lemons.gubbins.util;

import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import party.lemons.gubbins.init.GubbinsBlocks;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.accessor.AbstractBlockAccessor;

import java.util.ArrayList;
import java.util.List;

public class EntityUtil
{
	public static Packet<?> createCustomSpawnPacket(Entity e)
	{
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, WriteEntitySpawn(e));
	}

	public static PacketByteBuf WriteEntitySpawn(Entity entity)
	{
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
		buf.writeUuid(entity.getUuid());
		buf.writeVarInt(entity.getEntityId());
		buf.writeDouble(entity.getX());
		buf.writeDouble(entity.getY());
		buf.writeDouble(entity.getZ());
		buf.writeFloat(entity.pitch);
		buf.writeFloat(entity.yaw);

		return buf;
	}

	public static boolean isUsingTelescope(PlayerEntity playerEntity)
	{
		ItemStack activeStack = playerEntity.getActiveItem();
		return !activeStack.isEmpty() && activeStack.getItem() == GubbinsItems.TELESCOPE;
	}

	public static int getSlotWithItemStack(PlayerEntity playerEntity, ItemStack stack)
	{
		DefaultedList<ItemStack> inv = playerEntity.inventory.main;
		for(int i = 0; i < inv.size(); ++i) {
			if (!inv.get(i).isEmpty() && areItemsEqual(stack, inv.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public static void chorusTeleport(Entity entity)
	{
		World world = entity.world;
		double pX = entity.getX();
		double pY = entity.getY();
		double pZ = entity.getZ();

		if (!world.isClient)
		{
			BlockPos.Mutable pos = new BlockPos.Mutable(pX, pY, pZ);

			List<BlockPos> teleportPositions = new ArrayList<>();

			for(int x = (int) pX - 8; x < pX + 8; x++)
			{
				for(int y = (int) pY - 8; y < pY + 8; y++)
				{
					for(int z = (int) pZ - 8; z < pZ + 8; z++)
					{
						pos.set(x, y, z);
						BlockState state = world.getBlockState(pos);

						if(state.getBlock() == GubbinsBlocks.ENDERPEARL_BLOCK)
						{
							if(!pos.up().equals(entity.getBlockPos()) && !((AbstractBlockAccessor) world.getBlockState(pos.up()).getBlock()).isCollideable() && !((AbstractBlockAccessor) world.getBlockState(pos.up(2)).getBlock()).isCollideable())
								teleportPositions.add(new BlockPos(x, y, z));
						}
					}
				}
			}

			if(!teleportPositions.isEmpty())
			{
				BlockPos telePos = teleportPositions.get(world.getRandom().nextInt(teleportPositions.size()));
				float x = telePos.getX() + 0.5F;
				float y = telePos.getY() + 1F;
				float z = telePos.getZ() + 0.5F;

				if(entity.hasVehicle()) entity.stopRiding();

				if(teleport(entity, x, y, z, true))
				{
					world.playSound((PlayerEntity) null, x, y, z, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
					entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
				}
			}
			else
			{
				//Regular chorus teleport
				if (!world.isClient)
				{
					for(int i = 0; i < 16; ++i)
					{
						double teleportX = entity.getX() + (world.getRandom().nextDouble() - 0.5D) * 16.0D;
						double teleportY = MathHelper.clamp(entity.getY() + (double)(world.getRandom().nextInt(16) - 8), 0.0D, (double)(world.getDimensionHeight() - 1));
						double teleportZ = entity.getZ() + (world.getRandom().nextDouble() - 0.5D) * 16.0D;
						if (entity.hasVehicle())
							entity.stopRiding();

						if (teleport(entity, teleportX, teleportY, teleportZ, true))
						{
							world.playSound(null, pX, pY, pZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
							entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
							break;
						}
					}
				}
			}
		}
	}

	public static boolean teleport(Entity entity, double x, double y, double z, boolean particleEffects) {
		double d = entity.getX();
		double e = entity.getY();
		double f = entity.getZ();
		double g = y;
		boolean bl = false;
		BlockPos blockPos = new BlockPos(x, y, z);
		World world = entity.world;
		if (world.isChunkLoaded(blockPos)) {
			boolean bl2 = false;

			while(!bl2 && blockPos.getY() > 0) {
				BlockPos blockPos2 = blockPos.down();
				BlockState blockState = world.getBlockState(blockPos2);
				if (blockState.getMaterial().blocksMovement()) {
					bl2 = true;
				} else {
					--g;
					blockPos = blockPos2;
				}
			}

			if (bl2) {
				entity.requestTeleport(x, g, z);
				if (world.doesNotCollide(entity) && !world.containsFluid(entity.getBoundingBox())) {
					bl = true;
				}
			}
		}

		if (!bl) {
			entity.requestTeleport(d, e, f);
			return false;
		} else {
			if (particleEffects) {
				world.sendEntityStatus(entity, (byte)46);
			}

			if (entity instanceof MobEntityWithAi) {
				((MobEntityWithAi)entity).getNavigation().stop();
			}

			return true;
		}
	}

	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() == stack2.getItem() && ItemStack.areTagsEqual(stack1, stack2);
	}

	private EntityUtil(){}
}
