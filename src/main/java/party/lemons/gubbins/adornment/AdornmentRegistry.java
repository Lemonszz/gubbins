package party.lemons.gubbins.adornment;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.Map;

public class AdornmentRegistry extends SimpleRegistry<Adornment>
{
	private static final Map<Item, Identifier> ADORNMENTS = Maps.newHashMap();

	@Override
	public Adornment add(Identifier id, Adornment entry)
	{
		ADORNMENTS.put(entry.getRecipeItem(), id);

		return super.add(id, entry);
	}

	public Adornment getForMaterial(Item material)
	{
		return get(ADORNMENTS.get(material));
	}
}
