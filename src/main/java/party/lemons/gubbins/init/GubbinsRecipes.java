package party.lemons.gubbins.init;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import party.lemons.gubbins.recipe.PotionFlaskRecipe;
import party.lemons.gubbins.recipe.serializer.ConditionalRecipeSerializer;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = RecipeSerializer.class, registry = "recipe_serializer")
public class GubbinsRecipes
{
	public static final RecipeSerializer<PotionFlaskRecipe> POTION_FLASK = new SpecialRecipeSerializer<>(PotionFlaskRecipe::new);
	public static final RecipeSerializer<ShapedRecipe> CONDITIONAL_SHAPED = new ConditionalRecipeSerializer<>(RecipeSerializer.SHAPED);
	public static final RecipeSerializer<ShapelessRecipe> CONDITIONAL_SHAPELESS = new ConditionalRecipeSerializer<>(RecipeSerializer.SHAPELESS);

}
