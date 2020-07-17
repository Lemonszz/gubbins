package party.lemons.gubbins.mixin.client;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin
{
	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;fogMode(Lcom/mojang/blaze3d/platform/GlStateManager$FogMode;)V"), method = "applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V")
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo cbi)
	{
	/*	if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.hasStatusEffect(GubbinsStatusEffects.CLARITY))
		{
			StatusEffectInstance eff = MinecraftClient.getInstance().player.getStatusEffect(GubbinsStatusEffects.CLARITY);
			FluidState fluidState = camera.getSubmergedFluidState();
			Entity entity = camera.getFocusedEntity();
			boolean bl = fluidState.getFluid() != Fluids.EMPTY;
			float density;
			if(bl)
			{
				density = 1.0F;
				if(fluidState.matches(FluidTags.WATER))
				{
					density = 0.05F;
					if(entity instanceof ClientPlayerEntity)
					{
						ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) entity;
						density -= clientPlayerEntity.getUnderwaterVisibility() * clientPlayerEntity.getUnderwaterVisibility() * 0.03F;
						Biome biome = clientPlayerEntity.world.getBiome(clientPlayerEntity.getBlockPos());
						if(biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS)
						{
							density += 0.005F;
						}

						density -= 0.005F * (1F + eff.getAmplifier());
					}
				}else if(fluidState.matches(FluidTags.LAVA))
				{
					density = 0.15F - (eff.getAmplifier() / 20F);
				}
				RenderSystem.fogDensity(Math.max(0, density));
			}
		}*/
	}
}
