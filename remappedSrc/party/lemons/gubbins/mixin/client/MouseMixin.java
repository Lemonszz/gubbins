package party.lemons.gubbins.mixin.client;

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
	private boolean mouseSet = false;

	@Inject(at = @At("HEAD"), method = "updateMouse()V")
	public void preUpdateMouse(CallbackInfo cbi)
	{
		if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null)
		{
			if(!mouseSet)
			{
				mouse = MinecraftClient.getInstance().options.mouseSensitivity;
				smoothCam = MinecraftClient.getInstance().options.smoothCameraEnabled;
				mouseSet = true;
			}

			if(isUsingTelescope())
			{
				MinecraftClient.getInstance().options.mouseSensitivity = mouse / 2F;
				MinecraftClient.getInstance().options.smoothCameraEnabled = true;
			}
		}
	}

	private boolean isUsingTelescope()
	{
		return EntityUtil.isUsingTelescope(MinecraftClient.getInstance().player) && MinecraftClient.getInstance().options.perspective == 0;
	}

	@Inject(at = @At("TAIL"), method = "updateMouse()V")
	public void postUpdateMouse(CallbackInfo cbi)
	{
		if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null && !isUsingTelescope())
		{
			MinecraftClient.getInstance().options.mouseSensitivity = mouse;
			MinecraftClient.getInstance().options.smoothCameraEnabled = smoothCam;
			mouseSet = false;
		}
	}
}
