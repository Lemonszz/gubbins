package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import party.lemons.gubbins.cave.CaveBiome;
import party.lemons.gubbins.init.GubbinsFeatures;

import java.util.Random;

public class MushroomCaveBiome extends CaveBiome
{
	public MushroomCaveBiome(float rarity)
	{
		super(rarity);

		this.doFinalFloorPass = true;
	}

	@Override
	public void generateFinalFloorPass(IWorld world, Random random, BlockPos p)
	{
		if(random.nextFloat() < 0.5F && sample(p) < 0.25F)
		{
			world.setBlockState(p.up(), random.nextBoolean() ? Blocks.RED_MUSHROOM.getDefaultState() : Blocks.BROWN_MUSHROOM.getDefaultState(), 2);
		}
	}

	@Override
	public void generateFloor(IWorld world, BlockPos pos)
	{
		boolean n = sample(pos) < 0.25F;
		if(n)
			world.setBlockState(pos, Blocks.MYCELIUM.getDefaultState(), 2);
	}

	private double sample(BlockPos pos)
	{
		return GubbinsFeatures.CAVE_BIOME.NOISE.sample(pos.getX(), pos.getY(), pos.getZ(), pos.getY(), pos.getZ());
	}

	@Override
	public void generateSide(IWorld world, BlockPos pos)
	{

	}

	@Override
	public void generateRoof(IWorld world, BlockPos pos)
	{

	}
}
