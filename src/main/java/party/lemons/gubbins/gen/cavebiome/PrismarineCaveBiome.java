package party.lemons.gubbins.gen.cavebiome;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;

public class PrismarineCaveBiome extends SimpleCaveBiome
{
	public PrismarineCaveBiome(float rarity)
	{
		super(Blocks.PRISMARINE.getDefaultState(), rarity);

		setRestrictedToBiome();
	}

	@Override
	public boolean canGenerateInBiome(Biome biome)
	{
		return biome.getCategory() == Biome.Category.OCEAN;
	}
}
