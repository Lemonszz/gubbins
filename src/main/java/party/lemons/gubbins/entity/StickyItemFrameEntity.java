package party.lemons.gubbins.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import party.lemons.gubbins.init.GubbinsEntities;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.util.EntityUtil;

public class StickyItemFrameEntity extends ItemFrameEntity
{
	private static final TrackedData<Direction> FACING = DataTracker.registerData(StickyItemFrameEntity.class, TrackedDataHandlerRegistry.FACING);
	private static final TrackedData<BlockPos> ATTACH_POS = DataTracker.registerData(StickyItemFrameEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

	public StickyItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world)
	{
		super(entityType, world);
	}

	public StickyItemFrameEntity(World world, BlockPos pos, Direction direction)
	{
		this(GubbinsEntities.STICKY_ITEM_FRAME, world);
		this.attachmentPos = pos;
		this.setFacing(direction);
		updateAttachmentPosition();

		this.dataTracker.set(FACING, this.facing);
		this.dataTracker.set(ATTACH_POS, this.attachmentPos);
	}

	public boolean interact(PlayerEntity player, Hand hand)
	{
		ItemStack itemStack = player.getStackInHand(hand);
		boolean hasStack = !this.getHeldItemStack().isEmpty();
		boolean canPlaceHand = !itemStack.isEmpty();
		if (this.world.isClient)
		{
			return hasStack || canPlaceHand;
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

			return true;
		}
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data)
	{
		if(data == FACING)
			setFacing(dataTracker.get(FACING));
		else if(data == ATTACH_POS)
			attachmentPos = dataTracker.get(ATTACH_POS);
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(FACING, Direction.SOUTH);
		this.dataTracker.startTracking(ATTACH_POS, BlockPos.ORIGIN);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag)
	{
		super.readCustomDataFromTag(tag);

		this.dataTracker.set(FACING, this.facing);
		this.dataTracker.set(ATTACH_POS, this.attachmentPos);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		PacketByteBuf buf = EntityUtil.WriteEntitySpawn(this);
		return new CustomPayloadS2CPacket(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, buf);
	}
}
