package party.lemons.gubbins.boat;

import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public class BoatType
{
	public final Identifier id;
	public final ItemConvertible item;

	public BoatType(Identifier id, ItemConvertible item)
	{
		this.id = id;
		this.item = item;
	}
}
