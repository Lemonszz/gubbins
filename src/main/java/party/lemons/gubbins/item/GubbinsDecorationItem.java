package party.lemons.gubbins.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public abstract class GubbinsDecorationItem extends Item
{
	public GubbinsDecorationItem(Settings settings)
	{
		super(settings);
	}

	public ActionResult useOnBlock(ItemUsageContext context)
	{
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockPos placementPosition = blockPos.offset(direction);
		PlayerEntity player = context.getPlayer();
		ItemStack stack = context.getStack();

		if (player != null && !this.canPlaceOn(player, direction, stack, placementPosition))
		{
			return ActionResult.FAIL;
		}
		else
		{
			World world = context.getWorld();
			AbstractDecorationEntity entity = createEntity(world, placementPosition, direction);

			CompoundTag compoundTag = stack.getTag();
			if (compoundTag != null)
			{
				EntityType.loadFromEntityTag(world, player, entity, compoundTag);
			}

			if (entity.canStayAttached())
			{
				if (!world.isClient)
				{
					entity.onPlace();
					world.spawnEntity(entity);
				}
				stack.decrement(1);
				return ActionResult.SUCCESS;
			}
			else
			{
				return ActionResult.CONSUME;
			}
		}
	}

	protected abstract AbstractDecorationEntity createEntity(World world, BlockPos placementPos, Direction direction);

	protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
		return !side.getAxis().isVertical() && player.canPlaceOn(pos, side, stack);
	}
}
