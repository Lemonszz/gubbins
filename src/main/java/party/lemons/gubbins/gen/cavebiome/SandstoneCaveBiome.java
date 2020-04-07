package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import party.lemons.gubbins.cave.CaveBiome;
import party.lemons.gubbins.init.GubbinsFeatures;

public class SandstoneCaveBiome extends CaveBiome
{
	public SandstoneCaveBiome(float rarity)
	{
		super(rarity);
		this.isBiomeRestricted();
	}

	@Override
	public boolean canGenerateInBiome(Biome biome)
	{
		return biome.getCategory() == Biome.Category.DESERT;
	}

	@Override
	public void generateFloor(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, getSandstoneState(pos), 2);
	}

	@Override
	public void generateSide(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, getSandstoneState(pos), 2);

	}

	@Override
	public void generateRoof(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, getSandstoneState(pos), 2);
	}

	private BlockState getSandstoneState(BlockPos pos)
	{
		return GubbinsFeatures.CAVE_BIOME.NOISE.sample(pos.getX(), pos.getY(), pos.getZ(), pos.getY(), pos.getZ()) > 0 ? Blocks.SANDSTONE.getDefaultState() : Blocks.SMOOTH_SANDSTONE.getDefaultState();
	}
}
