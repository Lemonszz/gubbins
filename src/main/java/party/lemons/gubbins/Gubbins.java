package party.lemons.gubbins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.function.LootFunctions;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party.lemons.gubbins.adornment.Adornments;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.cave.CaveBiomes;
import party.lemons.gubbins.command.BetterLocateCommand;
import party.lemons.gubbins.config.GubbinsConfig;
import party.lemons.gubbins.entity.NewBoatEntity;
import party.lemons.gubbins.init.GubbinsBlocks;
import party.lemons.gubbins.init.GubbinsGeneration;
import party.lemons.gubbins.init.GubbinsLootTables;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;
import party.lemons.gubbins.itemgroup.GubbinsItemGroup;
import party.lemons.gubbins.misc.BiomeLootFunction;
import party.lemons.gubbins.util.registry.RegistryLoader;

public class Gubbins implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "gubbins";

	public static final Identifier QUIVER_SCREEN = new Identifier(MODID, "quiver");
	public static final Identifier BOAT_CHEST_SCREEN = new Identifier(MODID, "boat_chest");

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

		//Statically init classes lol
		CaveBiomes.REGISTRY.isEmpty();
		BoatTypes.REGISTRY.isEmpty();
		Adornments.REGISTRY.isEmpty();

		RegistryLoader.registerPackage("party.lemons");
		RegistryLoader.init();

		GubbinsBlocks.initDecorations();
		GubbinsGeneration.init();
		GubbinsLootTables.init();

		LootFunctions.register(new BiomeLootFunction.Factory());

		CommandRegistry.INSTANCE.register(false, d->{
			BetterLocateCommand.register(d);
		});

		//TODO: move this
		ContainerProviderRegistry.INSTANCE.registerFactory(QUIVER_SCREEN, (syncId, identifier, player, buf) -> {
			int slot = buf.readInt();

			return ((ScreenHandlerFactory) (syncId1, inv, player1)->new QuiverScreenHandler(syncId1, player1, inv.getStack(slot))).createMenu(syncId, player.inventory, player);
		});

		ContainerProviderRegistry.INSTANCE.registerFactory(BOAT_CHEST_SCREEN, (syncId, identifier, player, buf) -> {
			int entityId = buf.readInt();
			Entity e = player.world.getEntityById(entityId);
			if(!(e instanceof NewBoatEntity))
				return null;

			NewBoatEntity boat = (NewBoatEntity) e;

			return ((ScreenHandlerFactory) (syncId1, inv, player1)->GenericContainerScreenHandler.createGeneric9x3(syncId, player.inventory, boat.inventory)).createMenu(syncId, player.inventory, player);
		});
	}
}