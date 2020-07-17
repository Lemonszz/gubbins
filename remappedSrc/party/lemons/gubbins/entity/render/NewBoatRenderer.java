package party.lemons.gubbins.entity.render;

import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import party.lemons.gubbins.boat.BoatType;
import party.lemons.gubbins.entity.NewBoatEntity;

import java.util.Map;

public class NewBoatRenderer extends EntityRenderer<NewBoatEntity>
{
	private Map<BoatType, Identifier> textures = Maps.newHashMap();
	protected final BoatEntityModel model = new BoatEntityModel();

	public NewBoatRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
		this.shadowRadius = 0.8F;
	}

	public void render(NewBoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.375D, 0.0D);
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
		float h = (float)boatEntity.getDamageWobbleTicks() - g;
		float j = boatEntity.getDamageWobbleStrength() - g;
		if (j < 0.0F) {
			j = 0.0F;
		}

		if (h > 0.0F) {
			matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0F * (float)boatEntity.getDamageWobbleSide()));
		}

		float k = boatEntity.interpolateBubbleWobble(g);
		if (!MathHelper.approximatelyEquals(k, 0.0F)) {
			matrixStack.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), boatEntity.interpolateBubbleWobble(g), true));
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		this.model.setAngles(boatEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(boatEntity)));
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
		this.model.getBottom().render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV);

		if (boatEntity.hasChest())
		{
			matrixStack.push();
			float chestScale = 1F;
			matrixStack.scale(chestScale, chestScale, chestScale);
			matrixStack.translate(0.1D, 0.25F, 0.5D);
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
			matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
			MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.CHEST.getDefaultState(), matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
			matrixStack.pop();
		}

		matrixStack.pop();
		super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public Identifier getTexture(NewBoatEntity boatEntity)
	{
		BoatType type = boatEntity.getNewBoatType();

		if(textures.containsKey(type))
			return textures.get(type);
		else
		{
			Identifier texture = type.getTexture();
			textures.put(type, texture);

			return texture;
		}
	}
}
