package party.lemons.gubbins.item.quiver;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import party.lemons.gubbins.Gubbins;

public class QuiverScreen extends HandledScreen<QuiverScreenHandler>
{
	private static final Identifier TEXTURE = new Identifier(Gubbins.MODID, "textures/gui/quiver.png");

	public QuiverScreen(QuiverScreenHandler handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title);
		this.backgroundHeight = 133;
		this.backgroundWidth = 176;
	}

	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		this.drawBackground(delta, mouseX, mouseY);
		super.render(mouseX, mouseY, delta);
		this.drawMouseoverTooltip(mouseX, mouseY);
	}

	@Override
	protected void drawForeground(int mouseX, int mouseY) {
		this.textRenderer.draw(this.title.asFormattedString(), 8.0F, 6.0F, 4210752);
		this.textRenderer.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.backgroundHeight - 96 + 2), 4210752);
	}

	@Override
	protected void drawBackground(float delta, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;

		this.drawTexture(i, j, 0, 0, this.backgroundWidth, 6 * 18 + 17);
		this.drawTexture(i, j + 6 * 18 + 17, 0, 126, this.backgroundWidth, 96);
	}
}
