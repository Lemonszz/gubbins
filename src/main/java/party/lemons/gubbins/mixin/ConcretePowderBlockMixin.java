package party.lemons.gubbins.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.ConcretePowderBlockAccessor;

@Mixin(ConcretePowderBlock.class)
public class ConcretePowderBlockMixin implements ConcretePowderBlockAccessor
{
	@Shadow @Final private BlockState hardenedState;

	@Override
	public BlockState getHardenedState()
	{
		return hardenedState;
	}
}
