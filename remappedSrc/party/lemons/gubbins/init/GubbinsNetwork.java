package party.lemons.gubbins.init;

import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.network.C2S_OpenBoatChest;

public class GubbinsNetwork
{
	public static final Identifier SPAWN_ENTITY_CUSTOM = new Identifier(Gubbins.MODID, "spawn_entity");
	public static final Identifier SPAWN_ENTITY_STICKY_FRAME = new Identifier(Gubbins.MODID, "sticky_frame");
	public static final Identifier CL_OPEN_BOAT_CHEST = new Identifier(Gubbins.MODID, "cl_open_boat_chest");

	public static void initCommon(){
		ServerSidePacketRegistryImpl.INSTANCE.register(CL_OPEN_BOAT_CHEST, new C2S_OpenBoatChest());
	}

	private GubbinsNetwork(){}
}
