package party.lemons.gubbins.recipe.serializer;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.recipe.serializer.condition.RecipeCondition;

public class ConditionalRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T>
{
	private final RecipeSerializer<T> baseSerializer;

	public ConditionalRecipeSerializer(RecipeSerializer<T> baseSerializer)
	{
		this.baseSerializer = baseSerializer;
	}

	@Override
	public T read(Identifier id, JsonObject json)
	{
		JsonObject conditionObj = json.getAsJsonObject("condition");
		String type = conditionObj.get("type").getAsString();

		RecipeCondition condition = null;

		switch(type)    //TODO: load this from elsewhere
		{
			case "feature_enabled":
					Identifier featureId  = new Identifier(conditionObj.get("feature").getAsString());
			//		condition = new FeatureEnabledCondition(featureId);
				break;
		}

		if(condition == null || !condition.passes())
			return null;

		return baseSerializer.read(id, json);
	}

	@Override
	public T read(Identifier id, PacketByteBuf buf)
	{
		return baseSerializer.read(id, buf);
	}

	@Override
	public void write(PacketByteBuf buf, T recipe)
	{
		baseSerializer.write(buf, recipe);
	}
}
