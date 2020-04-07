package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import party.lemons.gubbins.init.GubbinsBlocks;
import party.lemons.gubbins.init.GubbinsFeatures;

public class MossyCaveBiome extends SimpleCaveBiome
{
	public MossyCaveBiome(float rarity)
	{
		super(null, rarity);
	}

	@Override
	public BlockState getState(BlockPos pos)
	{
		return GubbinsFeatures.CAVE_BIOME.NOISE.sample(pos.getX(), pos.getY(), pos.getZ(), pos.getY(), pos.getZ()) > 0 ? Blocks.STONE.getDefaultState() : GubbinsBlocks.MOSSY_STONE.getDefaultState();
	}
}
