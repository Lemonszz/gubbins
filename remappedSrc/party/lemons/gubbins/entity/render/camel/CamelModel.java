package party.lemons.gubbins.entity.render.camel;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import party.lemons.gubbins.entity.CamelEntity;

public class CamelModel extends EntityModel<CamelEntity>
{
	private final ModelPart bone;
	private final ModelPart bone2;

	public CamelModel() {
		textureWidth = 128;
		textureHeight = 64;

		bone = new ModelPart(this);
		bone.setPivot(0.0F, 24.0F, 0.0F);
		bone.addCuboid("bone", -4.0F, -17.0F, -8.0F, 8, 17, 32, 0.0F, 0, 15);

		bone2 = new ModelPart(this);
		bone2.setPivot(0.0F, 0.0F, 0.0F);
		bone.addChild(bone2);
		bone2.addCuboid("bone2", -3.0F, -22.0F, -12.0F, 5, 5, 6, 0.0F, 0, 0);
	}

	@Override
	public void setAngles(CamelEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch)
	{

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha)
	{
		bone.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}