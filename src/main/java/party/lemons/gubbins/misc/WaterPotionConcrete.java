package party.lemons.gubbins.misc;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import party.lemons.gubbins.util.accessor.ConcretePowderBlockAccessor;

public class WaterPotionConcrete
{
	public static void harden(PlayerEntity player, World world, BlockPos pos)
	{
		if(player != null && !world.canPlayerModifyAt(player, pos))
			return;

		BlockState st = world.getBlockState(pos);
		if(st.getBlock() instanceof ConcretePowderBlock)
		{
			ConcretePowderBlockAccessor accessor = (ConcretePowderBlockAccessor) st.getBlock();
			world.setBlockState(pos, accessor.getHardenedState());
		}
	}

	public static void convert(World world, PlayerEntity playerEntity, BlockHitResult result)
	{
		if(world.isClient())
			return;

		Direction direction = result.getSide();
		BlockPos blockPos = result.getBlockPos();
		BlockPos start = result.getBlockPos();
		harden(playerEntity, world, start);
		harden(playerEntity, world, start.offset(direction.getOpposite()));

		for(Direction d : Direction.values())
		{
			harden(playerEntity, world, start.offset(d));
		}
	}
}
