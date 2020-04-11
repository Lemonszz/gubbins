package party.lemons.gubbins.mixin;

import net.minecraft.enchantment.SilkTouchEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SilkTouchEnchantment.class)
public class SilkTouchEnchantmentMixin
{
	@Inject(at = @At("RETURN"), method = "getMaximumLevel()I", cancellable = true)
	public void getMaximumLevel(CallbackInfoReturnable<Integer> cbi)
	{
		cbi.setReturnValue(2);
	}

	@Inject(at = @At("RETURN"), method = "getMinimumPower(I)I", cancellable = true)
	public void getMinimumPower(int level, CallbackInfoReturnable<Integer> cbi)
	{
		if(level == 2)
			cbi.setReturnValue(9999999);
	}
}
