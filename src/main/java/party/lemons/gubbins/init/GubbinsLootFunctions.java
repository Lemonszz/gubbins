package party.lemons.gubbins.init;

import net.minecraft.loot.function.LootFunctionType;
import party.lemons.gubbins.misc.BiomeLootFunction;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = LootFunctionType.class, registry = "loot_function_type")
public class GubbinsLootFunctions
{
	public static final LootFunctionType BIOME_MAP = new LootFunctionType(new BiomeLootFunction.Factory());
}
