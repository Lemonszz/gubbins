package party.lemons.gubbins.boat;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;
import party.lemons.gubbins.util.registry.AutoReg;
import com.mojang.serialization.Lifecycle;

@AutoReg(type = BoatType.class, registry = "gubbins:boat_type")
public class BoatTypes
{
	public static final RegistryKey<Registry<BoatType>> REG_KEY = RegistryKey.ofRegistry(new Identifier(Gubbins.MODID, "boat_type"));
	public static final MutableRegistry<BoatType> REGISTRY = new SimpleRegistry<>(REG_KEY, Lifecycle.stable());
	static{
		((MutableRegistry)Registry.REGISTRIES).add(REG_KEY, REGISTRY);
	}

	public static final BoatType CRIMSON = new BoatType(new Identifier(Gubbins.MODID, "crimson"), GubbinsItems.CRIMSON_BOAT);
	public static final BoatType WARPED = new BoatType(new Identifier(Gubbins.MODID, "warped"), GubbinsItems.WARPED_BOAT);

	//Vanilla Types
	public static final BoatType ACACIA = new VanillaBoatType(new Identifier(Gubbins.MODID, "acacia"), BoatEntity.Type.ACACIA, Items.ACACIA_BOAT);
	public static final BoatType BIRCH = new VanillaBoatType(new Identifier(Gubbins.MODID, "birch"), BoatEntity.Type.BIRCH, Items.BIRCH_BOAT);
	public static final BoatType DARK_OAK = new VanillaBoatType(new Identifier(Gubbins.MODID, "dark_oak"), BoatEntity.Type.DARK_OAK, Items.DARK_OAK_BOAT);
	public static final BoatType JUNGLE = new VanillaBoatType(new Identifier(Gubbins.MODID, "jungle"), BoatEntity.Type.JUNGLE, Items.JUNGLE_BOAT);
	public static final BoatType OAK = new VanillaBoatType(new Identifier(Gubbins.MODID, "oak"), BoatEntity.Type.OAK, Items.OAK_BOAT);
	public static final BoatType SPRUCE = new VanillaBoatType(new Identifier(Gubbins.MODID, "spruce"), BoatEntity.Type.SPRUCE, Items.SPRUCE_BOAT);

	public static BoatType getVanillaType(BoatEntity.Type boatType)
	{
		for(BoatType t : REGISTRY)
		{
			if(t instanceof VanillaBoatType)
			{
				if(((VanillaBoatType) t).getVanillaType() == boatType)
					return t;
			}
		}

		return null;
	}
}
