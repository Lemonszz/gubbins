package party.lemons.gubbins.cave;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import party.lemons.gubbins.gen.cavebiome.CaveBiomeFeature;
import party.lemons.gubbins.util.GubbinsConstants;

import java.util.Random;
import java.util.Set;

public abstract class CaveBiome
{
	protected Set<Biome> biomes = Sets.newHashSet();
	private boolean isBiomeRestricted;
	protected float rarity;
	protected int maxY = 70;
	protected int minY = 1;
	protected int minHeight = 5;
	protected int maxHeight = 50;

	public boolean doFinalFloorPass = false;
	public boolean doFinalWallPass = false;
	public boolean doFinalRoofPass = false;

	public CaveBiome(float rarity)
	{
		this.rarity = rarity;
	}

	public void generate(Random random, ServerWorldAccess world, BlockPos pos, CaveBiomeFeature.CavePositions positions, boolean floorEdge, boolean roofEdge, boolean sideEdge)
	{
		BlockState currentState = world.getBlockState(pos);
		BlockPos genPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

		if(currentState.getHardness(world, pos) < 0 || !OreFeatureConfig.Target.NATURAL_STONE.getCondition().test(currentState))
			return;

		if(world.getBlockState(pos.up()).isAir())
		{
			if((floorEdge || sideEdge) && random.nextBoolean())
				return;

			positions.floorPositions.add(genPos);
			generateFloor(world, genPos);
		}
		else if(world.getBlockState(pos.down()).isAir())
		{
			if((roofEdge || sideEdge) && random.nextBoolean())
				return;

			positions.roofPositions.add(genPos);
			generateRoof(world, genPos);
		}
		else if(isSidePosition(world, pos))
		{
			if(sideEdge && random.nextBoolean())
				return;

			positions.sidePositions.add(genPos);
			generateSide(world, genPos);
		}
	}

	protected boolean isSidePosition(ServerWorldAccess world, BlockPos pos)
	{
		BlockPos.Mutable mutablePos = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
		for(Direction dir : GubbinsConstants.HORIZONTALS)
		{
			if(world.getBlockState(mutablePos.move(dir)).isAir())
				return true;
		}

		return false;
	}

	public abstract void generateFloor(ServerWorldAccess world, BlockPos pos);
	public abstract void generateSide(ServerWorldAccess world, BlockPos pos);
	public abstract void generateRoof(ServerWorldAccess world, BlockPos pos);

	public CaveBiome setRestrictedToBiome()
	{
		this.isBiomeRestricted = true;
		return this;
	}

	public boolean isBiomeRestricted()
	{
		return isBiomeRestricted;
	}

	public boolean canGenerateInBiome(Biome biome)
	{
		return !isBiomeRestricted || biomes.contains(biome);
	}

	public float getRarity()
	{
		return rarity;
	}

	public int getMaxY()
	{
		return maxY;
	}

	public int getMinY()
	{
		return minY;
	}

	public int getMinHeight()
	{
		return minHeight;
	}

	public int getMaxHeight()
	{
		return maxHeight;
	}

	public void generateFinalFloorPass(ServerWorldAccess world, Random random, BlockPos p)
	{
		
	}

	public void generateFinalRoofPass(ServerWorldAccess world, Random random, BlockPos p)
	{
	}

	public void generateFinalWallPass(ServerWorldAccess world, Random random, BlockPos p)
	{
	}
}
