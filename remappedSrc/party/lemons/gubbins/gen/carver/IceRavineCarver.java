package party.lemons.gubbins.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.RavineCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;
import party.lemons.gubbins.init.GubbinsCarvers;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class IceRavineCarver extends RavineCarver
{
	public IceRavineCarver(Codec<ProbabilityConfig> configDeserializer)
	{
		super(configDeserializer);
	}

	@Override
	protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, MutableBoolean mutableBoolean) {
		return GubbinsCarvers.ICE_CAVE.iceCarveAtPoint(chunk, posToBiome, carvingMask, random, mutable, mutable2, mutable3,seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ, mutableBoolean);
	}
}
