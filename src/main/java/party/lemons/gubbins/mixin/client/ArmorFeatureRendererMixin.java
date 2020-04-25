package party.lemons.gubbins.mixin.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.adornment.Adornment;
import party.lemons.gubbins.adornment.Adornments;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin extends FeatureRenderer<LivingEntity, BipedEntityModel<LivingEntity>>
{
	@Inject(at = @At("RETURN"), method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;FFFFFFLnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V")
	private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch, EquipmentSlot slot, int light, BipedEntityModel armorModel, CallbackInfo cbi)
	{
		ItemStack itemStack = entity.getEquippedStack(slot);
		if (itemStack.getItem() instanceof ArmorItem)
		{
			ArmorItem armorItem = (ArmorItem)itemStack.getItem();
			if(itemStack.hasTag() && itemStack.getTag().contains("_adornment"))
			{
				Adornment adornment = Adornments.REGISTRY.get(new Identifier(itemStack.getTag().getString("_adornment")));
				if (adornment != null && armorItem.getSlotType() == slot)
				{
					this.getContextModel().setAttributes(armorModel);
					armorModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
					this.setVisible(armorModel, slot);
					armorModel.setAngles(entity, limbAngle, limbDistance, customAngle, headYaw, headPitch);
					boolean bl = this.usesSecondLayer(slot);
					boolean bl2 = itemStack.hasEnchantmentGlint();

					int color = adornment.getColour();
					float r = (float) (color >> 16 & 255) / 255.0F;
					float g = (float) (color >> 8 & 255) / 255.0F;
					float b = (float) (color & 255) / 255.0F;

					this.renderArmorParts(matrices, vertexConsumers, light, bl2, armorModel, bl, r, g, b);
				}
			}
		}
	}

	private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, boolean glint, BipedEntityModel armorModel, boolean secondLayer, float red, float green, float blue) {
		VertexConsumer vertexConsumer = ItemRenderer.getArmorVertexConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(secondLayer)), false, glint);
		armorModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
	}

	protected Identifier getArmorTexture(boolean secondLayer) {
		String string = "textures/adornment/adornment_" + (secondLayer ? 2 : 1)  + ".png";
		return new Identifier(Gubbins.MODID, string);
	}

	public ArmorFeatureRendererMixin(FeatureRendererContext<LivingEntity, BipedEntityModel<LivingEntity>> context)
	{
		super(context);
	}

	@Shadow
	protected void setVisible(BipedEntityModel bipedModel, EquipmentSlot slot)
	{
	}

	@Shadow
	private boolean usesSecondLayer(EquipmentSlot slot)
	{
		return false;
	}
}