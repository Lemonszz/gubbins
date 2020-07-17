package party.lemons.gubbins.util;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class BiomeUtil
{
	public static List<Biome> gatherBiomeFromCategory(Biome.Category category, List<Biome> list, Biome... exceptions)
	{
		Registry.BIOME.forEach(b->{
			if(b.getCategory() == category && !ArrayUtils.contains(exceptions, b))
				list.add(b);
		});

		return list;
	}
}
