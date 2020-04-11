package party.lemons.gubbins.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.init.GubbinsRecipes;

public class PotionFlaskRecipe extends SpecialCraftingRecipe
{
	public PotionFlaskRecipe(Identifier identifier) {
		super(identifier);
	}

	public PotionCount getOutput(CraftingInventory craftingInventory)
	{
		Potion potion = null;
		int count = 0;
		int potionSlot = -1;

		for(int i = 0; i < craftingInventory.getWidth(); ++i) {
			for(int j = 0; j < craftingInventory.getHeight(); ++j) {
				int slot = i + j * craftingInventory.getWidth();
				ItemStack itemStack = craftingInventory.getStack(slot);

				Item item = itemStack.getItem();
				if(item != GubbinsItems.POTION_FLASK && item != Items.POTION && item != Items.AIR)
					return null;

				if (item == GubbinsItems.POTION_FLASK)
				{
					if(potionSlot != -1)
						return null;

					potionSlot = slot;

					Potion currentPotion = PotionUtil.getPotion(itemStack);
					if(potion != null && currentPotion != Potions.EMPTY && currentPotion != potion)
						return null;

					if(potion == null && currentPotion != Potions.EMPTY)
						potion = currentPotion;


				}
				else if(item == Items.POTION)
				{
					count++;
					if(potion == null)
						potion = PotionUtil.getPotion(itemStack);
					else if(PotionUtil.getPotion(itemStack) != potion)
						return null;
				}
				else if(item != Items.AIR)
					return null;
			}
		}

		if(potionSlot == -1 || count == 0 || potion == Potions.EMPTY)
			return null;

		return new PotionCount(potion, count, potionSlot);
	}

	public boolean matches(CraftingInventory craftingInventory, World world) {
		return getOutput(craftingInventory) != null;
	}

	public ItemStack craft(CraftingInventory craftingInventory) {
		PotionCount out = getOutput(craftingInventory);

		if(out == null)
			return ItemStack.EMPTY;

		ItemStack stack = craftingInventory.getStack(out.slot).copy();

		PotionUtil.setPotion(stack, out.potion);

		int totalDoses = GubbinsItems.POTION_FLASK.getUsages(stack) + out.count;
		if(totalDoses > Gubbins.config.POTION_FLASK.maxSize)
			return ItemStack.EMPTY;

		GubbinsItems.POTION_FLASK.setUsages(stack, totalDoses);

		return stack;
	}

	@Environment(EnvType.CLIENT)
	public boolean fits(int width, int height) {
		return true;
	}

	public RecipeSerializer<?> getSerializer() {
		return GubbinsRecipes.POTION_FLASK;
	}

	private static class PotionCount
	{
		public Potion potion;
		public int count;
		public int slot;

		public PotionCount(Potion potion, int count, int slot)
		{
			this.potion = potion;
			this.count = count;
			this.slot = slot;
		}
	}
}