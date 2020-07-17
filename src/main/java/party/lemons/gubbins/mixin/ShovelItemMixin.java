package party.lemons.gubbins.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.ShovelItemAccessor;

import java.util.Set;

@Mixin(ShovelItem.class)
public class ShovelItemMixin implements ShovelItemAccessor
{

	@Shadow @Final private static Set<Block> EFFECTIVE_BLOCKS;

	@Override
	public void addEffectiveBlock(Block block)
	{
		EFFECTIVE_BLOCKS.add(block);
	}
}
