package party.lemons.gubbins.init;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = Potion.class, registry = "potion")
public class GubbinsPotions
{
	public static final Potion CLARITY = new Potion(new StatusEffectInstance(GubbinsStatusEffects.CLARITY, 3600));
	public static final Potion LONG_CLARITY = new Potion("clarity", new StatusEffectInstance(GubbinsStatusEffects.CLARITY, 9600));
	public static final Potion STRONG_CLARITY = new Potion("clarity", new StatusEffectInstance(GubbinsStatusEffects.CLARITY, 1800, 1));

	static {
		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.HONEYCOMB, CLARITY);
		BrewingRecipeRegistry.registerPotionRecipe(CLARITY, Items.REDSTONE, LONG_CLARITY);
		BrewingRecipeRegistry.registerPotionRecipe(CLARITY, Items.GLOWSTONE_DUST, STRONG_CLARITY);
	}
}
