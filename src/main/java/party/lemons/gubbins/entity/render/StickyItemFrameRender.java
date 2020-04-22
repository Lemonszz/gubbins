package party.lemons.gubbins.entity.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class StickyItemFrameRender extends ItemFrameEntityRenderer
{
	public StickyItemFrameRender(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer)
	{
		super(dispatcher, itemRenderer);
	}

	@Override
	public void render(ItemFrameEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		if (this.hasLabel(entity)) {
			this.renderLabelIfPresent(entity, entity.getDisplayName(), matrixStack, vertexConsumerProvider, i);
		}

		matrixStack.push();
		Direction direction = entity.getHorizontalFacing();
		Vec3d offset = this.getPositionOffset(entity, g);
		matrixStack.translate(-offset.getX(), -offset.getY(), -offset.getZ());
		double translateFactor = 0.46875D;
		matrixStack.translate((double)direction.getOffsetX() * translateFactor, (double)direction.getOffsetY() * translateFactor, (double)direction.getOffsetZ() * translateFactor);
		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(entity.pitch));
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - entity.yaw));
		boolean isInvisible = entity.isInvisible();

		MinecraftClient client = MinecraftClient.getInstance();
		if (!isInvisible)
		{
			BlockRenderManager blockRenderManager = client.getBlockRenderManager();
			BakedModelManager bakedModelManager = blockRenderManager.getModels().getModelManager();
			ModelIdentifier modelIdentifier = entity.getHeldItemStack().getItem() == Items.FILLED_MAP ? MAP_FRAME : NORMAL_FRAME;
			matrixStack.push();
			matrixStack.translate(-0.5D, -0.5D, -0.5D);
			blockRenderManager.getModelRenderer().render(matrixStack.peek(), vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntitySolid()), null, bakedModelManager.getModel(modelIdentifier), 0.0F, 1.0F, 0.0F, i, OverlayTexture.DEFAULT_UV);
			matrixStack.pop();
		}

		ItemStack stack = entity.getHeldItemStack();
		if (!stack.isEmpty()) {
			boolean isMap = stack.getItem() == Items.FILLED_MAP;
			if (isInvisible) {
				matrixStack.translate(0.0D, 0.0D, 0.5D);
			} else {
				matrixStack.translate(0.0D, 0.0D, 0.4375D);
			}

			int rotation = isMap ? entity.getRotation() % 4 * 2 : entity.getRotation();
			matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)rotation * 360.0F / 8.0F));
			if (isMap)
			{
				matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
				float scale = 0.0078125F;
				matrixStack.scale(scale, scale, scale);
				matrixStack.translate(-64.0D, -64.0D, 0.0D);
				MapState mapState = FilledMapItem.getOrCreateMapState(stack, entity.world);
				matrixStack.translate(0.0D, 0.0D, -1.0D);
				if (mapState != null)
				{
					client.gameRenderer.getMapRenderer().draw(matrixStack, vertexConsumerProvider, mapState, true, i);
				}
			} else
				{
				matrixStack.scale(0.5F, 0.5F, 0.5F);
				client.getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider);
			}
		}

		matrixStack.pop();
	}

	private static final ModelIdentifier NORMAL_FRAME = new ModelIdentifier("gubbins:sticky_item_frame", "map=false");
	private static final ModelIdentifier MAP_FRAME = new ModelIdentifier("gubbins:sticky_item_frame", "map=true");
}
