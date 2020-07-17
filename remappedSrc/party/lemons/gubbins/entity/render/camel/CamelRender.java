package party.lemons.gubbins.entity.render.camel;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.entity.CamelEntity;

public class CamelRender extends MobEntityRenderer<CamelEntity, CamelModel>
{
	private final Identifier CAMEL_TEXTURE = new Identifier(Gubbins.MODID, "textures/entity/camel.png");

	public CamelRender(EntityRenderDispatcher r)
	{
		super(r, new CamelModel(), 1F);
	}

	@Override
	public Identifier getTexture(CamelEntity entity)
	{
		return CAMEL_TEXTURE;
	}
}
