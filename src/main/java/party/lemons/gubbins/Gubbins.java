package party.lemons.gubbins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party.lemons.gubbins.adornment.Adornments;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.cave.CaveBiomes;
import party.lemons.gubbins.command.BetterLocateCommand;
import party.lemons.gubbins.config.GubbinsConfig;
import party.lemons.gubbins.init.*;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;
import party.lemons.gubbins.util.registry.RegistryLoader;

public class Gubbins implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "gubbins";

	public static final Identifier QUIVER = new Identifier(MODID, "quiver");

	private static final Identifier tabID = new Identifier(MODID, MODID);
	public static final ItemGroup GROUP;

	public static GubbinsConfig config;

	static
	{
		((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
		GROUP = new ItemGroup(ItemGroup.GROUPS.length - 1, String.format("%s.%s", tabID.getNamespace(), tabID.getPath())) {
			@Override
			public ItemStack createIcon() {
				return new ItemStack(GubbinsItems.AMETHYST);
			}

			@Override
			public void appendStacks(DefaultedList<ItemStack> stacks) {
				super.appendStacks(stacks);

				stacks.sort((o1, o2)->
				{
					boolean isBlock1 = o1.getItem() instanceof BlockItem;
					boolean isBlock2 = o2.getItem() instanceof BlockItem;
					if(isBlock1 == isBlock2)
					{
						return o1.getName().asString().compareTo(o2.getName().asString());
					}else if(isBlock1)
					{
						return 1;
					}else
					{
						return -1;
					}
				});
			}
		};
	}

	@Override
	public void onInitialize()
	{
		config = GubbinsConfig.load();
		GubbinsConfig.write(config);

		GubbinsNetwork.initCommon();
		//RegistryLoader.init();

		//Statically init classes lol
		CaveBiomes.REGISTRY.isEmpty();
		BoatTypes.REGISTRY.isEmpty();
		Adornments.REGISTRY.isEmpty();

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
		RegistryLoader.register(BoatTypes.class);


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