package party.lemons.gubbins.util.registry;

import net.minecraft.item.Item;
import party.lemons.gubbins.init.GubbinsItems;

public interface BlockWithItem
{
	default boolean hasItem()
	{
		return true;
	}

	default Item.Settings makeItemSettings(){
		return GubbinsItems.settings();
	}
}
