package party.lemons.gubbins.gen.camp;

import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import party.lemons.gubbins.gen.JigsawConfig;
import party.lemons.gubbins.init.GubbinsFeatures;

import java.util.Random;
import java.util.function.Function;

public class CampFeature extends StructureFeature<JigsawConfig>
{
	public CampFeature(Function<Dynamic<?>, ? extends JigsawConfig> function)
	{
		super(function);
	}

	@Override
	protected ChunkPos getStart(ChunkGenerator<?> chunkGenerator, Random random, int chunkX, int chunkZ, int distanceScale, int seperationScale) {
		int distance = chunkGenerator.getConfig().getVillageDistance();
		int separation = chunkGenerator.getConfig().getVillageSeparation();
		int xDistance = chunkX + distance * distanceScale;
		int zDistance = chunkZ + distance * seperationScale;

		int q = xDistance < 0 ? xDistance - distance + 1 : xDistance;
		int r = zDistance < 0 ? zDistance - distance + 1 : zDistance;

		int xChunk = q / distance;
		int zChunk = r / distance;

		((ChunkRandom)random).setRegionSeed(chunkGenerator.getSeed(), xChunk, zChunk, 696969);
		xChunk *= distance;
		zChunk *= distance;

		xChunk += random.nextInt(distance - separation);
		zChunk += random.nextInt(distance - separation);

		return new ChunkPos(xChunk, zChunk);
	}

	@Override
	public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkX, int chunkZ, Biome biome)
	{
		ChunkPos chunkPos = this.getStart(chunkGenerator, random, chunkX, chunkZ, 0, 0);
		return (chunkX == chunkPos.x && chunkZ == chunkPos.z) && chunkGenerator.hasStructure(biome, this);
	}

	@Override
	public StructureStartFactory getStructureStartFactory()
	{
		return CampFeature.Start::new;
	}

	@Override
	public String getName()
	{
		return "gubbins:campsite";
	}

	@Override
	public int getRadius()
	{
		return 3;
	}

	public static class Start extends StructureStart
	{
		public Start(StructureFeature<?> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
			super(structureFeature, i, j, blockBox, k, l);
		}

		public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
			JigsawConfig cfg = chunkGenerator.getStructureConfig(biome, GubbinsFeatures.CAMPSITE);
			BlockPos blockPos = new BlockPos(x * 16, 0, z * 16);
			CampGenerator.addPieces(chunkGenerator, structureManager, blockPos, this.children, this.random, cfg);
			this.setBoundingBoxFromChildren();

		}
	}
}