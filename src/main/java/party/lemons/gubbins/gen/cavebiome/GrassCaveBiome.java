package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import party.lemons.gubbins.cave.CaveBiome;

import java.util.Random;

public class GrassCaveBiome extends CaveBiome
{
	public GrassCaveBiome(float rarity)
	{
		super(rarity);

		this.setRestrictedToBiome();
		this.doFinalFloorPass = true;
	}

	@Override
	public void generateFloor(IWorld world, BlockPos pos)
	{
		world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState(), 2);
		if(world.getRandom().nextFloat() < 0.7F && world.getBlockState(pos.down()).isOpaqueFullCube(world, pos.down()))
		{
			world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 2);
		}
	}

	@Override
	public void generateSide(IWorld world, BlockPos pos)
	{

	}

	@Override
	public void generateRoof(IWorld world, BlockPos pos)
	{

	}

	@Override
	public boolean canGenerateInBiome(Biome biome)
	{
		Biome.Category category = biome.getCategory();
		return category == Biome.Category.JUNGLE || category == Biome.Category.FOREST;
	}

	@Override
	public void generateFinalFloorPass(IWorld world, Random random, BlockPos p)
	{
		Block currentBlock = world.getBlockState(p).getBlock();
		if(currentBlock== Blocks.GRASS_BLOCK && random.nextBoolean())
		{
			BlockPos up = p.up();
			if(world.getBlockState(up).isAir())
			{
				BlockState state = random.nextBoolean() ? FLOWERS[random.nextInt(FLOWERS.length)].getDefaultState() : Blocks.GRASS.getDefaultState();
				world.setBlockState(up, state, 2);
			}
		}
	}
	public Block[] FLOWERS = new Block[]{
			Blocks.POPPY,
			Blocks.AZURE_BLUET,
			Blocks.ALLIUM,
			Blocks.RED_TULIP,
			Blocks.ORANGE_TULIP,
			Blocks.WHITE_TULIP,
			Blocks.PINK_TULIP,
			Blocks.OXEYE_DAISY,
			Blocks.CORNFLOWER,
			Blocks.LILY_OF_THE_VALLEY
	};
}
