package party.lemons.gubbins.init;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.apache.commons.lang3.ArrayUtils;
import party.lemons.gubbins.gen.GubbinsOreFeatureConfig;

import java.util.List;

public class GubbinsGeneration
{
	private static final List<Biome> AMETHYST_BIOMES = Lists.newArrayList();
	private static final List<Biome> GARNET_BIOMES = Lists.newArrayList();
	private static final List<Biome> ONYX_BIOMES = Lists.newArrayList();

	public static void init()
	{
		gatherBiomeFromCategory(Biome.Category.THEEND, AMETHYST_BIOMES);
		gatherBiomeFromCategory(Biome.Category.NETHER, GARNET_BIOMES);
		gatherBiomeFromCategory(Biome.Category.OCEAN, ONYX_BIOMES);

		AMETHYST_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bl->bl.getBlock() == Blocks.END_STONE, GubbinsBlocks.AMETHYST_ORE.getDefaultState(), 2)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(4, 0, 0, 50)))));
		GARNET_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bl->bl.getBlock() == Blocks.NETHERRACK, GubbinsBlocks.GARNET_ORE.getDefaultState(), 3)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(6, 0, 0, 128)))));
		ONYX_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(OreFeatureConfig.Target.NATURAL_STONE.getCondition(), GubbinsBlocks.ONYX_ORE.getDefaultState(), 3)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(2, 0, 0, 40)))));
	}

	private static void gatherBiomeFromCategory(Biome.Category category, List<Biome> list, Biome... exceptions)
	{
		Registry.BIOME.forEach(b->{
			if(b.getCategory() == category && !ArrayUtils.contains(exceptions, b))
				list.add(b);
		});
	}
}
