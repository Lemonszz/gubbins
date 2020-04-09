package party.lemons.gubbins.cave;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.gen.cavebiome.*;
import party.lemons.gubbins.util.random.weighted.WeightedRandom;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = CaveBiome.class, registry = "gubbins:cave_biomes")
public class CaveBiomes
{
	public static final MutableRegistry<CaveBiome> REGISTRY = new SimpleRegistry<>();
	static{
		Registry.REGISTRIES.add(new Identifier(Gubbins.MODID, "cave_biomes"), REGISTRY);
	}

	public static final CaveBiome SANDSTONE = new SandstoneCaveBiome(10);
	public static final CaveBiome MOSSY = new MossyCaveBiome(10);
	public static final CaveBiome GRASS = new GrassCaveBiome(10);
	public static final CaveBiome SPIDER = new SpiderCaveBiome(10);
	public static final CaveBiome HARD_DIRT = new HardenedDirtCaveBiome(10);
	public static final CaveBiome PRISMARINE = new PrismarineCaveBiome(10);
	public static final CaveBiome MUSHROOM = new MushroomCaveBiome(3);

	public static CaveBiome selectBiome(IWorld world, BlockPos pos)
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
