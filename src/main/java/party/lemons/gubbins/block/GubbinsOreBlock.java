package party.lemons.gubbins.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class GubbinsOreBlock extends GubbinsBlock
{
	public GubbinsOreBlock(Settings settings)
	{
		super(settings);

		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
	}
}
