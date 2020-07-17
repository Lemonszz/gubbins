package party.lemons.gubbins.misc;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import party.lemons.gubbins.init.GubbinsLootFunctions;

import java.util.Set;

public class BiomeLootFunction extends ConditionalLootFunction
{
	public final Biome destination;
	public final MapIcon.Type decoration;
	public final byte zoom;

	protected BiomeLootFunction(LootCondition[] conditions, Biome biome, byte zoom)
	{
		super(conditions);

		this.destination = biome;
		this.zoom = zoom;
		this.decoration = MapIcon.Type.TARGET_X;
	}

	public Set<LootContextParameter<?>> getRequiredParameters() {
		return ImmutableSet.of(LootContextParameters.POSITION);
	}

	@Override
	protected ItemStack process(ItemStack stack, LootContext context)
	{
		if (stack.getItem() != Items.MAP) {
			return stack;
		} else {
			BlockPos blockPos = context.get(LootContextParameters.POSITION);
			if (blockPos != null) {
				ServerWorld serverWorld = context.getWorld();
				BlockPos blockPos2 = serverWorld.locateBiome(this.destination, blockPos, 6400, 8);
				if (blockPos2 != null) {
					ItemStack itemStack = FilledMapItem.createMap(serverWorld, blockPos2.getX(), blockPos2.getZ(), this.zoom, true, true);
					FilledMapItem.fillExplorationMap(serverWorld, itemStack);
					MapState.addDecorationsTag(itemStack, blockPos2, "+", this.decoration);
					itemStack.setCustomName(new TranslatableText("filled_map.biome"));
					return itemStack;
				}
			}

			return stack;
		}
	}

	@Override
	public LootFunctionType getType()
	{
		return GubbinsLootFunctions.BIOME_MAP;
	}

	public static class Factory extends ConditionalLootFunction.Serializer<BiomeLootFunction>
	{
		public Factory()
		{
			super();
		}

		public void toJson(JsonObject jsonObject, BiomeLootFunction func, JsonSerializationContext ctx)
		{
			super.toJson(jsonObject, func, ctx);
			jsonObject.add("destination", ctx.serialize(Registry.BIOME.getId(func.destination).toString()));
			jsonObject.addProperty("zoom", func.zoom);
		}

		@Override
		public BiomeLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions)
		{
			String biomeId = json.has("destination") ? JsonHelper.getString(json, "destination") : "minecraft:mushroom_fields";
			Biome biome = Registry.BIOME.get(new Identifier(biomeId));

			byte zoom = JsonHelper.getByte(json, "zoom", (byte) 2);
			return new BiomeLootFunction(conditions, biome, zoom);
		}
	}

	public static class Builder extends ConditionalLootFunction.Builder<BiomeLootFunction.Builder> {

		private Biome biome;
		private byte zoom;

		public Builder(Biome biome, byte zoom)
		{
			this.biome = biome;
			this.zoom = zoom;
		}

		protected BiomeLootFunction.Builder getThisBuilder() {
			return this;
		}


		public LootFunction build() {
			return new BiomeLootFunction(this.getConditions(), biome, zoom);
		}
	}
}
