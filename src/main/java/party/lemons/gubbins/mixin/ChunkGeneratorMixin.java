package party.lemons.gubbins.mixin;

import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.ChunkGeneratorAccessor;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin implements ChunkGeneratorAccessor
{
	@Shadow @Final protected IWorld world;

	@Override
	public IWorld getWorld()
	{
		return world;
	}
}
