package party.lemons.gubbins.gen.cavebiome;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import party.lemons.gubbins.cave.CaveBiome;
import party.lemons.gubbins.cave.CaveBiomes;
import party.lemons.gubbins.util.random.RandomUtil;

import java.util.List;
import java.util.Random;

public class CaveBiomeFeature extends Feature<DefaultFeatureConfig>
{
	PerlinNoiseSampler NOISE = new PerlinNoiseSampler(new Random(42069));

	public CaveBiomeFeature(Codec<DefaultFeatureConfig> codec)
	{
		super(codec);
	}

	@Override
	public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config)
	{
		CaveBiome biome = CaveBiomes.selectBiome(world, pos);
		if(biome == null)
			return false;

		int height = RandomUtil.getIntRange(biome.getMinHeight(), biome.getMaxHeight(), random);
		int positionY = RandomUtil.getIntRange(biome.getMinY(), biome.getMaxY(), random);

		int sizeX = RandomUtil.getIntRange(15, 50, random);
		int sizeZ = RandomUtil.getIntRange(15, 50, random);
		BlockPos.Mutable genPos = new BlockPos.Mutable(pos.getX(), positionY, pos.getZ());

		CavePositions positions = new CavePositions();

		for(int x = 0; x < sizeX; x++)
		{
			for(int z = 0; z < sizeZ; z++)
			{
				for(int y = 0; y < height; y++)
				{
					boolean sideEdge = x == 0 || x == sizeX - 1 || z == 0 || z == sizeZ - 1;
					boolean roofEdge =  y == 0;
					boolean floorEdge = y == height - 1;
					genPos.set(pos.getX() + x, positionY - y, pos.getZ() + z);
					biome.generate(random, world, genPos, positions, floorEdge, roofEdge, sideEdge);
				}
			}
		}

		if(biome.doFinalFloorPass)
			positions.floorPositions.forEach(p->biome.generateFinalFloorPass(world, random, p));

		if(biome.doFinalRoofPass)
			positions.roofPositions.forEach(p->biome.generateFinalRoofPass(world, random, p));

		if(biome.doFinalWallPass)
			positions.sidePositions.forEach(p->biome.generateFinalWallPass(world, random, p));

		return true;	}

	public static class CavePositions
	{
		public final List<BlockPos> floorPositions = Lists.newArrayList();
		public final List<BlockPos> sidePositions = Lists.newArrayList();
		public final List<BlockPos> roofPositions = Lists.newArrayList();
	}
}
