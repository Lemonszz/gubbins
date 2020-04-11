package party.lemons.gubbins.util.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class GsonUtil
{
	public static Gson makeStandardGson()
	{
		GsonBuilder builder = new GsonBuilder()
				.registerTypeAdapter(Identifier.class, new IdentifierAdapter()).setPrettyPrinting();

		return builder.create();
	}

	private static class IdentifierAdapter extends TypeAdapter<Identifier>
	{
		@Override
		public void write(JsonWriter out, Identifier value) throws IOException
		{
			out.value(value.toString());
		}

		@Override
		public Identifier read(JsonReader in) throws IOException
		{
			return new Identifier(in.nextString());
		}
	}
}
