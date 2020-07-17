package party.lemons.gubbins.gen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import party.lemons.gubbins.block.WarpedPodBlock;
import party.lemons.gubbins.init.GubbinsBlocks;

import java.util.Random;

public class WarpedPodFeature extends Feature<DefaultFeatureConfig>
{
	public WarpedPodFeature(Codec<DefaultFeatureConfig> configDeserializer)
	{
		super(configDeserializer);
	}

	private boolean place(ServerWorldAccess world, BlockPos pos, Random random)
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
	public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config)
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
