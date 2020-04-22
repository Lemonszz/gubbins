package party.lemons.gubbins.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;

public class BetterLocateCommand
{
	private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.locate.failed"));

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		ArgumentBuilder command = CommandManager.literal("locate2").requires((serverCommandSource) ->serverCommandSource.hasPermissionLevel(2));
		for(StructureFeature st : Registry.STRUCTURE_FEATURE)
		{
			Identifier id = Registry.STRUCTURE_FEATURE.getId(st);
			command = command.then(CommandManager.literal(id.toString()).executes((commandContext) ->execute(commandContext.getSource(), id)));
		}

		dispatcher.register((LiteralArgumentBuilder)command);
	}

	private static int execute(ServerCommandSource source, Identifier structure) throws CommandSyntaxException
	{
		BlockPos sourcePosition = new BlockPos(source.getPosition());
		BlockPos structurePosition = source.getWorld().locateStructure(structure.toString(), sourcePosition, 100, false);
		if(structurePosition == null)
			structurePosition = source.getWorld().locateStructure(structure.getPath(), sourcePosition, 100, false);

		if (structurePosition == null)
		{
			throw FAILED_EXCEPTION.create();
		}
		else {
			return sendCoordinates(source, structure.toString(), sourcePosition, structurePosition, "commands.locate.success");
		}
	}

	public static int sendCoordinates(ServerCommandSource serverCommandSource, String string, BlockPos blockPos, BlockPos blockPos2, String successMessage) {
		int i = MathHelper.floor(getDistance(blockPos.getX(), blockPos.getZ(), blockPos2.getX(), blockPos2.getZ()));
		Text text = Texts.bracketed(new TranslatableText("chat.coordinates", new Object[]{blockPos2.getX(), "~", blockPos2.getZ()})).method_27694((style) -> {
			return style.setColor(Formatting.GREEN).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + blockPos2.getX() + " ~ " + blockPos2.getZ())).setHoverEvent(new HoverEvent(HoverEvent.class_5247.field_24342, new TranslatableText("chat.coordinates.tooltip")));
		});
		serverCommandSource.sendFeedback(new TranslatableText(successMessage, new Object[]{string, text, i}), false);
		return i;
	}

	private static float getDistance(int x1, int y1, int x2, int y2) {
		int i = x2 - x1;
		int j = y2 - y1;
		return MathHelper.sqrt((float)(i * i + j * j));
	}
}
