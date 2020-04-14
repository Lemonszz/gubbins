package party.lemons.gubbins.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.entity.NewBoatEntity;
import party.lemons.gubbins.init.GubbinsNetwork;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin
{
	@Shadow
	public ClientPlayerEntity player;

	@Inject(at = @At("HEAD"), method = "openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", cancellable = true)
	public void onOpenScreen(Screen screen, CallbackInfo cbi)
	{
		if(screen instanceof AbstractInventoryScreen)
		{
			if(player.hasVehicle() && player.getVehicle() instanceof NewBoatEntity)
			{
				NewBoatEntity boat = (NewBoatEntity) player.getVehicle();
				if(boat.hasChest())
				{
					PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
					buf.writeInt(boat.getEntityId());
					ClientSidePacketRegistry.INSTANCE.sendToServer(GubbinsNetwork.CL_OPEN_BOAT_CHEST, buf);
					cbi.cancel();
				}
			}
		}
	}
}
