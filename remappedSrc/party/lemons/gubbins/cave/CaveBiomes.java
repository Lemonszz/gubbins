package party.lemons.gubbins.cave;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.gen.cavebiome.*;
import party.lemons.gubbins.util.random.weighted.WeightedRandom;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = CaveBiome.class, registry = "gubbins:cave_biomes")
public class CaveBiomes
{
	public static final RegistryKey<Registry<CaveBiome>> REG_KEY = RegistryKey.ofRegistry(new Identifier(Gubbins.MODID, "cave_biomes"));
	public static final MutableRegistry<CaveBiome> REGISTRY = new SimpleRegistry<>(REG_KEY, Lifecycle.stable());
	static{
		((MutableRegistry)Registry.REGISTRIES).add(REG_KEY, REGISTRY);
	}

	public static final CaveBiome SANDSTONE = new SandstoneCaveBiome(Gubbins.config.CAVE_BIOMES.SANDSTONE.weight);
	public static final CaveBiome MOSSY = new MossyCaveBiome(Gubbins.config.CAVE_BIOMES.MOSSY.weight);
	public static final CaveBiome GRASS = new GrassCaveBiome(Gubbins.config.CAVE_BIOMES.GRASS.weight);
	public static final CaveBiome SPIDER = new SpiderCaveBiome(Gubbins.config.CAVE_BIOMES.SPIDER.weight);
	public static final CaveBiome HARD_DIRT = new HardenedDirtCaveBiome(Gubbins.config.CAVE_BIOMES.HARD_DIRT.weight);
	public static final CaveBiome PRISMARINE = new PrismarineCaveBiome(Gubbins.config.CAVE_BIOMES.PRISMARINE.weight);
	public static final CaveBiome MUSHROOM = new MushroomCaveBiome(Gubbins.config.CAVE_BIOMES.MUSHROOM.weight);

	public static CaveBiome selectBiome(ServerWorldAccess world, BlockPos pos)
	{
		WeightedRandom<CaveBiome> selector = new WeightedRandom<>();
		Biome worldBiome = world.getBiome(pos);
		for(CaveBiome bi : REGISTRY)
		{
			if(bi.canGenerateInBiome(worldBiome))
				selector.add(bi, bi.rarity);
		}

		return selector.select();
	}
}
