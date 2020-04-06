package party.lemons.gubbins.gen.dungeon;

import com.mojang.datafixers.Dynamic;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.JigsawReplacementStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import party.lemons.gubbins.util.accessor.StructurePlacementDataAccessor;

import java.util.List;

public class EnhancedDungeonElement extends SinglePoolElement
{
	public EnhancedDungeonElement(String location, List<StructureProcessor> processors)
	{
		super(location, processors);
	}

	public EnhancedDungeonElement(String string, List<StructureProcessor> list, StructurePool.Projection projection)
	{
		super(string, list, projection);
	}

	public EnhancedDungeonElement(String string)
	{
		super(string);
	}

	public EnhancedDungeonElement(Dynamic<?> dynamic)
	{
		super(dynamic);
	}

	protected StructurePlacementData createPlacementData(BlockRotation blockRotation, BlockBox blockBox) {
		StructurePlacementData structurePlacementData = new StructurePlacementData();
		structurePlacementData.setBoundingBox(blockBox);
		structurePlacementData.setRotation(blockRotation);
		structurePlacementData.setUpdateNeighbors(true);
		structurePlacementData.setIgnoreEntities(false);
		((StructurePlacementDataAccessor)structurePlacementData).setPlaceFluid(false);
		structurePlacementData.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
		structurePlacementData.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
		this.processors.forEach(structurePlacementData::addProcessor);
		this.getProjection().getProcessors().forEach(structurePlacementData::addProcessor);
		return structurePlacementData;
	}
}
