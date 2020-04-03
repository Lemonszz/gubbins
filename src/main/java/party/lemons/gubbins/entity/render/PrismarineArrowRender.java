package party.lemons.gubbins.entity.render;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.Gubbins;

public class PrismarineArrowRender extends ArrowEntityRenderer
{
	private static final Identifier TEXTURE = new Identifier(Gubbins.MODID, "textures/entity/arrow/prismarine_arrow.png");

	public PrismarineArrowRender(EntityRenderDispatcher entityRenderDispatcher)
	{
		super(entityRenderDispatcher);
	}

	@Override
	public Identifier getTexture(ArrowEntity arrowEntity)
	{
		return TEXTURE;
	}
}
