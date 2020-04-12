package party.lemons.gubbins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party.lemons.gubbins.adornment.Adornments;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.cave.CaveBiomes;
import party.lemons.gubbins.command.BetterLocateCommand;
import party.lemons.gubbins.config.GubbinsConfig;
import party.lemons.gubbins.init.*;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;
import party.lemons.gubbins.itemgroup.GubbinsItemGroup;
import party.lemons.gubbins.util.registry.RegistryLoader;

public class Gubbins implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "gubbins";

	public static final Identifier QUIVER = new Identifier(MODID, "quiver");

	private static final Identifier tabID = new Identifier(MODID, MODID);
	public static ItemGroup GROUP;

	public static GubbinsConfig config;

	@Override
	public void onInitialize()
	{
		GROUP = new GubbinsItemGroup(new Identifier(Gubbins.MODID, Gubbins.MODID));

		config = GubbinsConfig.load();
		GubbinsConfig.write(config);

		GubbinsNetwork.initCommon();
		//RegistryLoader.init();

		//Statically init classes lol
		CaveBiomes.REGISTRY.isEmpty();
		BoatTypes.REGISTRY.isEmpty();
		Adornments.REGISTRY.isEmpty();

		RegistryLoader.register(BoatTypes.class);
		RegistryLoader.register(GubbinsBlocks.class);
		RegistryLoader.register(GubbinsItems.class);
		RegistryLoader.register(GubbinsRecipes.class);
		RegistryLoader.register(CaveBiomes.class);
		RegistryLoader.register(GubbinsParticles.class);
		RegistryLoader.register(GubbinsCarvers.class);
		RegistryLoader.register(GubbinsEntities.class);
		RegistryLoader.register(GubbinsFeatures.class);
		RegistryLoader.register(GubbinsStructurePieces.class);
		RegistryLoader.register(GubbinsStructures.class);
		RegistryLoader.register(Adornments.class);


		GubbinsBlocks.initDecorations();
		GubbinsGeneration.init();
		GubbinsLootTables.init();

		CommandRegistry.INSTANCE.register(false, d->{
			BetterLocateCommand.register(d);
		});

		//TODO: move this
		ContainerProviderRegistry.INSTANCE.registerFactory(QUIVER, (syncId, identifier, player, buf) -> {
			int slot = buf.readInt();

			return ((ScreenHandlerFactory) (syncId1, inv, player1)->new QuiverScreenHandler(syncId1, player1, inv.getStack(slot))).createMenu(syncId, player.inventory, player);
		});
	}
}