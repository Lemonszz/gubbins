package party.lemons.gubbins.util.registry;

import com.google.common.reflect.ClassPath;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.transformer.throwables.IllegalClassLoadError;
import party.lemons.gubbins.Gubbins;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

public class RegistryLoader
{
	public static void init()
	{
		try
		{
			ClassPath path = ClassPath.from(RegistryLoader.class.getClassLoader());
			for(ClassPath.ClassInfo info : path.getTopLevelClassesRecursive("party.lemons"))
			{
				try
				{
					Class c = Class.forName(info.getName());
					Annotation reg = c.getDeclaredAnnotation(AutoReg.class);
					if(reg != null)
					{
						AutoReg i = (AutoReg) reg;
						Registry registry = Registry.REGISTRIES.get(new Identifier(i.registry()));

						for(Field f : c.getDeclaredFields())
						{
							if(Modifier.isStatic(f.getModifiers()) && i.type().isAssignableFrom(f.getType()))
							{
								String regName = f.getName().toLowerCase(Locale.ENGLISH);
								Registry.register(registry, new Identifier(Gubbins.MODID, regName), f.get(c));

								//Block Special Case, create item if need be
								if(i.registry().equalsIgnoreCase("block"))
								{
									Block bl = (Block) f.get(c);
									if(bl instanceof BlockWithItem && ((BlockWithItem) bl).hasItem())
									{
										BlockItem bi = new BlockItem(bl, ((BlockWithItem) bl).makeItemSettings());
										Registry.register(Registry.ITEM, new Identifier(Gubbins.MODID, regName), bi);
									}
								}
							}
						}

					}
				}
				catch(RuntimeException e)   // lol
				{

				}
			}

		}
		catch(IOException | IllegalAccessException | ClassNotFoundException | IllegalClassLoadError e)
		{
		}
	}
}
