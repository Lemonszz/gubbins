package party.lemons.gubbins.network;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import party.lemons.gubbins.entity.NewBoatEntity;

public class C2S_OpenBoatChest implements PacketConsumer
{
	@Override
	public void accept(PacketContext ctx, PacketByteBuf buf)
	{
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
	}
}
