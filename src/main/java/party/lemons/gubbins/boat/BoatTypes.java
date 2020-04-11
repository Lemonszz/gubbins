package party.lemons.gubbins.boat;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = BoatType.class, registry = "gubbins:boat_type")
public class BoatTypes
{
	public static final MutableRegistry<BoatType> REGISTRY = new SimpleRegistry<>();
	static{
		Registry.REGISTRIES.add(new Identifier(Gubbins.MODID, "boat_type"), REGISTRY);
	}

	public static final BoatType CRIMSON = new BoatType(new Identifier(Gubbins.MODID, "crimson"), GubbinsItems.CRIMSON_BOAT);
	public static final BoatType WARPED = new BoatType(new Identifier(Gubbins.MODID, "warped"), GubbinsItems.WARPED_BOAT);

	//Vanilla Types
	public static final BoatType ACACIA = new VanillaBoatType(new Identifier(Gubbins.MODID, "acacia"), BoatEntity.Type.ACACIA);
	public static final BoatType BIRCH = new VanillaBoatType(new Identifier(Gubbins.MODID, "birch"), BoatEntity.Type.BIRCH);
	public static final BoatType DARK_OAK = new VanillaBoatType(new Identifier(Gubbins.MODID, "dark_oak"), BoatEntity.Type.DARK_OAK);
	public static final BoatType JUNGLE = new VanillaBoatType(new Identifier(Gubbins.MODID, "jungle"), BoatEntity.Type.JUNGLE);
	public static final BoatType OAK = new VanillaBoatType(new Identifier(Gubbins.MODID, "oak"), BoatEntity.Type.OAK);
	public static final BoatType SPRUCE = new VanillaBoatType(new Identifier(Gubbins.MODID, "spruce"), BoatEntity.Type.SPRUCE);
}
