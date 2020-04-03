package party.lemons.gubbins.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.boat.BoatTypes;

public class DisplayItem extends Item
{
	public DisplayItem()
	{
		super(new Item.Settings().maxCount(1));
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		stack.setCount(0);

		Gubbins.LOGGER.info(BoatTypes.REGISTRY.isEmpty());
	}
}
