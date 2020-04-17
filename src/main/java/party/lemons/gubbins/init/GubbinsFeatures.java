package party.lemons.gubbins.init;

import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import party.lemons.gubbins.gen.GubbinsOreFeature;
import party.lemons.gubbins.gen.WarpedPodFeature;
import party.lemons.gubbins.gen.cavebiome.CaveBiomeFeature;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = Feature.class, registry = "feature")
public class GubbinsFeatures
{
	public static final GubbinsOreFeature GUBBINS_ORE = new GubbinsOreFeature();
	public static final CaveBiomeFeature CAVE_BIOME = new CaveBiomeFeature(DefaultFeatureConfig::deserialize);
	public static final WarpedPodFeature WARPED_POD = new WarpedPodFeature(DefaultFeatureConfig::deserialize);
}
