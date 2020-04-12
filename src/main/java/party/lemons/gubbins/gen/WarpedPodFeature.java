package party.lemons.gubbins.gen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import party.lemons.gubbins.block.WarpedPodBlock;
import party.lemons.gubbins.init.GubbinsBlocks;

import java.util.Random;
import java.util.function.Function;

public class WarpedPodFeature extends Feature<DefaultFeatureConfig>
{
	public WarpedPodFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer)
	{
		super(configDeserializer);
	}

	private boolean place(IWorld world, BlockPos pos, Random random)
	{
		if (!world.isAir(pos)) {
			return false;
		} else
		{
			Block block = world.getBlockState(pos.up()).getBlock();

			//TODO Tag?
			if(block != Blocks.NETHERRACK && block != Blocks.BASALT && block != Blocks.BLACKSTONE && block != Blocks.WARPED_NYLIUM)
			{
				return false;
			}else
			{
				world.setBlockState(pos, GubbinsBlocks.WARPED_POD.getDefaultState().with(WarpedPodBlock.AGE, random.nextInt(3)), 2);
			}
		}
		return true;
	}

	@Override
	public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config)
	{
		if(place(world, pos, random))
		{
			for(int i = 0; i < 50; i++)
			{
				BlockPos p = pos.add(random.nextInt(9) - random.nextInt(9), random.nextInt(5) - random.nextInt(5), random.nextInt(9) - random.nextInt(9));
				place(world, p, random);
			}
			return true;
		}
		return false;
	}
}
