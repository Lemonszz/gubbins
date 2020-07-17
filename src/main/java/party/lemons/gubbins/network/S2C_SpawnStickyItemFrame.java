package party.lemons.gubbins.network;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import party.lemons.gubbins.entity.StickyItemFrameEntity;

import java.util.UUID;

public class S2C_SpawnStickyItemFrame implements PacketConsumer
{
	@Override
	public void accept(PacketContext ctx, PacketByteBuf data)
	{
		UUID entityUUID = data.readUuid();
		int entityID = data.readVarInt();
		double x = data.readDouble();
		double y = data.readDouble();
		double z = data.readDouble();
		float pitch = data.readFloat();
		float yaw = data.readFloat();
		BlockPos attachPos = data.readBlockPos();
		int facingId = data.readInt();

		ctx.getTaskQueue().execute(() -> {
			ClientWorld world = MinecraftClient.getInstance().world;

			StickyItemFrameEntity entity = new StickyItemFrameEntity(world, attachPos, Direction.byId(facingId));
			entity.updatePosition(x,y,z);
			entity.updateTrackedPosition(x,y,z);
			entity.pitch = pitch;
			entity.yaw = yaw;
			entity.setEntityId(entityID);
			entity.setUuid(entityUUID);
			world.addEntity(entityID, entity);
		});
	}
}
