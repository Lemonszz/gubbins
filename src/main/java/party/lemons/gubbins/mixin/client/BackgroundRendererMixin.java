package party.lemons.gubbins.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.init.GubbinsStatusEffects;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin
{
	@Inject(at = @At(value = "TAIL"), method = "applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V")
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo cbi)
	{
		if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(GubbinsStatusEffects.CLARITY))
		{
			StatusEffectInstance eff = MinecraftClient.getInstance().player.getStatusEffect(GubbinsStatusEffects.CLARITY);
			FluidState fluidState = camera.getSubmergedFluidState();
			float end = 0;
			if(fluidState != Fluids.EMPTY.getDefaultState())
			{
				end =  (5F + (eff.getAmplifier() * 4));
			}

			if(end != 0)
				RenderSystem.fogEnd(end);
		}
	}
}
