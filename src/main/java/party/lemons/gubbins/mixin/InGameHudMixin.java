package party.lemons.gubbins.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.util.EntityUtil;

@Mixin(InGameHud.class)
public class InGameHudMixin
{
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;

	private final Identifier TELESCOPE_BLUR = new Identifier(Gubbins.MODID, "textures/misc/telescope_blur.png");

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;"), method = "Lnet/minecraft/client/gui/hud/InGameHud;render(F)V")
	public void render(float tickDelta, CallbackInfo cbi)
	{
		if(EntityUtil.isUsingTelescope(MinecraftClient.getInstance().player) && MinecraftClient.getInstance().options.perspective == 0)
		{
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.defaultBlendFunc();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableAlphaTest();
			MinecraftClient.getInstance().getTextureManager().bindTexture(TELESCOPE_BLUR);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);

			double change = scaledHeight - 256;
			double width = 256 + change;

			double xPos = (scaledWidth / 2d) - (width / 2d);

			bufferBuilder.vertex(xPos, this.scaledHeight, -90.0D).texture(0.0F, 1.0F).next();
			bufferBuilder.vertex(xPos + width, this.scaledHeight, -90.0D).texture(1.0F, 1.0F).next();
			bufferBuilder.vertex(xPos + width, 0.0D, -90.0D).texture(1.0F, 0.0F).next();
			bufferBuilder.vertex(xPos, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
			tessellator.draw();

			DrawableHelper.fill(0, 0, (int)xPos + 1, scaledHeight, 0xFF000000);
			DrawableHelper.fill((int)(xPos + width), 0, scaledWidth, scaledHeight, 0xFF000000);

			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			RenderSystem.enableAlphaTest();
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		}
	}
}
