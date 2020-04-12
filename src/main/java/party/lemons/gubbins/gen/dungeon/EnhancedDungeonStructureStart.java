package party.lemons.gubbins.gen.dungeon;

import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class EnhancedDungeonStructureStart extends StructureStart
{
	public EnhancedDungeonStructureStart(StructureFeature<?> structureFeature, int i, int j, BlockBox blockBox, int k, long l)
	{
		super(structureFeature, i, j, blockBox, k, l);
	}

}
