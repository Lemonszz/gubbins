package party.lemons.gubbins.util;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import party.lemons.gubbins.util.accessor.ShovelItemAccessor;

/*
General utils that don't really fit anywhere
 */
public class GubbinsUtil
{
	public static void addShovelableBlock(Block block)
	{
		((ShovelItemAccessor) Items.GOLDEN_SHOVEL).addEffectiveBlock(block);
	}

}
