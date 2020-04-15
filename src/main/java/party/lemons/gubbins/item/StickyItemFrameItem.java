package party.lemons.gubbins.item;

import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import party.lemons.gubbins.entity.StickyItemFrameEntity;

public class StickyItemFrameItem extends GubbinsDecorationItem
{
	public StickyItemFrameItem(Settings settings)
	{
		super(settings);
	}

	@Override
	protected AbstractDecorationEntity createEntity(World world, BlockPos placementPos, Direction direction)
	{
		return new StickyItemFrameEntity(world, placementPos, direction);
	}

	protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos)
	{
		return !World.isHeightInvalid(pos) && player.canPlaceOn(pos, side, stack);
	}
}
