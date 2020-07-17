package party.lemons.gubbins.mixin;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.AbstractBlockAccessor;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin implements AbstractBlockAccessor
{
	@Shadow @Final protected boolean collidable;

	@Override
	public boolean isCollideable()
	{
		return collidable;
	}
}
