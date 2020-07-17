package party.lemons.gubbins.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DisplayItem extends Item
{
	public DisplayItem()
	{
		super(new Item.Settings().maxCount(1));
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		stack.setCount(0);
	}
}
