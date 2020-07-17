package party.lemons.gubbins.mixin.client;

import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.GubbinsClient;

@Mixin(DoubleOption.class)
public class DoubleOptionMixin
{
	@Inject(at = @At("RETURN"), method = "set(Lnet/minecraft/client/options/GameOptions;D)V")
	public void set(GameOptions options, double value, CallbackInfo cbi)
	{
		if((Object)this == Option.SENSITIVITY)
		{
			GubbinsClient.telescope_mouse_sens = value;
		}
	}
}
