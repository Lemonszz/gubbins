package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import party.lemons.gubbins.entity.NewBoatEntity;
import party.lemons.gubbins.entity.PrismarineArrowEntity;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = EntityType.class, registry = "entity_type")
public class GubbinsEntities
{
	public static final EntityType<NewBoatEntity> NEW_BOAT = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<NewBoatEntity>)NewBoatEntity::new).size(EntityDimensions.fixed(1.375F, 0.5625F)).build();
	public static final EntityType<PrismarineArrowEntity> PRISMARINE_ARROW = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<PrismarineArrowEntity>)PrismarineArrowEntity::new).size(EntityDimensions.fixed(0.5F, 0.5F)).build();
}