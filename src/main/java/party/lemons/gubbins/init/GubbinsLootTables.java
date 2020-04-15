package party.lemons.gubbins.init;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ExplorationMapLootFunction;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biomes;
import org.apache.commons.lang3.ArrayUtils;
import party.lemons.gubbins.misc.BiomeLootFunction;

import java.util.List;

public class GubbinsLootTables
{
	private static final List<LootTableInsert> INSERTS = Lists.newArrayList();


	public static void init()
	{
		FabricLootPoolBuilder TELESCOPE_LOOT_PIRATEY = FabricLootPoolBuilder.builder()
				.withRolls(ConstantLootTableRange.create(1))
				.withEntry(ItemEntry.builder(GubbinsItems.TELESCOPE))
				.withCondition(RandomChanceLootCondition.builder(0.25F).build());

		insert(new LootTableInsert(TELESCOPE_LOOT_PIRATEY,
				new Identifier("minecraft", "chests/buried_treasure"),
				new Identifier("minecraft", "chests/shipwreck_supply"),
				new Identifier("minecraft", "chests/shipwreck_map"),
				new Identifier("minecraft", "chests/shipwreck_treasure"),
				new Identifier("minecraft", "chests/underwater_ruin_big"),
				new Identifier("minecraft", "chests/underwater_ruin_small"),
				new Identifier("minecraft", "gameplay/fishing/treasure")
		));

		FabricLootPoolBuilder JUNGLE_TEMPLE_MAP = FabricLootPoolBuilder.builder()
				.withRolls(ConstantLootTableRange.create(1))
				.withCondition(RandomChanceLootCondition.builder(0.10F).build())
				.withEntry(ItemEntry.builder(Items.MAP)
						.withFunction(
								ExplorationMapLootFunction.create()
										.withSkipExistingChunks(false)
										.withDestination("Jungle_Pyramid")
										.withDecoration(MapIcon.Type.MANSION)
										.withZoom((byte) 1)
						)
				.build());

		insert(new LootTableInsert(JUNGLE_TEMPLE_MAP,
				new Identifier("minecraft", "gameplay/fishing/treasure"),
				new Identifier("minecraft", "chests/simple_dungeon")
		));


		FabricLootPoolBuilder MUSHROOM_MAP = FabricLootPoolBuilder.builder()
				.withRolls(ConstantLootTableRange.create(1))
				.withCondition(RandomChanceLootCondition.builder(0.05F).build())
				.withEntry(ItemEntry.builder(Items.MAP)
						.withFunction(new BiomeLootFunction.Builder(Biomes.MUSHROOM_FIELDS, (byte)2)).build());

		insert(new LootTableInsert(MUSHROOM_MAP,
				new Identifier("minecraft", "gameplay/fishing/treasure"),
				new Identifier("minecraft", "chests/simple_dungeon")
		));

		LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, identifier, supplier, lootTableSetter) -> {
			INSERTS.forEach(i->{
				if(ArrayUtils.contains(i.tables, identifier))
				{
					i.insert(supplier);
				}
			});
		}));
	}

	public static void insert(LootTableInsert insert)
	{
		INSERTS.add(insert);
	}

	public static class LootTableInsert
	{
		public final Identifier[] tables;
		public final FabricLootPoolBuilder lootPool;

		public LootTableInsert(FabricLootPoolBuilder lootPool, Identifier... tables)
		{
			this.tables = tables;
			this.lootPool = lootPool;
		}

		public void insert(FabricLootSupplierBuilder supplier)
		{
			supplier.withPool(lootPool);
		}
	}
}
