package party.lemons.gubbins.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;

public class TranslucentBlock extends GubbinsBlock
{
	public TranslucentBlock(Settings settings)
	{
		super(settings);

		if(FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER)
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getTranslucent());
	}
}
