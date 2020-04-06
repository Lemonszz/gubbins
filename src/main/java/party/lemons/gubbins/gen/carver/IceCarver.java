package party.lemons.gubbins.gen.carver;

import com.google.common.collect.Maps;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class IceCarver extends CaveCarver
{
	PerlinNoiseSampler NOISE = new PerlinNoiseSampler(new Random(42069));
	PerlinNoiseSampler NOISE_2 = new PerlinNoiseSampler(new Random(69420));

	public IceCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> function, int i)
	{
		super(function, i);
		this.alwaysCarvableBlocks = new HashSet<>(this.alwaysCarvableBlocks);
		this.alwaysCarvableBlocks.add(Blocks.ICE);
	}

	public boolean iceCarveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, AtomicBoolean foundSurface) {
		int i = relativeX | relativeZ << 4 | y << 8;
		if (carvingMask.get(i)) {
			return false;
		} else {
			carvingMask.set(i);
			mutable.set(x, y, z);
			BlockState blockState = chunk.getBlockState(mutable);
			BlockState blockState2 = chunk.getBlockState(mutable2.set(mutable, Direction.UP));
			boolean icy = false;

			for(Direction d : Direction.values())
			{
				if(OreFeatureConfig.Target.NATURAL_STONE.getCondition().test(chunk.getBlockState(mutable2.set(mutable, d))))
				{
					mutable3.set(mutable, d);
					double n2 = NOISE_2.sample(mutable3.getX() / 50F, mutable3.getY() / 50F, mutable3.getZ() / 50F, mutable.getX() / 50F, mutable.getY() / 50F);

					BlockState STATE;
					if(n2 > 0.0)
					{
						double n = NOISE.sample(mutable3.getX(), mutable3.getY(), mutable3.getZ(), mutable.getX(), mutable.getY());
						STATE = n < 0.2 ? Blocks.ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState();
						if(d == Direction.DOWN)
							STATE = Blocks.PACKED_ICE.getDefaultState();

						icy = true;
					}
					else
					{
						STATE = Blocks.STONE.getDefaultState();
					}
					chunk.setBlockState(mutable3, STATE, false);
				}
			}

			if (!this.canCarveBlock(blockState, blockState2)) {
				return false;
			} else {
				if (y < 11) {
					chunk.setBlockState(mutable, icy ? WATER.getBlockState() : LAVA.getBlockState(), false);
				} else {
					chunk.setBlockState(mutable, CAVE_AIR, false);
				}

				return true;
			}
		}
	}

	protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, AtomicBoolean foundSurface) {
		return iceCarveAtPoint(chunk, posToBiome, carvingMask, random, mutable, mutable2, mutable3,seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ, foundSurface);
	}
}
