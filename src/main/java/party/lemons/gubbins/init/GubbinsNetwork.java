package party.lemons.gubbins.init;

import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.entity.NewBoatEntity;

public class GubbinsNetwork
{
	public static final Identifier SPAWN_ENTITY_CUSTOM = new Identifier(Gubbins.MODID, "spawn_entity");
	public static final Identifier SPAWN_ENTITY_STICKY_FRAME = new Identifier(Gubbins.MODID, "sticky_frame");
	public static final Identifier CL_OPEN_BOAT_CHEST = new Identifier(Gubbins.MODID, "cl_open_boat_chest");

	public static void initCommon(){
		ServerSidePacketRegistryImpl.INSTANCE.register(CL_OPEN_BOAT_CHEST, ((ctx, buf) -> {
			int entityID = buf.readInt();
			Entity e = ctx.getPlayer().world.getEntityById(entityID);
			if(e instanceof NewBoatEntity)
			{
				NewBoatEntity boat = (NewBoatEntity) e;
				if(boat.hasPassenger(ctx.getPlayer()))
				{
					ctx.getTaskQueue().execute(()->{
						boat.openInventory(ctx.getPlayer());
					});
				}
			}
		}));
	}
}
