package party.lemons.gubbins.init;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import party.lemons.gubbins.gen.GubbinsOreFeatureConfig;

import java.util.List;

public class GubbinsGeneration
{
	private static final List<Biome> END_BIOMES = Lists.newArrayList();
	private static final List<Biome> NETHER_BIOMES = Lists.newArrayList();

	public static void init()
	{
		gatherBiomeFromCategory(Biome.Category.THEEND, END_BIOMES);
		gatherBiomeFromCategory(Biome.Category.NETHER, NETHER_BIOMES);

		END_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bl->bl.getBlock() == Blocks.END_STONE, GubbinsBlocks.AMETHYST_ORE.getDefaultState(), 2)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(4, 0, 0, 50)))));
		NETHER_BIOMES.forEach(b->b.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, GubbinsFeatures.GUBBINS_ORE.configure(new GubbinsOreFeatureConfig(bl->bl.getBlock() == Blocks.NETHERRACK, GubbinsBlocks.GARNET_ORE.getDefaultState(), 3)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(20, 0, 0, 128)))));

	}

	private static void gatherBiomeFromCategory(Biome.Category category, List<Biome> list)
	{
		Registry.BIOME.forEach(b->{
			if(b.getCategory() == category)
				list.add(b);
		});
	}
}
