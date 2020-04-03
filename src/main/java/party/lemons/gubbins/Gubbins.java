package party.lemons.gubbins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party.lemons.gubbins.adornment.Adornments;
import party.lemons.gubbins.init.GubbinsColours;
import party.lemons.gubbins.init.GubbinsGeneration;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.init.GubbinsNetwork;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;
import party.lemons.gubbins.util.registry.RegistryLoader;

public class Gubbins implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();

	public static final String MODID = "gubbins";

	public static final Identifier QUIVER = new Identifier(MODID, "quiver");

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), ()->new ItemStack(GubbinsItems.AMETHYST));

	@Override
	public void onInitialize()
	{
		GubbinsNetwork.initCommon();
		RegistryLoader.init();
		GubbinsColours.init();
		GubbinsGeneration.init();

		ContainerProviderRegistry.INSTANCE.registerFactory(QUIVER, (syncId, identifier, player, buf) -> {
			int slot = buf.readInt();

			return ((ScreenHandlerFactory) (syncId1, inv, player1)->new QuiverScreenHandler(syncId1, player1, inv.getStack(slot))).createMenu(syncId, player.inventory, player);
		});
	}
}