package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import party.lemons.gubbins.cave.CaveBiome;

import java.util.Random;

public class SpiderCaveBiome extends CaveBiome
{
	public SpiderCaveBiome(float rarity)
	{
		super(rarity);
		this.doFinalWallPass = true;
		this.doFinalRoofPass = true;
	}

	@Override
	public void generateFinalFloorPass(IWorld world, Random random, BlockPos p)
	{
		if(random.nextFloat() < 0.05F)
			world.setBlockState(p.up(), Blocks.COBWEB.getDefaultState(), 2);
	}

	@Override
	public void generateFinalRoofPass(IWorld world, Random random, BlockPos p)
	{
		if(random.nextFloat() < 0.1F)
			world.setBlockState(p.down(), Blocks.COBWEB.getDefaultState(), 2);
	}

	@Override
	public void generateFloor(IWorld world, BlockPos pos)
	{

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
