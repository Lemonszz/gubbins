package party.lemons.gubbins.adornment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Adornment
{
	private final Item recipeItem;
	private final int colour;

	public Adornment(Item recipeItem, int colour)
	{
		this.recipeItem = recipeItem;
		this.colour = colour;
	}

	public Item getRecipeItem()
	{
		return recipeItem;
	}

	public boolean matches(ItemStack stack)
	{
		return getRecipeItem() == stack.getItem();
	}

	public int getColour()
	{
		return colour;
	}
}
