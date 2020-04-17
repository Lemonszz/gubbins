package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import party.lemons.gubbins.entity.StickyItemFrameEntity;

import java.util.UUID;

public class GubbinsNetworkClient
{
	//TODO: make this not shit
	public static void initClient(){
		ClientSidePacketRegistry.INSTANCE.register(GubbinsNetwork.SPAWN_ENTITY_CUSTOM, (ctx, data)->{
			EntityType<?> type = Registry.ENTITY_TYPE.get(data.readVarInt());
			UUID entityUUID = data.readUuid();
			int entityID = data.readVarInt();
			double x = data.readDouble();
			double y = data.readDouble();
			double z = data.readDouble();
			float pitch = (data.readByte() * 360) / 256.0F;
			float yaw = (data.readByte() * 360) / 256.0F;

			ctx.getTaskQueue().execute(() -> {
				ClientWorld world = MinecraftClient.getInstance().world;
				Entity entity = type.create(world);
				if(entity != null) {
					entity.updatePosition(x, y, z);
					entity.updateTrackedPosition(x, y, z);
					entity.pitch = pitch;
					entity.yaw = yaw;
					entity.setEntityId(entityID);
					entity.setUuid(entityUUID);
					world.addEntity(entityID, entity);
				}
			});
		});


		ClientSidePacketRegistry.INSTANCE.register(GubbinsNetwork.SPAWN_ENTITY_STICKY_FRAME, (ctx, data)->{
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
		});
	}
}
