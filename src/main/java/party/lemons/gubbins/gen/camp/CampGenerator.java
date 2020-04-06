package party.lemons.gubbins.gen.camp;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import party.lemons.gubbins.gen.JigsawConfig;
import party.lemons.gubbins.init.GubbinsStructurePieces;

import java.util.List;
import java.util.Random;

public class CampGenerator
{
	public static void addPieces(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, BlockPos pos, List<StructurePiece> pieces, ChunkRandom random, JigsawConfig config)
	{
		StructurePoolBasedGenerator.addPieces(config.startPool, config.size, Piece::new, chunkGenerator, structureManager, pos, pieces, random);
	}

	public static class Piece extends PoolStructurePiece
	{
		public Piece(StructureManager structureManager, StructurePoolElement structurePoolElement, BlockPos blockPos, int i, BlockRotation blockRotation, BlockBox blockBox) {
			super(GubbinsStructurePieces.CAMPSITE, structureManager, structurePoolElement, blockPos, i, blockRotation, blockBox);
		}

		public Piece(StructureManager structureManager, CompoundTag compoundTag) {
			super(structureManager, compoundTag, GubbinsStructurePieces.CAMPSITE);
		}

		@Override
		public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos pos, BlockPos blockPos)
		{
			return super.generate(world, generator, random, box, pos, blockPos);
		}
	}
}
