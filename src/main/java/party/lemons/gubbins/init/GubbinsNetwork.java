package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import party.lemons.gubbins.Gubbins;

import java.util.UUID;

public class GubbinsNetwork
{
	public static final Identifier SPAWN_ENTITY_CUSTOM = new Identifier(Gubbins.MODID, "spawn_entity");

	public static void initCommon(){

	}
}
