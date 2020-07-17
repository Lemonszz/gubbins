package party.lemons.gubbins.mixin.client;

import com.google.common.collect.Maps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.adornment.Adornment;
import party.lemons.gubbins.adornment.Adornments;
import party.lemons.gubbins.init.GubbinsItems;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
	private Map<EquipmentSlot, ItemStack> stacks;

	@Inject(at = @At("TAIL"), method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V")
	public void onItemRender(ItemStack itemStack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo cbi)
	{
		if(stacks == null)
		{
			stacks = Maps.newHashMap();

			stacks.put(EquipmentSlot.HEAD, new ItemStack(GubbinsItems.DISPLAY_ITEM_HELMET));
			stacks.put(EquipmentSlot.CHEST, new ItemStack(GubbinsItems.DISPLAY_ITEM_CHEST));
			stacks.put(EquipmentSlot.LEGS, new ItemStack(GubbinsItems.DISPLAY_ITEM_LEGGINGS));
			stacks.put(EquipmentSlot.FEET, new ItemStack(GubbinsItems.DISPLAY_ITEM_FEET));
		}

		if(!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorItem && itemStack.hasTag() && itemStack.getTag().contains("_adornment"))
		{
			Adornment adornment = Adornments.REGISTRY.get(new Identifier(itemStack.getTag().getString("_adornment")));
			if(adornment != null)
			{
				ArmorItem armorItem = (ArmorItem) itemStack.getItem();
				ItemStack st = stacks.get(armorItem.getSlotType());

				BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getHeldItemModel(st, null, null);
				int c = adornment.getColour();

				renderItem(st, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, bakedModel, c);
			}
		}
	}

	public void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, int color) {
		if (!stack.isEmpty()) {
			matrices.push();
			boolean bl = renderMode == ModelTransformation.Mode.GUI;
			boolean bl2 = bl || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;

			model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
			matrices.translate(-0.5D, -0.5D, -0.5D);
			if (!model.isBuiltin() && (stack.getItem() != Items.TRIDENT || bl2)) {
				boolean idk = renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND || renderMode == ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND || renderMode == ModelTransformation.Mode.FIXED;
				RenderLayer renderLayer = RenderLayers.getItemLayer(stack, idk);
				VertexConsumer vertexConsumer = ItemRenderer.getArmorVertexConsumer(vertexConsumers, renderLayer, true, stack.hasEnchantmentGlint());
				this.renderBakedItemModel(model, light, overlay, matrices, vertexConsumer, color);
			}
			matrices.pop();
		}
	}

	private void renderBakedItemModel(BakedModel model, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, int color) {
		Random random = new Random();
		Direction[] dirs = Direction.values();

		for(Direction direction : dirs)
		{
			random.setSeed(42L);
			this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, random), light, overlay, color);
		}

		random.setSeed(42L);
		this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), light, overlay, color);
	}

	private void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, int light, int overlay, int color)
	{
		MatrixStack.Entry entry = matrices.peek();

		for(BakedQuad bakedQuad : quads)
		{
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			vertices.quad(entry, bakedQuad, r, g, b, light, overlay);
		}
	}
}
