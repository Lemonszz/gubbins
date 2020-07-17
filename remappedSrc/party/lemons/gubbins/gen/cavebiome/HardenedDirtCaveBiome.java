package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import party.lemons.gubbins.cave.CaveBiome;
import party.lemons.gubbins.init.GubbinsBlocks;
import party.lemons.gubbins.init.GubbinsFeatures;

public class HardenedDirtCaveBiome extends CaveBiome
{
	public HardenedDirtCaveBiome(float rarity)
	{
		super(rarity);
		this.maxHeight = 90;
		this.minHeight = 45;
	}

	@Override
	public void generateFloor(ServerWorldAccess world, BlockPos pos)
	{
		world.setBlockState(pos, getDirtState(pos), 2);
	}

	@Override
	public void generateSide(ServerWorldAccess world, BlockPos pos)
	{
		world.setBlockState(pos, getDirtState(pos), 2);

	}

	@Override
	public void generateRoof(ServerWorldAccess world, BlockPos pos)
	{
		world.setBlockState(pos, getDirtState(pos), 2);
	}

	private BlockState getDirtState(BlockPos pos)
	{
		return GubbinsFeatures.CAVE_BIOME.NOISE.sample(pos.getX(), pos.getY(), pos.getZ(), pos.getY(), pos.getZ()) > 0 ? Blocks.DIRT.getDefaultState() : GubbinsBlocks.HARDENED_DIRT.getDefaultState();
	}
}
