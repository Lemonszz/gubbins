package party.lemons.gubbins.adornment;

import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = Adornment.class, registry = "gubbins:adornment")
public final class Adornments
{
	public static final AdornmentRegistry REGISTRY = new AdornmentRegistry();
	static {
		((MutableRegistry)Registry.REGISTRIES).add(RegistryKey.ofRegistry(new Identifier(Gubbins.MODID, "adornment")), REGISTRY);
	}

	public static final Adornment IRON = new Adornment(Items.IRON_INGOT, 0xe0e0e0);
	public static final Adornment GOLD = new Adornment(Items.GOLD_INGOT, 0xffc014);
	public static final Adornment DIAMOND = new Adornment(Items.DIAMOND, 0x4deaff);
	public static final Adornment NETHERITE_SCRAP = new Adornment(Items.NETHERITE_SCRAP, 0x4d443e);
	public static final Adornment EMERALD = new Adornment(Items.EMERALD, 0x000c928);
	public static final Adornment LAPIS_LAZULI = new Adornment(Items.LAPIS_LAZULI, 0x0079c9);
	public static final Adornment GARNET = new Adornment(GubbinsItems.GARNET, 0xff2b2b);
	public static final Adornment AMETHYST = new Adornment(GubbinsItems.AMETHYST, 0x9a1fff);
	public static final Adornment ONYX = new Adornment(GubbinsItems.ONYX, 0x262626);

	private Adornments(){}
}
