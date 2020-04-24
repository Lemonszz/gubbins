package party.lemons.gubbins.util.registry;

import com.google.common.collect.Lists;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class RegistryLoader
{
	private static Set<String> REGISTER_PACKAGES = new HashSet<>();
	private static Map<Registry, RegistryCallback> CALLBACKS = new HashMap<>();

	public static void register(Class c)
	{
		Annotation reg = c.getDeclaredAnnotation(AutoReg.class);
		if(reg != null)
		{
			AutoReg i = (AutoReg) reg;
			Registry registry = Registry.REGISTRIES.get(new Identifier(i.registry()));

			try
			{
				for(Field f : c.getDeclaredFields())
				{
					if(Modifier.isStatic(f.getModifiers()) && i.type().isAssignableFrom(f.getType()))
					{
						String regName = f.getName().toLowerCase(Locale.ENGLISH);
						Identifier id = new Identifier(i.modid(), regName);
						Object regObj = f.get(c);
						Registry.register(registry, id, regObj);

						if(CALLBACKS.containsKey(registry))
						{
							CALLBACKS.get(registry).callback(registry, regObj, id);
						}
					}
				}
			}
			catch(Exception e)
			{
				//if crash == true; dont()
			}
		}
	}

	public static void init()
	{
		//Block -> BlockItem callback via BlockWithItem interface
		registerCallback(Registry.BLOCK, (registry, block, identifier)->
		{
			if(block instanceof BlockWithItem && ((BlockWithItem) block).hasItem())
			{
				BlockItem bi = new BlockItem(block, ((BlockWithItem) block).makeItemSettings());
				Registry.register(Registry.ITEM, identifier, bi);
			}
		});

		for(String s : REGISTER_PACKAGES)
		{
			Reflections reflections = new Reflections(s);
			List<Class<?>> toRegister = Lists.newArrayList();
			toRegister.addAll(reflections.getTypesAnnotatedWith(AutoReg.class));
			toRegister.sort((a,b)->{
				int p1 = a.getDeclaredAnnotation(AutoReg.class).priority();
				int p2 = b.getDeclaredAnnotation(AutoReg.class).priority();

				return p1 - p2;
			});

			toRegister.forEach(RegistryLoader::register);
		}
	}

	public static <T> void registerCallback(Registry<T> registry, RegistryCallback<T> callback)
	{
		CALLBACKS.put(registry, callback);
	}

	public static void registerPackage(String packagePrefix)
	{
		REGISTER_PACKAGES.add(packagePrefix);
	}

	public interface RegistryCallback<T>
	{
		void callback(Registry<T> registry, T registryObject, Identifier identifier);
	}
}
