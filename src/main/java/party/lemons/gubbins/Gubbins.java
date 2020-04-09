package party.lemons.gubbins;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party.lemons.gubbins.command.BetterLocateCommand;
import party.lemons.gubbins.init.*;
import party.lemons.gubbins.item.quiver.QuiverScreenHandler;
import party.lemons.gubbins.util.registry.RegistryLoader;

import java.util.Comparator;
import java.util.Iterator;

public class Gubbins implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	public static final String MODID = "gubbins";

	public static final Identifier QUIVER = new Identifier(MODID, "quiver");

	private static final Identifier tabID = new Identifier(MODID, MODID);
	public static final ItemGroup GROUP;

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
		GubbinsNetwork.initCommon();
		RegistryLoader.init();
		GubbinsBlocks.initDecorations();
		GubbinsColours.init();
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