package party.lemons.gubbins.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.apache.commons.lang3.ArrayUtils;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.gen.GubbinsOreFeatureConfig;
import party.lemons.gubbins.gen.dungeon.EnhancedDungeonElement;
import party.lemons.gubbins.util.accessor.BiomeAccessor;

import java.util.List;

public class GubbinsGeneration
{
	private static final List<Biome> AMETHYST_BIOMES = Lists.newArrayList();
	private static final List<Biome> GARNET_BIOMES = Lists.newArrayList();
	private static final List<Biome> ONYX_BIOMES = Lists.newArrayList();
	private static final List<Biome> ICY = Lists.newArrayList();

	public static void init()
	{
		initStructures();

		gatherBiomeFromCategory(Biome.Category.THEEND, AMETHYST_BIOMES);
		gatherBiomeFromCategory(Biome.Category.NETHER, GARNET_BIOMES);
		gatherBiomeFromCategory(Biome.Category.OCEAN, ONYX_BIOMES);
		gatherBiomeFromCategory(Biome.Category.ICY, ICY);
		ICY.add(Biomes.SNOWY_TAIGA);

		if(Gubbins.config.ORE.enableAmethyst)
			AMETHYST_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bl->bl.getBlock() == Blocks.END_STONE, GubbinsBlocks.AMETHYST_ORE.getDefaultState(), 2)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(4, 0, 0, 50)))));
		if(Gubbins.config.ORE.enableGarnet)
			GARNET_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bl->bl.getBlock() == Blocks.NETHERRACK, GubbinsBlocks.GARNET_ORE.getDefaultState(), 3)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(6, 0, 0, 128)))));
		if(Gubbins.config.ORE.enableOnyx)
			ONYX_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE.getCondition(), GubbinsBlocks.ONYX_ORE.getDefaultState(), 3)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(2, 0, 0, 40)))));

		//Warped Pods
		Biomes.WARPED_FOREST.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, GubbinsFeatures.WARPED_POD.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(10, 0, 0, 128))));

		if(Gubbins.config.ICE_CAVES.enabled)
		{
			ICY.forEach(b->
			{
				((BiomeAccessor) b).clearCarvers();
				b.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(GubbinsCarvers.ICE_CAVE, new ProbabilityConfig(0.14285715F)));
				b.addCarver(GenerationStep.Carver.AIR, Biome.configureCarver(GubbinsCarvers.ICE_RAVINE, new ProbabilityConfig(0.02F)));

				b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bs->(bs.getBlock() == Blocks.ICE || bs.getBlock() == Blocks.PACKED_ICE), GubbinsBlocks.COAL_ICE_ORE.getDefaultState(), 17)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(20, 0, 0, 128))));
				b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bs->(bs.getBlock() == Blocks.ICE || bs.getBlock() == Blocks.PACKED_ICE), GubbinsBlocks.IRON_ICE_ORE.getDefaultState(), 9)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(20, 0, 0, 64))));
				b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bs->(bs.getBlock() == Blocks.ICE || bs.getBlock() == Blocks.PACKED_ICE), GubbinsBlocks.GOLD_ICE_ORE.getDefaultState(), 9)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(2, 0, 0, 32))));
				b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bs->(bs.getBlock() == Blocks.ICE || bs.getBlock() == Blocks.PACKED_ICE), GubbinsBlocks.REDSTONE_ICE_ORE.getDefaultState(), 8)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(8, 0, 0, 16))));
				b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bs->(bs.getBlock() == Blocks.ICE || bs.getBlock() == Blocks.PACKED_ICE), GubbinsBlocks.DIAMOND_ICE_ORE.getDefaultState(), 8)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(1, 0, 0, 16))));
			});
		}

		if(Gubbins.config.MISC.enableMansionInSnowTundra)
			Biomes.SNOWY_TUNDRA.addStructureFeature(Feature.WOODLAND_MANSION.configure(FeatureConfig.DEFAULT));

		//Cave Biomes
		if(Gubbins.config.CAVE_BIOMES.enabled)
		{
			Registry.BIOME.forEach(b->
			{
				b.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, GubbinsFeatures.CAVE_BIOME.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(10))));
			});
		}

		//TODO: make these work properly
	/*	CAMP.forEach(b->{
	//		b.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, GubbinsFeatures.CAMPSITE.configure(new JigsawConfig("gubbins:camp", 6)));
	//		b.addStructureFeature(GubbinsFeatures.CAMPSITE.configure(new JigsawConfig("gubbins:camp", 50)));

		});

		Registry.BIOME.forEach(b->
		{
	//		b.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, GubbinsFeatures.ENHANCED_DUNGEON.configure(new JigsawConfig("gubbins:dungeon/room", 6)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(1, 0, 0, 60))));
	//		b.addStructureFeature(GubbinsFeatures.ENHANCED_DUNGEON.configure(new JigsawConfig("gubbins:dungeon/room", 6)));
		});*/
	}

	private static void initStructures()
	{
		ImmutableList<StructureProcessor> roomProcessor = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new StructureProcessorRule(new BlockMatchRuleTest(Blocks.WATER), AlwaysTrueRuleTest.INSTANCE, Blocks.CAVE_AIR.getDefaultState()), new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.getDefaultState()))));
		ImmutableList<StructureProcessor> basicProcessor = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of()));
		ImmutableList rooms = ImmutableList.of(
				new Pair(new EnhancedDungeonElement("gubbins:dungeon/rooms/dungeon_room_1", roomProcessor), 2),
				new Pair(new EnhancedDungeonElement("gubbins:dungeon/rooms/dungeon_room_2", roomProcessor), 2),
				new Pair(new EnhancedDungeonElement("gubbins:dungeon/rooms/dungeon_room_3", roomProcessor), 2),
				new Pair(new EnhancedDungeonElement("gubbins:dungeon/rooms/dungeon_room_4", roomProcessor), 2)
		);
		StructurePool pool = new StructurePool(
				new Identifier(Gubbins.MODID, "dungeon/room"),
				new Identifier(Gubbins.MODID, "dungeon/terminators"),
				rooms,
				StructurePool.Projection.RIGID
		);


		StructurePool CAMP_BASE = new StructurePool(
			new Identifier(Gubbins.MODID, "camp"), new Identifier(Gubbins.MODID, "camp/decoration"),
			ImmutableList.of(new Pair(new SinglePoolElement("gubbins:camp/base/campfire_base_1", basicProcessor), 1)),
				StructurePool.Projection.RIGID
		);

		StructurePool CAMP_DECO = new StructurePool(
				new Identifier(Gubbins.MODID, "camp/decoration"), new Identifier("village/snowy/terminators"),
				ImmutableList.of(
						new Pair(new SinglePoolElement("gubbins:camp/decoration/campfire_decoration_1", basicProcessor), 2),
						new Pair(new SinglePoolElement("gubbins:camp/decoration/campfire_decoration_2", basicProcessor), 2),
						new Pair(new SinglePoolElement("gubbins:camp/decoration/campfire_decoration_3", basicProcessor), 2),
						new Pair(new SinglePoolElement("gubbins:camp/decoration/campfire_decoration_4", basicProcessor), 2),
						new Pair(new SinglePoolElement("gubbins:camp/decoration/tent_1", basicProcessor), 2),
						new Pair(new SinglePoolElement("gubbins:camp/decoration/tent_2", basicProcessor), 2)
				),
				StructurePool.Projection.RIGID
		);

		StructurePool CAMP_STRUCTURE = new StructurePool(
				new Identifier(Gubbins.MODID, "camp/structures"), new Identifier(Gubbins.MODID, "camp/decoration"),
				ImmutableList.of(
					//	new Pair(new SinglePoolElement("gubbins:camp/structures/camp_structure_1", basicProcessor), 2),
					//	new Pair(new SinglePoolElement("gubbins:camp/structures/structure_2", basicProcessor), 2)
						new Pair(new SinglePoolElement("gubbins:camp/structures/structure_3", basicProcessor), 3)
				),
				StructurePool.Projection.RIGID
		);

		StructurePoolBasedGenerator.REGISTRY.add(pool);
		StructurePoolBasedGenerator.REGISTRY.add(CAMP_BASE);
		StructurePoolBasedGenerator.REGISTRY.add(CAMP_DECO);
		StructurePoolBasedGenerator.REGISTRY.add(CAMP_STRUCTURE);
	}

	private static void gatherBiomeFromCategory(Biome.Category category, List<Biome> list, Biome... exceptions)
	{
		Registry.BIOME.forEach(b->{
			if(b.getCategory() == category && !ArrayUtils.contains(exceptions, b))
				list.add(b);
		});
	}
}
