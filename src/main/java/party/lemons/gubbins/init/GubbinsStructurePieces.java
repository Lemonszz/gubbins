package party.lemons.gubbins.init;

import net.minecraft.structure.StructurePieceType;
import party.lemons.gubbins.gen.camp.CampGenerator;
import party.lemons.gubbins.gen.dungeon.EnhancedDungeonGenerator;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = StructurePieceType.class, registry = "structure_piece")
public class GubbinsStructurePieces
{
	public static final StructurePieceType ENHANCED_DUNGEON = EnhancedDungeonGenerator.Piece::new;
	public static final StructurePieceType CAMPSITE = CampGenerator.Piece::new;
}
