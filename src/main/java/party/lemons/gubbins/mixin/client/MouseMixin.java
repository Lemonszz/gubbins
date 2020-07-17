package party.lemons.gubbins.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.GubbinsClient;
import party.lemons.gubbins.util.EntityUtil;

@Mixin(Mouse.class)
public class MouseMixin
{

	@Inject(at = @At("HEAD"), method = "updateMouse()V")
	public void preUpdateMouse(CallbackInfo cbi)
	{
		if(MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null)
		{
			if(!GubbinsClient.telescope_mouse_set && MinecraftClient.getInstance().currentScreen != null)
			{
				GubbinsClient.telescope_mouse_sens = MinecraftClient.getInstance().options.mouseSensitivity;
				GubbinsClient.telescope_smooth_cam = MinecraftClient.getInstance().options.smoothCameraEnabled;
				GubbinsClient.telescope_mouse_set = true;
				return;
			}

			if(isUsingTelescope())
			{
				MinecraftClient.getInstance().options.mouseSensitivity = GubbinsClient.telescope_mouse_sens / 2F;
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
		if(MinecraftClient.getInstance() == null)
			return;

		if(MinecraftClient.getInstance().player != null && !isUsingTelescope() && GubbinsClient.telescope_mouse_sens != -1)
		{
			MinecraftClient.getInstance().options.mouseSensitivity = GubbinsClient.telescope_mouse_sens;
			MinecraftClient.getInstance().options.smoothCameraEnabled = GubbinsClient.telescope_smooth_cam;
			GubbinsClient.telescope_mouse_set = false;
		}
	}
}
