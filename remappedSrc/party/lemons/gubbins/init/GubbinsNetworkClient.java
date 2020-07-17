package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import party.lemons.gubbins.network.S2C_SpawnEntityCustom;
import party.lemons.gubbins.network.S2C_SpawnStickyItemFrame;

public class GubbinsNetworkClient
{
	public static void initClient(){
		ClientSidePacketRegistry.INSTANCE.register(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, new S2C_SpawnEntityCustom());
		ClientSidePacketRegistry.INSTANCE.register(GubbinsNetwork.SPAWN_ENTITY_STICKY_FRAME, new S2C_SpawnStickyItemFrame());
	}
}
