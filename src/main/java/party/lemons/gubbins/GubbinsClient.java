package party.lemons.gubbins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import party.lemons.gubbins.entity.render.NewBoatRenderer;
import party.lemons.gubbins.entity.render.PrismarineArrowRender;
import party.lemons.gubbins.init.*;
import party.lemons.gubbins.item.quiver.QuiverScreen;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;

public class GubbinsClient implements ClientModInitializer
{

	@Override
	public void onInitializeClient()
	{
		GubbinsNetworkClient.initClient();
		GubbinsColours.init();
		GubbinsParticles.clientInit();

		ScreenProviderRegistry.INSTANCE.<QuiverScreenHandler>registerFactory(Gubbins.QUIVER, (container) -> new QuiverScreen(container, MinecraftClient.getInstance().player.inventory, new TranslatableText("container.gubbins.quiver")));

		EntityRendererRegistry.INSTANCE.register(GubbinsEntities.NEW_BOAT, (r, c)->new NewBoatRenderer(r));
		EntityRendererRegistry.INSTANCE.register(GubbinsEntities.PRISMARINE_ARROW, (r, c)->new PrismarineArrowRender(r));
	}
}