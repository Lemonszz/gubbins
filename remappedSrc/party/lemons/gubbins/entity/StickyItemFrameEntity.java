package party.lemons.gubbins.entity;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import party.lemons.gubbins.init.GubbinsEntities;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.init.GubbinsNetwork;

public class StickyItemFrameEntity extends ItemFrameEntity
{
	public StickyItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world)
	{
		super(entityType, world);
	}

	public StickyItemFrameEntity(World world, BlockPos pos, Direction direction)
	{
		this(GubbinsEntities.STICKY_ITEM_FRAME, world);
		this.attachmentPos = pos;
		this.setFacing(direction);
	}

	public ActionResult interact(PlayerEntity player, Hand hand)
	{
		ItemStack itemStack = player.getStackInHand(hand);
		boolean hasStack = !this.getHeldItemStack().isEmpty();
		boolean canPlaceHand = !itemStack.isEmpty();
		if (this.world.isClient)
		{
			return (hasStack || canPlaceHand) ? ActionResult.SUCCESS : ActionResult.PASS;
		}
		else
		{
			if (!hasStack)
			{
				if (canPlaceHand && !this.removed) {
					this.setHeldItemStack(itemStack);
					if (!player.abilities.creativeMode) {
						itemStack.decrement(1);
					}
				}
			}
			else
			{
				if(player.isSneaking())
				{
					this.playSound(SoundEvents.ENTITY_ITEM_FRAME_ROTATE_ITEM, 1.0F, 1.0F);
					this.setRotation(this.getRotation() + 1);
				}
				else
				{
					BlockPos interactPos = attachmentPos.offset(facing.getOpposite());
					world.getBlockState(interactPos).onUse(world, player, hand, new BlockHitResult(Vec3d.ZERO, facing, interactPos, true));
				}
			}

			return ActionResult.SUCCESS;
		}
	}

	public void onBreak(Entity entity) {
		this.playSound(SoundEvents.ENTITY_ITEM_FRAME_BREAK, 1.0F, 1.0F);
		this.dropHeldStack(entity, true);
	}

	private void dropHeldStack(Entity entity, boolean alwaysDrop) {
		if (!this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			if (entity == null) {
				this.removeFromFrame(this.getHeldItemStack());
			}

		} else {
			ItemStack itemStack = this.getHeldItemStack();
			this.setHeldItemStack(ItemStack.EMPTY);
			if (entity instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity)entity;
				if (playerEntity.abilities.creativeMode) {
					this.removeFromFrame(itemStack);
					return;
				}
			}

			if (alwaysDrop) {
				this.dropItem(GubbinsItems.STICKY_ITEM_FRAME);
			}

			if (!itemStack.isEmpty()) {
				itemStack = itemStack.copy();
				this.removeFromFrame(itemStack);
				this.dropStack(itemStack);
			}
		}
	}

	private void removeFromFrame(ItemStack map) {
		if (map.getItem() == Items.FILLED_MAP) {
			MapState mapState = FilledMapItem.getOrCreateMapState(map, this.world);
			mapState.removeFrame(this.attachmentPos, this.getEntityId());
			mapState.setDirty(true);
		}
		map.setHolder(null);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeUuid(getUuid());
		buf.writeVarInt(getEntityId());
		buf.writeDouble(getX());
		buf.writeDouble(getY());
		buf.writeDouble(getZ());
		buf.writeFloat(pitch);
		buf.writeFloat(yaw);
		buf.writeBlockPos(attachmentPos);
		int facingId = facing.getId();
		buf.writeInt(facingId);

		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_STICKY_FRAME, buf);
	}
}
