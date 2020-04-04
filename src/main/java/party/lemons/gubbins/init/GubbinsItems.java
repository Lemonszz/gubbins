package party.lemons.gubbins.init;

import net.minecraft.item.Item;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.boat.BoatTypes;
import party.lemons.gubbins.item.*;
import party.lemons.gubbins.item.quiver.QuiverItem;
import party.lemons.gubbins.util.registry.AutoReg;


@AutoReg(type = Item.class, registry = "item")
public class GubbinsItems
{
	public static final PotionFlaskItem POTION_FLASK = new PotionFlaskItem();
	public static final QuiverItem QUIVER = new QuiverItem();
	public static final Item GARNET = new Item(settings());
	public static final Item AMETHYST = new Item(settings());
	public static final Item ONYX = new Item(settings());
	public static final Item PRISMARINE_ARROW = new PrismarineArrowItem(settings());
	public static final Item TELESCOPE = new TelescopeItem(settings().maxDamage(600));

	public static final Item CRIMSON_BOAT = new ItemNewBoat(BoatTypes.CRIMSON, settings().maxCount(1));
	public static final Item WARPED_BOAT = new ItemNewBoat(BoatTypes.WARPED, settings().maxCount(1));

	//Used for adornment overlay, honestly the easiest way lol
	public static final Item DISPLAY_ITEM_HELMET = new DisplayItem();
	public static final Item DISPLAY_ITEM_CHEST = new DisplayItem();
	public static final Item DISPLAY_ITEM_LEGGINGS = new DisplayItem();
	public static final Item DISPLAY_ITEM_FEET = new DisplayItem();


	public static Item.Settings settings()
	{
		return new Item.Settings().group(Gubbins.GROUP);
	}
}
