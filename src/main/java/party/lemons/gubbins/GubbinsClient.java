package party.lemons.gubbins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.TranslatableText;
import party.lemons.gubbins.entity.render.NewBoatRenderer;
import party.lemons.gubbins.entity.render.PrismarineArrowRender;
import party.lemons.gubbins.entity.render.StickyItemFrameRender;
import party.lemons.gubbins.entity.render.camel.CamelRender;
import party.lemons.gubbins.init.GubbinsColours;
import party.lemons.gubbins.init.GubbinsEntities;
import party.lemons.gubbins.init.GubbinsNetworkClient;
import party.lemons.gubbins.init.GubbinsParticles;
import party.lemons.gubbins.item.quiver.QuiverScreen;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;
import party.lemons.gubbins.particle.DyedSmokeParticle;

public class GubbinsClient implements ClientModInitializer
{

	@Override
	public void onInitializeClient()
	{
		GubbinsNetworkClient.initClient();
		GubbinsColours.init();

		ScreenProviderRegistry.INSTANCE.<QuiverScreenHandler>registerFactory(Gubbins.QUIVER_SCREEN, (container) -> new QuiverScreen(container, MinecraftClient.getInstance().player.inventory, new TranslatableText("container.gubbins.quiver")));
		ScreenProviderRegistry.INSTANCE.<GenericContainerScreenHandler>registerFactory(Gubbins.BOAT_CHEST_SCREEN, (container) -> new GenericContainerScreen(container, MinecraftClient.getInstance().player.inventory, new TranslatableText("container.gubbins.boat_chest")));

		EntityRendererRegistry.INSTANCE.register(GubbinsEntities.NEW_BOAT, (r, c)->new NewBoatRenderer(r));
		EntityRendererRegistry.INSTANCE.register(GubbinsEntities.PRISMARINE_ARROW, (r, c)->new PrismarineArrowRender(r));
		EntityRendererRegistry.INSTANCE.register(GubbinsEntities.STICKY_ITEM_FRAME, (r, c)->new StickyItemFrameRender(r, MinecraftClient.getInstance().getItemRenderer()));
		EntityRendererRegistry.INSTANCE.register(GubbinsEntities.CAMEL, (r, c)->new CamelRender(r));

		ParticleFactoryRegistryImpl.INSTANCE.register(GubbinsParticles.DYED_SIGNAL_SMOKE, provider->(parameters, world, x, y, z, velocityX, velocityY, velocityZ)->
		{
			DyedSmokeParticle particle = new DyedSmokeParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
			particle.setSprite(provider);
			return particle;
		});
	}
}