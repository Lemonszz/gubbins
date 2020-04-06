package party.lemons.gubbins.gen.dungeon;

import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public abstract class EnhancedDungeonStructureStart extends StructureStart
{
	public EnhancedDungeonStructureStart(StructureFeature<?> structureFeature, int i, int j, BlockBox blockBox, int k, long l)
	{
		super(structureFeature, i, j, blockBox, k, l);
	}

	public void generateStructure(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox box, ChunkPos pos)
	{
		super.generateStructure(world, chunkGenerator, random, box, pos);
	}
}
