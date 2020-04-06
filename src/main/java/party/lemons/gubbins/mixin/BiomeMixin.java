package party.lemons.gubbins.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.BiomeAccessor;

import java.util.List;
import java.util.Map;

@Mixin(Biome.class)
public class BiomeMixin implements BiomeAccessor
{
	@Shadow @Final
	protected Map<GenerationStep.Carver, List<ConfiguredCarver<?>>> carvers;

	@Override
	public void clearCarvers()
	{
		carvers.clear();
	}
}
