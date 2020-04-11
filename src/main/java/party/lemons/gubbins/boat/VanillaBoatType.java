package party.lemons.gubbins.boat;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

public class VanillaBoatType extends BoatType
{
	private BoatEntity.Type vanillaType;

	public VanillaBoatType(Identifier id, BoatEntity.Type type)
	{
		super(id, type.getBaseBlock());
		this.vanillaType = type;
	}

	@Override
	public Identifier getTexture()
	{
		return new Identifier("minecraft", "textures/entity/boat/" + vanillaType.getName() + ".png");
	}
}
