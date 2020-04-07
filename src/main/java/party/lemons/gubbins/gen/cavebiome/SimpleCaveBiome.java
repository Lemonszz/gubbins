package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import party.lemons.gubbins.cave.CaveBiome;

public class SimpleCaveBiome extends CaveBiome
{
	private final BlockState state;

	public SimpleCaveBiome(BlockState state, float rarity)
	{
		super(rarity);
		this.state = state;
	}

	@Override
	public void generateFloor(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, getState(pos), 2);
	}

	@Override
	public void generateSide(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, getState(pos), 2);
	}

	@Override
	public void generateRoof(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, getState(pos), 2);
	}

	public BlockState getState(BlockPos pos)
	{
		return state;
	}
}
