package party.lemons.gubbins.adornment;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import party.lemons.gubbins.Gubbins;

import java.util.Map;

public class AdornmentRegistry extends SimpleRegistry<Adornment>
{
	private static final Map<Item, Identifier> ADORNMENTS = Maps.newHashMap();
	public static final RegistryKey<Registry<Adornment>> KEY = RegistryKey.ofRegistry(new Identifier(Gubbins.MODID, "adornments"));
	public AdornmentRegistry()
	{
		super(KEY, Lifecycle.stable());
	}

	@Override
	public <V extends Adornment> V add(RegistryKey<Adornment> arg, V entry)
	{
		ADORNMENTS.put(entry.getRecipeItem(), arg.getValue());
		return super.add(arg, entry);
	}

	public Adornment getForMaterial(Item material)
	{
		return get(ADORNMENTS.get(material));
	}
}
