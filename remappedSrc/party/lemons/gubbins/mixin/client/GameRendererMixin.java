package party.lemons.gubbins.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.gubbins.util.EntityUtil;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
	@Inject(at = @At("HEAD"), method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", cancellable = true)
	public void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cbi)
	{
		if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null && EntityUtil.isUsingTelescope(MinecraftClient.getInstance().player) && MinecraftClient.getInstance().options.perspective == 0)
		{
			cbi.setReturnValue(7D);
		}
	}
}
