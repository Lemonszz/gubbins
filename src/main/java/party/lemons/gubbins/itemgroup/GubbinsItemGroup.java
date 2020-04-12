package party.lemons.gubbins.itemgroup;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.init.GubbinsBlocks;
import party.lemons.gubbins.init.GubbinsItems;

import java.util.List;

public class GubbinsItemGroup extends TabbedItemGroup
{
	public GubbinsItemGroup(Identifier id)
	{
		super(id);
	}

	@Override
	public void initTabs(List<ItemTab> tabs)
	{
		tabs.add(new ItemTab(new ItemStack(GubbinsBlocks.BASALT_BRICKS.asItem()), "decoration", GubbinsItems.DECORATION));
		tabs.add(new ItemTab(new ItemStack(GubbinsBlocks.ONYX_ORE.asItem()), "ore", GubbinsItems.ORE));
		tabs.add(new ItemTab(new ItemStack(GubbinsItems.QUIVER),"utility", GubbinsItems.UTILITY));
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(GubbinsItems.TELESCOPE);
	}
}
