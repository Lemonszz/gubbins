package party.lemons.gubbins.init;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = StructureFeature.class, registry = "structure_feature")
public class GubbinsStructures
{
	public static final StructureFeature<?> ENHANCED_DUNGEON = GubbinsFeatures.ENHANCED_DUNGEON;
	public static final StructureFeature<?> CAMPSITE = GubbinsFeatures.CAMPSITE;

	static
	{
		Feature.STRUCTURES.put("gubbins:enhanced_dungeon", ENHANCED_DUNGEON);
		Feature.STRUCTURES.put("gubbins:campsite", CAMPSITE);
	}
}
