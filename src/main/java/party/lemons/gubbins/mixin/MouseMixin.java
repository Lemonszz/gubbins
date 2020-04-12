package party.lemons.gubbins.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.util.EntityUtil;

@Mixin(Mouse.class)
public class MouseMixin
{
	private double mouse = -1;
	private boolean smoothCam = false;

	@Inject(at = @At("HEAD"), method = "updateMouse()V")
	public void preUpdateMouse(CallbackInfo cbi)
	{
		if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null)
		{
			mouse = MinecraftClient.getInstance().options.mouseSensitivity;
			smoothCam = MinecraftClient.getInstance().options.smoothCameraEnabled;

			if(EntityUtil.isUsingTelescope(MinecraftClient.getInstance().player) && MinecraftClient.getInstance().options.perspective == 0)
			{
				MinecraftClient.getInstance().options.mouseSensitivity = mouse / 2F;
				MinecraftClient.getInstance().options.smoothCameraEnabled = true;
			}
		}
	}

	@Inject(at = @At("TAIL"), method = "updateMouse()V")
	public void postUpdateMouse(CallbackInfo cbi)
	{
		if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null)
		{
			MinecraftClient.getInstance().options.mouseSensitivity = mouse;
			MinecraftClient.getInstance().options.smoothCameraEnabled = smoothCam;
		}
	}
}
